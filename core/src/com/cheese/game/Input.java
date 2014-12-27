package com.cheese.game;

import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {
	public static Input inst = new Input();

	public static boolean rightButton;
	public static boolean leftButton;

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == com.badlogic.gdx.Input.Keys.A)
			leftButton = true;
		else if(keycode == com.badlogic.gdx.Input.Keys.D)
			rightButton = true;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == com.badlogic.gdx.Input.Keys.A)
			leftButton = false;
		else if(keycode == com.badlogic.gdx.Input.Keys.D)
			rightButton = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
