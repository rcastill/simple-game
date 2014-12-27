package com.cheese.game;

import com.badlogic.gdx.math.MathUtils;
import com.cheese.net.FrontClient;
import com.cheese.net.GameStream;

import java.io.IOException;

public class Streamer {
	public static boolean DEV_MODE = true;

	FrontClient frontClient;
	public boolean devMode;
	public boolean start;
	public int playerNo;

	public Streamer(boolean devMode) {
		this.devMode = devMode;

		if(!devMode) {
			try {
				frontClient = new FrontClient("Temp" + MathUtils.random(100), "192.168.0.11", 5000);

				while(frontClient.getName() == null)
					frontClient.sendName("Temp" + MathUtils.random(100));

				playerNo = frontClient.size + 1;

				frontClient.thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			start = true;
			playerNo = 1;
		}
	}

	public Streamer() {
		this(false);
	}

	public void out(Player player) {
		if(devMode) return;

		try {
			frontClient.send(player.getX(), player.getY(), player.getDirection(), player.getLife());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void in(Player player) {
		if(devMode) return;

		try {
			GameStream gs;
			while((gs = frontClient.getGameStream()) != null) {
				start = true;
				player.take(gs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
