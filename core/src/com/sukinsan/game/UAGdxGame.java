package com.sukinsan.game;

import com.badlogic.gdx.Game;
import com.sukinsan.screen.Play;

public class UAGdxGame extends Game{
	public Play play;

	@Override
	public void create () {
		play = new Play();
		setScreen(play);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
