package com.arcanetower.game;

import com.arcanetower.screens.MainGameScreen;
import com.arcanetower.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArcaneTower extends Game {
	
	public static final int SCREEN_WIDTH = 864;
	public static final int SCREEN_HEIGTH = 544;
	
	private SpriteBatch batch;
	private MainGameScreen gameScreen;
	private MainMenuScreen menuScreen;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		gameScreen = new MainGameScreen(this);
		menuScreen = new MainMenuScreen(this);
		this.setScreen(menuScreen);
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
	
	public SpriteBatch getBatch()
	{
		return this.batch;
	}
}
