package com.cheese.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Main extends ApplicationAdapter {

	Streamer streamer;
	Player player1;
	Player player2;
	Road road;

	@Override
	public void create () {
		Tools.create();

		player1 = new Player(Assets.player1, View.TILE_SIZE * 3, 0);
		player2 = new Player(Assets.player2, View.TILE_SIZE * 3, 0);
		road	= new Road("map.rd");

		streamer = new Streamer();

		// configurations.
		View.setViewport();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(Input.inst);

		// move players to their sections.
		player1.move(View.width / 2 - road.getRealWidth(), 0);
		player2.move(View.width / 2, 0);

		player2.setOnline(true);
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		road.render(View.width / 2);
		road.render(View.width / 2 - road.getRealWidth());

		player1.render();
		player2.render();
	}

	public void update() {
		player1.update();
		player2.update();

		streamer.out(player1);
		streamer.in(player2);

		View.follow(View.width / 2, player1.getDrawY());
		View.update();
	}
}
