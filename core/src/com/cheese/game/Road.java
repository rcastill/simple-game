package com.cheese.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Road {
	Tile[][] tiles;

	int height;
	int width;

	public Road(String... files) {
		// load all maps.
		ArrayList<Tile[]> rows = new ArrayList<Tile[]>();
		for(int i = files.length - 1; i > -1; i--)
			rows.addAll(loadTiles(files[i]));

		// get max row width.
		int maxWidth = 0;
		for(Tile[] row : rows)
			maxWidth = row.length > maxWidth ? row.length : maxWidth;

		// save resulting map dimensions.
		height 	= rows.size();
		width 	= maxWidth;

		// create matrix.
		tiles = new Tile[width][height];

		// align every row the center and fill final matrix.
		for(int y = 0; y < rows.size(); y++) {
			Tile[] row = rows.get(y);
			for(int x = 0; x < row.length; x++)
				tiles[x + (width - row.length) / 2][height - y - 1] = row[x];
		}

		// transform all roads when the tile matrix is done.
		transform();
	}

	private ArrayList<Tile[]> loadTiles(String filename) {
		ArrayList<Tile[]> tiles = new ArrayList<Tile[]>();

		FileHandle file = Gdx.files.getFileHandle(filename, Files.FileType.Internal);
		BufferedReader reader = new BufferedReader(file.reader());

		try {
			String line;
			while((line = reader.readLine()) != null) {
				Tile[] row = new Tile[line.length()];

				for(int x = 0; x < line.length(); x++)
					if(line.charAt(x) == '0') {
						int random = MathUtils.random(0, 15);
						switch(random) {
							case 0:  row[x] = new Tile(Assets.tree_1); break;
							case 1:  row[x] = new Tile(Assets.tree_2); break;
							default: row[x] = new Tile(Assets.grass);  break;
						}
					} else if(line.charAt(x) == '1')
						row[x] = new Tile(Assets.road_v, Tile.ROAD);

				tiles.add(row);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return tiles;
	}

	private void transform() {
		// transform roads.
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++) {
				if(tiles[x][y] == null || !tiles[x][y].isRoad()) continue;

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

		// round edges.
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++) {
				if(isTileAt(x, y)) {
					if(!isTileAt(x - 1, y) && !isTileAt(x, y - 1))
						tiles[x][y].texture = Assets.grass_bl;
					else if(!isTileAt(x - 1, y) && !isTileAt(x, y + 1))
						tiles[x][y].texture = Assets.grass_tl;
					else if(!isTileAt(x + 1, y) && !isTileAt(x, y - 1))
						tiles[x][y].texture = Assets.grass_br;
					else if(!isTileAt(x + 1, y) && !isTileAt(x, y + 1))
						tiles[x][y].texture = Assets.grass_tr;
				}
			}
	}

	public void render(int d) {
		Tools.batch.begin();
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				if(tiles[x][y] != null)
					Tools.batch.draw(tiles[x][y].getTex(), d + x * View.TILE_SIZE - View.getX(),
							y * View.TILE_SIZE - View.getY(), View.TILE_SIZE, View.TILE_SIZE);
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
		return tiles[x][y] != null;
	}

	public boolean isRoadAt(int x, int y) {
		return isTileAt(x, y) && tiles[x][y].isRoad();
	}
}
