package com.cheese.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DisconnectionStream implements TCPStream<DisconnectionStream> {
    String name;

    public DisconnectionStream() {}

    public DisconnectionStream(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void send(DataOutputStream os) throws IOException {
        os.writeByte(NetworkConstants.DISCONNECTION_STREAM);
        os.writeUTF(name);
    }

    @Override
    public DisconnectionStream receive(DataInputStream is) throws IOException {
        name = is.readUTF();
        return this;
    }
}
