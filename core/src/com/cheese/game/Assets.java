package com.cheese.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
	public static final String THEME = "road";

	public static Texture road_s_d;
	public static Texture road_s_r;
	public static Texture road_s_l;
	public static Texture road_s_u;
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

	public static BitmapFont font;

	static {
		road_s_d	= new Texture(THEME + "/road-stop-down.png");
		road_s_l	= new Texture(THEME + "/road-stop-left.png");
		road_s_r	= new Texture(THEME + "/road-stop-right.png");
		road_s_u	= new Texture(THEME + "/road-stop-up.png");
		road_t_b 	= new Texture(THEME + "/road-downy.png");
		road_t_u 	= new Texture(THEME + "/road-upty.png");
		road_t_l 	= new Texture(THEME + "/road-righty.png");
		road_t_r 	= new Texture(THEME + "/road-lefty.png");
		road_tr 	= new Texture(THEME + "/road-corner-upright.png");
		road_tl 	= new Texture(THEME + "/road-corner-upleft.png");
		road_br 	= new Texture(THEME + "/road-corner-downright.png");
		road_bl 	= new Texture(THEME + "/road-corner-downleft.png");
		road_v 		= new Texture(THEME + "/road-vertical.png");
		road_h 		= new Texture(THEME + "/road-horizontal.png");
		road_i		= new Texture(THEME + "/road-inter.png");

		grass_bl	= new Texture(THEME + "/corner-4.png");
		grass_br	= new Texture(THEME + "/corner-3.png");
		grass_tl	= new Texture(THEME + "/corner-1.png");
		grass_tr	= new Texture(THEME + "/corner-2.png");
		tree_1 		= new Texture(THEME + "/tree-1.png");
		tree_2 		= new Texture(THEME + "/tree-2.png");
		grass 		= new Texture(THEME + "/grass.png");

		player1 	= new Texture(THEME + "/truck-blue.png");
		player2 	= new Texture(THEME + "/truck-red.png");

		smoke		= new Texture(THEME + "/smoke.png");

		font = new BitmapFont(Gdx.files.internal("fonts/century_gothic.fnt"));
		font.setScale(0.75f, 0.75f);
	}
}
