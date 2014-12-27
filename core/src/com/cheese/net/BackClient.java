package com.cheese.net;

import sun.nio.ch.Net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BackClient extends Thread {
    String name;
    Server server;
    Socket socket;
    DataOutputStream os;
    DataInputStream is;
    boolean running = true;

    public BackClient(Server server) throws IOException {
        this.server = server;
        socket = server.accept();

        System.out.println("Client connected (" + toString() + ")");

        os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
    }

    public boolean isRunning() {
        return running;
    }

    public void halt() {
        running = false;
    }

    public void disconnect() {
        try {
            server.sendToAll(new DisconnectionStream(name), this);
            server.removeClient(this);
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not close BackClient (" + toString() + ")");
        }
    }

    public String toString() {
        return socket.getInetAddress().toString();
    }

    @Override
    public void run() {
        try {
            while (true) {
                name = is.readUTF();

                if (server.checkName(name))
                    os.writeByte(NetworkConstants.GREEN_LIGHT);

                else {
                    os.writeByte(NetworkConstants.RED_LIGHT);
                    continue;
                }

                server.sendToAll(new ConnectionStream(name), this);
                break;
            }
        } catch (IOException e) {
            System.out.println("Could not retrieve name");
            disconnect();
            return;
        }

        while (server.isRunning()) try {
            is.readByte(); // signal
            server.sendToAll(new GameStream().receive(is), this);
        } catch (IOException e) {
            disconnect();
            break;
        }
    }
}