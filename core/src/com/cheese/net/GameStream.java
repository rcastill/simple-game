package com.cheese.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GameStream implements TCPStream<GameStream> {
    public float x, y;
    public boolean side;
    public byte life;

    public GameStream() {}

    public GameStream(float x, float y, boolean side, int life) {
        this.x = x;
        this.y = y;
        this.side = side;
        this.life = (byte) life;
    }

    @Override
    public void send(DataOutputStream os) throws IOException {
        os.writeByte(NetworkConstants.GAME_STREAM);
        os.writeFloat(x);
        os.writeFloat(y);
        os.writeBoolean(side);
        os.writeByte(life);
    }

    @Override
    public GameStream receive(DataInputStream is) throws IOException {
        x = is.readFloat();
        y = is.readFloat();
        side = is.readBoolean();
        life = is.readByte();
        return this;
    }
}
