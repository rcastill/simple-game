package com.cheese.game;

import com.badlogic.gdx.math.MathUtils;
import com.cheese.net.FrontClient;
import com.cheese.net.GameStream;

import java.io.IOException;

public class Streamer {
	FrontClient frontClient;

	public Streamer() {
		try {
			frontClient = new FrontClient("Temp" + MathUtils.random(100), "localhost", 5000);

			while(frontClient.getName() == null)
				frontClient.sendName("Temp" + MathUtils.random(100));

			frontClient.thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void out(Player player) {
		try {
			frontClient.send(player.getX(), player.getY(), player.getDirection(), player.getLife());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void in(Player player) {
		try {
			GameStream gs;
			while((gs = frontClient.getGameStream()) != null)
				player.take(gs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
