package com.cheese.game;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class AI {
	boolean prevMoving;
	ArrayList<Vector2> points;
	float level;

	public AI(float level) {
		this.level 	= level;
		points = new ArrayList<Vector2>();
	}

	public void update(Player player) {
		if(player.isMoving()) {
			prevMoving = true;
			return;
		}

		if(prevMoving != player.isMoving())
			points.add(new Vector2(player.getTileX(), player.getTileY()));
		prevMoving = player.isMoving();

		if(MathUtils.random() > level) return;

		int x = player.getTileX();
		int y = player.getTileY();

		if(Main.road.isRoadAt(x, y + 1) && !isVisited(x, y + 1))
			player.addDirection(0);
		else if(Main.road.isRoadAt(x - 1, y) && !isVisited(x - 1, y))
			player.addDirection(1);
		else if(Main.road.isRoadAt(x + 1, y) && !isVisited(x + 1, y))
			player.addDirection(2);
		else if(Main.road.isRoadAt(x, y - 1) && !isVisited(x, y - 1))
			player.addDirection(3);
	}

	public boolean isVisited(int x, int y) {
		for(Vector2 point : points)
			if(point.x == x && point.y == y)
				return true;
		return false;
	}
}
