package com.cheese.game;

import com.badlogic.gdx.Gdx;

public class View {
	public static int TILE_SIZE = 64;

	public static int height;
	public static int width;

	private static int _x;
	private static int _y;
	private static int x;
	private static int y;

	public static void setViewport() {
		height	= Gdx.graphics.getHeight();
		width	= Gdx.graphics.getWidth();
	}

	public static void update() {
		x += (_x - x) * 0.1f;
		y += (_y - y) * 0.1f;
	}

	public static void follow(int x, int y) {
		_x = x;
		_y = y;
	}

	public static int getX() {
		return x - width / 2;
	}

	public static int getY() {
		if(y < height / 2)
			return 0;
		return y - height / 2;
	}
}
