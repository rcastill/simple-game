package com.cheese.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class Road {
	Tile[][] tiles;
	String name;

	int height;
	int width;

	public Road(String filename) {
		// save road name for future reference.
		name = filename.replace(".rd", "");

		FileHandle file = Gdx.files.getFileHandle(filename, Files.FileType.Internal);
		BufferedReader reader = new BufferedReader(file.reader());

		try {
			String line; int y = -1;
			while((line = reader.readLine()) != null) {
				// read the first line, which is the dimensions line.
				if(y == -1) {
					String[] dims = line.split("x");

					// save dims.
					width 	= Integer.parseInt(dims[0]);
					height 	= Integer.parseInt(dims[1]);

					// create tile map.
					tiles = new Tile[width][height];
				} else {
					for(int x = 0; x < line.length(); x++) {
						if(line.charAt(x) == '0') {
							int random = MathUtils.random(0, 15);
							switch(random) {
								case 0:  tiles[x][height - y - 1] = new Tile(Assets.tree_1); break;
								case 1:  tiles[x][height - y - 1] = new Tile(Assets.tree_2); break;
								default: tiles[x][height - y - 1] = new Tile(Assets.grass);  break;
							}
						} else if(line.charAt(x) == '1')
							tiles[x][height - y - 1] = new Tile(Assets.road_v, Tile.ROAD);
					}
				}

				y++;
			}

			for(int x = 0; x < width; x++)
				for(y = 0; y < height; y++) {
					if(!tiles[x][y].isRoad()) continue;

					if(isRoadAt(x - 1, y) && isRoadAt(x + 1, y) &&
							isRoadAt(x, y - 1) && isRoadAt(x, y + 1)) {
						tiles[x][y].texture = Assets.road_i;
					} else {
						if(isRoadAt(x - 1, y) && isRoadAt(x + 1, y) && isRoadAt(x, y + 1))
							tiles[x][y].texture = Assets.road_t_u;
						else if(isRoadAt(x - 1, y) && isRoadAt(x + 1, y) && isRoadAt(x, y - 1))
							tiles[x][y].texture = Assets.road_t_b;
						else if(isRoadAt(x, y - 1) && isRoadAt(x, y + 1) && isRoadAt(x - 1, y))
							tiles[x][y].texture = Assets.road_t_r;
						else if(isRoadAt(x, y - 1) && isRoadAt(x, y + 1) && isRoadAt(x + 1, y))
							tiles[x][y].texture = Assets.road_t_l;
						else {
							if(isRoadAt(x - 1, y) && isRoadAt(x + 1, y))
								tiles[x][y].texture = Assets.road_h;
							else if(isRoadAt(x, y - 1) && isRoadAt(x, y + 1))
								tiles[x][y].texture = Assets.road_v;
							else if(isRoadAt(x + 1, y) && isRoadAt(x, y - 1))
								tiles[x][y].texture = Assets.road_tr;
							else if(isRoadAt(x - 1, y) && isRoadAt(x, y - 1))
								tiles[x][y].texture = Assets.road_tl;
							else if(isRoadAt(x - 1, y) && isRoadAt(x, y + 1))
								tiles[x][y].texture = Assets.road_bl;
							else if(isRoadAt(x + 1, y) && isRoadAt(x, y + 1))
								tiles[x][y].texture = Assets.road_br;
						}
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(int d) {
		Tools.batch.begin();
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++) {
				Tools.batch.draw(tiles[x][y].getTex(), d + x * View.TILE_SIZE - View.getX(),
						y * View.TILE_SIZE - View.getY(), View.TILE_SIZE, View.TILE_SIZE);
			}
		Tools.batch.end();
	}

	public int getRealWidth() {
		return width * View.TILE_SIZE;
	}

	public int getRealHeight() {
		return height * View.TILE_SIZE;
	}

	public boolean isTileAt(int x, int y) {
		if(x < 0 || x > width - 1) return false;
		if(y < 0 || y > height - 1) return false;
		return true;
	}

	public boolean isRoadAt(int x, int y) {
		return isTileAt(x, y) && tiles[x][y].isRoad();
	}
}
