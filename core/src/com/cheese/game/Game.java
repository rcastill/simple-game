package com.cheese.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Game extends ApplicationAdapter {
	public static ParticleSystem ps;
	public static Streamer streamer;
	public static Audio audio;
	public static Road road;

	Player player1;
	Player player2;

	@Override
	public void create() {
		Tools.create();

		streamer 	= new Streamer(Streamer.DEV_MODE);
		road 		= new Road("maps/entrance.rd", "maps/map2.rd", "maps/map1.rd", "maps/map3.rd");
		ps			= new ParticleSystem();

		if(streamer.playerNo == 1) {
			player1 = new Player(Assets.player1, View.TILE_SIZE * 4, 0);
			player2 = new Player(Assets.player2, View.TILE_SIZE * 4 + road.getRealWidth() + 20, 0);
		} else {
			player1 = new Player(Assets.player2, View.TILE_SIZE * 4 + road.getRealWidth() + 20, 0);
			player2 = new Player(Assets.player1, View.TILE_SIZE * 4, 0);
		}

//		player2.setOnline();
		player2.setArtificial(0.5f);

		// configurations.
		View.setViewport();
//		View.lookAt(road.getRealWidth() + 10, 2000);
		View.lookAt(road.getRealWidth() + 10, 0);
		Gdx.gl.glClearColor(0.375f, 0.29296875f, 0.2421875f, 1);
		Gdx.input.setInputProcessor(Input.inst);
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		road.render(0);
		road.render(road.getRealWidth() + 20);

		ps.render();

		player1.render();
		player2.render();
	}

	public void update() {
		streamer.out(player1);
		streamer.in(player2);

		if(streamer.start) {
			player1.update();
			player2.update();
		}

		ps.update();

		View.follow((player1.getCenterX() + player2.getCenterX()) / 2,
				(player1.getCenterY() + player2.getCenterY()) / 2);
		View.update();
		Input.update();
	}
}
