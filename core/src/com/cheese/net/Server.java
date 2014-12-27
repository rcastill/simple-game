package com.cheese.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends ServerSocket {
    List<BackClient> clients = new ArrayList<BackClient>();
    boolean running = true;

    public static void main(String[] args) {
        try {
            String host = "localhost";
            int port = 5000;

            if (args.length == 2) {
                host = args[0];
                port = Integer.parseInt(args[1]);
            }

            new Server(host, port).mainloop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(String address, int port) throws IOException {
        super(port, 5, InetAddress.getByName(address));
    }

    public void sendToAll(TCPStream stream, BackClient sender) throws IOException {
        for (BackClient client : clients)
            if (client != sender)
                stream.send(client.os);
    }

    public boolean isRunning() {
        return running;
    }

    public void halt() {
        running = false;
    }

    public boolean checkName(String name) {
        for (BackClient client : clients)
            if (client.name.equals(name))
                return false;

        return true;
    }

    public void mainloop() {
        while (running) try {
            Thread clientThread = new BackClient(this);
            clientThread.setDaemon(true);
            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(BackClient backClient) {
        clients.remove(backClient);
    }
}
