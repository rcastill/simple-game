package com.cheese.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tools {
	public static SpriteBatch batch;
	public static ShapeRenderer shape;

	public static void create() {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
	}
}
