package com.cheese.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FrontClient extends Socket implements Runnable {
    String name = null;
    DataOutputStream os;
    DataInputStream is;
    boolean running = true;
    BlockingQueue<TCPStream> sendQueue = new LinkedBlockingQueue<TCPStream>();
    Queue<GameStream> gameStreams = new LinkedList<GameStream>();
    Queue<ConnectionStream> connectionStreams = new LinkedList<ConnectionStream>();
    Queue<DisconnectionStream> disconnectionStreams = new LinkedList<DisconnectionStream>();
    public final Thread thread;
    public int size;

    public FrontClient(String name, String host, int port) throws IOException {
        super(host, port);
        os = new DataOutputStream(getOutputStream());
        is = new DataInputStream(getInputStream());
        sendName(name);
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    public boolean sendName(String name) throws IOException {
        os.writeUTF(name);
        int ans = is.readByte();

        if (ans == NetworkConstants.GREEN_LIGHT) {
            size = is.readInt();
            this.name = name;
            return true;
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void send(float x, float y, int side, int life) throws IOException, InterruptedException {
        sendQueue.put(new GameStream(x, y, side, life));
    }

    public void send(GameStream gameStream) throws IOException, InterruptedException {
        sendQueue.put(gameStream);
    }

    public ConnectionStream checkConnection() {
        return connectionStreams.poll();
    }

    public DisconnectionStream checkDisconnection() {
        return disconnectionStreams.poll();
    }

    public GameStream getGameStream() throws IOException {
        return gameStreams.poll();
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        System.out.println("Thread init");

        FrontClientSender sendThread = new FrontClientSender(this);
        sendThread.setDaemon(true);
        sendThread.start();

        while (running) try {
            System.out.println("running");

            int signal = is.readByte();

            System.out.println("Que mierda");

            switch (signal) {
                case NetworkConstants.CONNECTION_STREAM:
                    connectionStreams.add(new ConnectionStream().receive(is));
                    break;

                case NetworkConstants.DISCONNECTION_STREAM:
                    disconnectionStreams.add(new DisconnectionStream().receive(is));
                    break;

                case NetworkConstants.GAME_STREAM:
                    gameStreams.add(new GameStream().receive(is));
                    break;
            }

            System.out.println("Packet received (" + signal + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
