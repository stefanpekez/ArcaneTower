package com.arcanetower.screens;

import java.util.Random;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainGameScreen implements Screen {

	float x;
	float y;
	
	private ArcaneTower game;
	
	private OrthographicCamera camera;
	
	private Stage stage;
	private TerrainGenerator generator;
	
	public MainGameScreen(ArcaneTower game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		generator = new TerrainGenerator(stage);
		Gdx.input.setInputProcessor(stage);
		
		camera = new OrthographicCamera(800, 480);
		camera.setToOrtho(false, 800, 480);
		camera.position.set(800 / 2, 480 / 2, 0);
		camera.update();
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Gdx.gl.glViewport(0, 0, 800, 480);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}
