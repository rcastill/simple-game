package com.cheese.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.cheese.net.GameStream;

import java.util.ArrayList;

public class Player {
	public static final int SPEED = 5;

	public Texture texture;
	public int height;
	public int width;

	private ArrayList<Integer> directions;
	private boolean artificial;
	private boolean moving;
	private boolean online;
	private int direction;
	private Color color;
	private int life;
	private float _x;
	private float _y;
	private float x;
	private float y;

	private AI ai;

	public Player(Texture texture, int x, int y) {
		this.texture 	= texture;
		this.x 			= x;
		this.y 			= y;

		directions 	= new ArrayList<Integer>();

		_x = x;
		_y = y;

		height = width = (int) (View.TILE_SIZE * 0.9f);
	}

	public void update() {
		/* Get new direction if there's one. */
		if(!moving && directions.size() > 0) {
			direction = directions.get(0);
			directions.remove(0);

			if(direction == 0)
				_y += View.TILE_SIZE;
			else if(direction == 1)
				_x -= View.TILE_SIZE;
			else if(direction == 2)
				_x += View.TILE_SIZE;
			else if(direction == 3)
				_y -= View.TILE_SIZE;

			moving = true;
		}

		if(!online && !artificial) {
			/* Check inputs. */
			if(Input.rightButton)
				directions.add(2);
			else if(Input.leftButton)
				directions.add(1);
			else if(Input.upButton)
				directions.add(0);
			else if(Input.downButton)
				directions.add(3);
		}

		if(artificial) {
			ai.update(this);
		}

		/* Move the truck only if... */
		if(moving) {
			x += MathUtils.clamp(_x - x, -SPEED, SPEED);
			y += MathUtils.clamp(_y - y, -SPEED, SPEED);

			if((int) (x / 10) == (int) (_x / 10) && (int) (y / 10) == (int) (_y / 10)) {
				x = _x;
				y = _y;
				moving = false;
			}
		}
	}

	public void render() {
		// draw guide line.
		Tools.shape.setColor(getColor());
		Tools.shape.begin(ShapeRenderer.ShapeType.Filled);
		Tools.shape.rect(0, getY() + height + 5 - View.getY(), View.width, 2);
		Tools.shape.end();

		// draw rotated truck.
		Tools.batch.begin();

		if(direction == 0)
			Tools.batch.draw(texture, x + View.TILE_SIZE - 3 - View.getX(), y - View.getY(), 0, 0, width, height, 1, 1,
					90, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		else if(direction == 1)
			Tools.batch.draw(texture, x - View.getX(), y - View.getY(), 0, 0, width, height, 1, 1, 0, 0, 0,
					texture.getWidth(), texture.getHeight(), true, false);
		else if(direction == 2)
			Tools.batch.draw(texture, x - View.getX(), y - View.getY(), width, height);
		else if(direction == 3)
			Tools.batch.draw(texture, x - View.getX() + 4, y - View.getY() + View.TILE_SIZE - 3, 0, 0, width, height, 1, 1,
					270, 0, 0, texture.getWidth(), texture.getHeight(), false, false);

		Tools.batch.end();
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setArtificial(float level) {
		this.artificial = true;
		this.ai = new AI(level);
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

	public int getDrawX() {
		return (int) (x + width / 2);
	}

	public int getDrawY() {
		return (int) (y + height / 2);
	}

	public int getTileX() {
		if(x > Main.road.getRealWidth())
			return (int) ((x - Main.road.getRealWidth() - 10) / View.TILE_SIZE);
		else
			return (int) (x / View.TILE_SIZE);
	}

	public int getTileY() {
		return (int) (y / View.TILE_SIZE);
	}

	public int getLife() {
		return life;
	}

	public boolean isMoving() {
		return moving;
	}

	public void addDirection(int direction) {
		directions.add(direction);
	}

	public void move(int x, int y) {
		this.x += x;
		this.y += y;

		_x += x;
		_y += y;
	}

	public void take(GameStream gs) {
//		int key = gs.key;
		life 	= gs.life;
	}

	/**
	 * Returns the color of this player. This is a LAZY method.
	 * @return color
	 */
	public Color getColor() {
		if(color != null) return color;

		if(texture == Assets.player1)
			color = new Color(0.19921875f, 0.3984375f, 0.59765625f, 0);
		else if(texture == Assets.player2)
			color = new Color(0.82421875f, 0.28125f, 0.2109375f, 0);

		return color;
	}
}
