package com.cheese.net;

import java.io.IOException;

public class FrontClientSender extends Thread {
    FrontClient client;

    public FrontClientSender(FrontClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (client.isRunning()) try {
            TCPStream stream = client.sendQueue.take();
            stream.send(client.os);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
