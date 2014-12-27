package com.cheese.game;

import com.badlogic.gdx.graphics.Texture;

public class Player {
	public static final int SPEED = 5;

	public Texture texture;
	public int height;
	public int width;
	private float x;
	private float y;
	private int direction;

	private boolean online;

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
		}

		/* Move the truck. */
		if(direction == 0)
			y += SPEED;
		else if(direction == 1)
			x -= SPEED;
		else if(direction == 2)
			x += SPEED;
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

	public int getX() {
		return (int) (x + width / 2);
	}

	public int getY() {
		return (int) (y + height / 2);
	}

	public void move(int x, int y) {
		this.x += x;
		this.y += y;
	}
}
