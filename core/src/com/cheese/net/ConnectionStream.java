package com.cheese.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConnectionStream implements TCPStream<ConnectionStream> {
    String name;

    public ConnectionStream() {}

    public ConnectionStream(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void send(DataOutputStream os) throws IOException {
        os.writeByte(NetworkConstants.CONNECTION_STREAM);
        os.writeUTF(name);
    }

    @Override
    public ConnectionStream receive(DataInputStream is) throws IOException {
        name = is.readUTF();
        return this;
    }
}
