package com.cheese.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameStream implements TCPStream<GameStream> {
    public float x, y;
    public byte side, life;

    public GameStream() {}

    public GameStream(float x, float y, int side, int life) {
        this.x = x;
        this.y = y;
        this.side = (byte) side;
        this.life = (byte) life;
    }

    @Override
    public void send(DataOutputStream os) throws IOException {
        os.writeByte(NetworkConstants.GAME_STREAM);
        os.writeFloat(x);
        os.writeFloat(y);
        os.writeByte(side);
        os.writeByte(life);
    }

    @Override
    public GameStream receive(DataInputStream is) throws IOException {
        x = is.readFloat();
        y = is.readFloat();
        side = is.readByte();
        life = is.readByte();
        return this;
    }
}
