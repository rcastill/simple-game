package com.cheese.game;

import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class Audio {
	HashMap<String, Sound> sounds;
	HashMap<String, Boolean> played;

	public Audio() {
//		sounds.put("car", Assets.car);
	}

	public void playOnce(String sound) {
		if(played.containsKey(sound)) return;

		sounds.get(sound).play();
		played.put(sound, true);
	}
}
