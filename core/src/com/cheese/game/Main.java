package com.cheese.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Main extends ApplicationAdapter {
	public static Road road;
	Streamer streamer;
	Player player1;
	Player player2;

	@Override
	public void create() {
		Tools.create();

		streamer 	= new Streamer(Streamer.DEV_MODE);
		road 		= new Road("map.rd");

		if(streamer.playerNo == 1) {
			player1 = new Player(Assets.player1, View.TILE_SIZE * 3, 0);
			player2 = new Player(Assets.player2, View.TILE_SIZE * 3 + road.getRealWidth() + 20, 0);
		} else {
			player1 = new Player(Assets.player2, View.TILE_SIZE * 3 + road.getRealWidth() + 20, 0);
			player2 = new Player(Assets.player1, View.TILE_SIZE * 3, 0);
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

		View.follow((player1.getCenterX() + player2.getCenterX()) / 2, (player1.getCenterY() + player2.getCenterY()) / 2);
		View.update();
		Input.update();
	}
}
