package com.cheese.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface TCPStream<DataType> {
    public void send(DataOutputStream os) throws IOException;
    public DataType receive(DataInputStream is) throws IOException;
}
