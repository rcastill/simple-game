package com.cheese.game;

import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Texture road_t_b;
	public static Texture road_t_u;
	public static Texture road_t_l;
	public static Texture road_t_r;
	public static Texture road_tr;
	public static Texture road_tl;
	public static Texture road_br;
	public static Texture road_bl;
	public static Texture road_v;
	public static Texture road_h;
	public static Texture road_i;

	public static Texture grass_bl;
	public static Texture grass_br;
	public static Texture grass_tl;
	public static Texture grass_tr;
	public static Texture grass;
	public static Texture tree_1;
	public static Texture tree_2;

	public static Texture player1;
	public static Texture player2;

	public static Texture smoke;

	static {
		road_t_b 	= new Texture("imgs/road-downy.png");
		road_t_u 	= new Texture("imgs/road-upty.png");
		road_t_l 	= new Texture("imgs/road-righty.png");
		road_t_r 	= new Texture("imgs/road-lefty.png");
		road_tr 	= new Texture("imgs/road-corner-upright.png");
		road_tl 	= new Texture("imgs/road-corner-upleft.png");
		road_br 	= new Texture("imgs/road-corner-downright.png");
		road_bl 	= new Texture("imgs/road-corner-downleft.png");
		road_v 		= new Texture("imgs/road-vertical.png");
		road_h 		= new Texture("imgs/road-horizontal.png");
		road_i		= new Texture("imgs/road-inter.png");

		grass_bl	= new Texture("imgs/corner-4.png");
		grass_br	= new Texture("imgs/corner-3.png");
		grass_tl	= new Texture("imgs/corner-1.png");
		grass_tr	= new Texture("imgs/corner-2.png");
		tree_1 		= new Texture("imgs/tree-1.png");
		tree_2 		= new Texture("imgs/tree-2.png");
		grass 		= new Texture("imgs/grass.png");

		player1 	= new Texture("imgs/truck-blue.png");
		player2 	= new Texture("imgs/truck-red.png");

		smoke		= new Texture("imgs/smoke.png");
	}
}
