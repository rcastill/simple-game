package com.cheese.game;

import com.badlogic.gdx.Gdx;

/**
 * A very simple and common particle.
 * @author Shelo
 */
public class Particle {
	public static final float SIZE = 30;

	public float time;
	public float life;
	public float vx;
	public float vy;
	public float x;
	public float y;

	public Particle(float x, float y, float vx, float vy, float time) {
		this.time	= life = time;
		this.vx 	= vx;
		this.vy 	= vy;
		this.x 		= x;
		this.y 		= y;
	}

	/**
	 * Updates this particle.
	 */
	public void update() {
		life 	-= Gdx.graphics.getDeltaTime();
		x 		+= vx;
		y 		+= vy;
	}

	/**
	 * Renders this particle.
	 */
	public void render() {
		Tools.batch.setColor(1, 1, 1, life / time);
		float addition = (1 - life / time) * 20;
		float size = SIZE + addition;
		Tools.batch.draw(Assets.smoke, x - size / 2 - View.getX(), y - size / 2 - View.getY(), size, size);
		Tools.batch.setColor(1, 1, 1, 1);
	}

	public boolean isDead() {
		return life <= 0;
	}
}
