package com.cheese.game;

import java.util.ArrayList;

public class ParticleSystem {
	public static final float PARTICLE_TIME = 1;
	ArrayList<Particle> particles;

	public ParticleSystem() {
		particles = new ArrayList<Particle>();
	}

	public void update() {
		for(int i = particles.size() - 1; i > -1; i--) {
			Particle particle = particles.get(i);
			if(particle.isDead())
				particles.remove(i);
			else
				particle.update();
		}
	}

	public void render() {
		Tools.batch.begin();

		for(int i = particles.size() - 1; i > -1; i--)
			if(!particles.get(i).isDead())
				particles.get(i).render();

		Tools.batch.end();
	}

	public void emit(float x, float y, float vx, float vy) {
		particles.add(new Particle(x, y, vx, vy, PARTICLE_TIME));
	}
}
