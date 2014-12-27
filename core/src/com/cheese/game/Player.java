package com.cheese.game;

import com.badlogic.gdx.graphics.Texture;
import com.cheese.net.GameStream;

public class Player {
	public static final int SPEED = 5;

	public Texture texture;
	public int height;
	public int width;

	private float x;
	private float y;
	private int direction;
	private boolean online;
	private int life;

	public Player(Texture texture, int x, int y) {
		this.texture = texture;
		this.x = x;
		this.y = y;

		height = width = (int) (View.TILE_SIZE * 0.9f);

		direction = 0;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void update() {
		/* Check inputs. */
		if(!online) {
			if(Input.rightButton)
				direction = 2;
			else if(Input.leftButton)
				direction = 1;
			else
				direction = 0;

		/* Move the truck. */
			if(direction == 0)
				y += SPEED;
			else if(direction == 1)
				x -= SPEED;
			else if(direction == 2)
				x += SPEED;
		} else {
			if(x < View.width / 2)
				x += View.width / 2 + 10;
		}
	}

	public void render() {
		Tools.batch.begin();

		if(direction == 0)
			Tools.batch.draw(texture, x + View.TILE_SIZE - 3 - View.getX(), y - View.getY(), 0, 0, width, height, 1, 1,
					90, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		else if(direction == 1)
			Tools.batch.draw(texture, x - View.getX(), y - View.getY(), 0, 0, width, height, 1, 1, 0, 0, 0,
					texture.getWidth(), texture.getHeight(), true, false);
		else
			Tools.batch.draw(texture, x - View.getX(), y - View.getY(), width, height);

		Tools.batch.end();
	}

	public int getDirection() {
		return direction;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getLife() {
		return life;
	}

	public int getDrawX() {
		return (int) (x + width / 2);
	}

	public int getDrawY() {
		return (int) (y + height / 2);
	}

	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void take(GameStream gs) {
		direction 	= gs.side;
		life 		= gs.life;
		x 			= gs.x;
		y 			= gs.y;
	}
}
