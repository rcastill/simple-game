package com.cheese.game;

import com.badlogic.gdx.Gdx;

public class View {
	public static final float CAMERA_SPEED 	= 0.05f;
	public static final int TILE_SIZE 		= 64;

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
		x += (_x - x) * CAMERA_SPEED;
		y += (_y - y) * CAMERA_SPEED;
	}

	public static void follow(int x, int y) {
		_x = x;
		_y = y;
	}

	public static void lookAt(int x, int y) {
		View.x = x;
		View.y = y;

		follow(x, y);
	}

	public static int getX() {
		return x - width / 2;
	}

	public static int getY() {
		return y - height / 2;
	}
}
