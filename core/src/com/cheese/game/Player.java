package com.cheese.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.cheese.net.GameStream;

import java.util.ArrayList;

public class Player {
	/**
	 * Frames in which the player cannot take more damage.
	 */
	public static final int DAMAGE_COOLDOWN = 30;
	public static final float EMISSION_FREQ = 0.2f;
	public static final int DAMAGE			= 10;
	public static final int SPEED 			= 5;

	public Texture texture;
	public int height;
	public int width;

	private ArrayList<Integer> directions;
	private float emissionTime;
	private boolean artificial;
	private int damageTime;
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

		height 	= width = (int) (View.TILE_SIZE * 0.9f);
		life 	= 100;
		_x 		= x;
		_y 		= y;
	}

	public void update() {
		if(damageTime > 0)
			damageTime -= 1;

		// get new direction from list.
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
			// check user input if this isn't artificial nor online player.
			if(Input.rightButton)
				directions.add(2);
			else if(Input.leftButton)
				directions.add(1);
			else if(Input.upButton)
				directions.add(0);
			else if(Input.downButton)
				directions.add(3);
		}

		if(artificial)
			ai.update(this);

		// move the truck only if...
		if(moving) {
			x += MathUtils.clamp(_x - x, -SPEED, SPEED);
			y += MathUtils.clamp(_y - y, -SPEED, SPEED);

			if((int) (x / 10) == (int) (_x / 10) && (int) (y / 10) == (int) (_y / 10)) {
				x = _x;
				y = _y;
				moving = false;
			}
		}

		// apply damage.
		if(damageTime <= 0 && !Game.road.isRoadAt(getTileX(), getTileY())) {
			damageTime = DAMAGE_COOLDOWN;
			life -= DAMAGE;
		}

		// update emission time and emit a particle.
		emissionTime -= Gdx.graphics.getDeltaTime();
		if(emissionTime <= 0) {
			emissionTime = EMISSION_FREQ;
			int vx = direction == 1 ? 1 : (direction == 2 ? -1 : 0);
			int vy = direction == 3 ? 1 : (direction == 0 ? -1 : 0);
			Game.ps.emit(getCenterX() + 4 + vx * 20, getCenterY() + vy * 20, vx, vy);
		}
	}

	public void render() {
		// draw guide line.
		Tools.shape.setColor(getColor());
		Tools.shape.begin(ShapeRenderer.ShapeType.Filled);
		Tools.shape.rect(0, getY() + height + 5 - View.getY(), View.width, 1);

		Tools.shape.setColor(getColor());
		Tools.shape.rect(getCenterX() - 51 - View.getX(), getY() - 11 - View.getY(), 102, 7);

		Tools.shape.setColor(Color.BLACK);
		Tools.shape.rect(getCenterX() - 50 - View.getX(), getY() - 10 - View.getY(), 100, 5);

		Tools.shape.setColor(getColor());
		Tools.shape.rect(getCenterX() - 50 - View.getX(), getY() - 10 - View.getY(), life, 5);
		Tools.shape.end();

		// draw rotated truck.
		Tools.batch.begin();

		if(damageTime <= 0 || damageTime % 4 == 0 || damageTime % 4 == 1) {
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
		}

		Tools.batch.end();
	}

	/**
	 * Sets this player as an online player, this will require the method
	 * {@code Player.take} to update the information.
	 */
	public void setOnline() {
		this.online = true;
	}

	/**
	 * Sets this player as an AI.
	 * @param level the level of the AI, from 0 to 1, being 1 as an non-failure intelligence.
	 */
	public void setArtificial(float level) {
		this.artificial = true;
		this.ai = new AI(level);
	}

	/**
	 * Getter for the player's current direction.
	 * @return the direction, 0 up, 1 left, 2, right, 3 down.
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Getter for the player's real x position.
	 * TODO: CHANGE NAME.
	 * @return x.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Getter for the player's real y position.
	 * TODO: CHANGE NAME.
	 * @return y.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Getter for the player's center x.
	 * TODO: CHANGE NAME.
	 * @return center x.
	 */
	public int getCenterX() {
		return (int) (x + width / 2);
	}

	/**
	 * Getter for the player's center x.
	 * TODO: CHANGE NAME.
	 * @return center x.
	 */
	public int getCenterY() {
		return (int) (y + height / 2);
	}

	/**
	 * Obtains the x position according to tile position.
	 * @return tile x.
	 */
	public int getTileX() {
		if(x > Game.road.getRealWidth())
			return (int) ((x - Game.road.getRealWidth() - 10) / View.TILE_SIZE);
		else
			return (int) (x / View.TILE_SIZE);
	}

	/**
	 * Obtains the y position according to tile position.
	 * @return tile y.
	 */
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
