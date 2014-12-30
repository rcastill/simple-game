package com.cheese.game;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class AI {
	ArrayList<Integer> directions;
	ArrayList<Vector2> points;
	boolean prevMoving;
	float level;

	Vector2 endPoint;

	public AI(Player player, float level) {
		this.level 	= level;

		directions	= new ArrayList<Integer>();
		points 		= new ArrayList<Vector2>();

		endPoint	= Game.road.getEndPoint();

		tree(player.getTileX(), player.getTileY());
		points.clear();
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

		player.addDirection(directions.get(0));
		directions.remove(0);
	}

	public boolean tree(int x, int y) {
		if(endPoint.x == x && endPoint.y == y)
			return true;

		points.add(new Vector2(x, y));

		if(Game.road.isRoadAt(x, y + 1) && !isVisited(x, y + 1) && tree(x, y + 1)) {
			directions.add(0, 0);
			return true;
		} else if(Game.road.isRoadAt(x - 1, y) && !isVisited(x - 1, y) && tree(x - 1, y)) {
			directions.add(0, 1);
			return true;
		} else if(Game.road.isRoadAt(x + 1, y) && !isVisited(x + 1, y) && tree(x + 1, y)) {
			directions.add(0, 2);
			return true;
		} else if(Game.road.isRoadAt(x, y - 1) && !isVisited(x, y - 1) && tree(x, y - 1)) {
			directions.add(0, 3);
			return true;
		} else
			return false;
	}

	public boolean isVisited(int x, int y) {
		for(Vector2 point : points)
			if(point.x == x && point.y == y)
				return true;
		return false;
	}
}
