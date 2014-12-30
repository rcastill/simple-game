package com.cheese.game;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
	public static final int NORMAL 	= 0;
	public static final int ROAD 	= 1;

	public Texture texture;

	private int flags;

	public Tile(Texture texture, int flags) {
		this.texture 	= texture;
		this.flags		= flags;
	}

	public Tile(Texture texture) {
		this(texture, NORMAL);
	}

	public boolean isRoad() {
		return (flags & ROAD) != 0;
	}

	public Texture getTex() {
		return texture;
	}
}
