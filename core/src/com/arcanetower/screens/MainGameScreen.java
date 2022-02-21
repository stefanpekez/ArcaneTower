package com.arcanetower.screens;

import java.util.Random;

import javax.swing.JOptionPane;

import com.arcanetower.enemies.BasicEnemy;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.arcanetower.ui.CreateFont;
import com.arcanetower.ui.InfoLabels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainGameScreen implements Screen {

	float x;
	float y;
	
	private ArcaneTower game;
	
	private OrthographicCamera camera;
	
	private Stage stage;
	private Stage stageUI;
	private TerrainGenerator generator;
	
	private Image infoBar;
	private InfoLabels infoLabels;
	
	private Dialog dialog;
	private boolean isFirst = true;
	
	public MainGameScreen(ArcaneTower game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera(ArcaneTower.SCREEN_WIDTH, ArcaneTower.SCREEN_HEIGTH);
		camera.setToOrtho(false, ArcaneTower.SCREEN_WIDTH, ArcaneTower.SCREEN_HEIGTH);
		camera.position.set(ArcaneTower.SCREEN_WIDTH / 2, ArcaneTower.SCREEN_HEIGTH / 2, 0);
		camera.update();
		
		stage = new Stage();
		stageUI = new Stage();
		generator = new TerrainGenerator(stage);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(stageUI);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		infoBar = new Image(new Texture("infobarNB.png"));
		infoBar.setPosition(0, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		
		stageUI.addActor(infoBar);
		
		infoLabels = new InfoLabels(stageUI, generator, this.game.getBatch());
		
//		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
//		
//		dialog = new Dialog("Game over", skin, "dialog")
//		{
//			public void result(Object obj) {
//		        System.out.println("result "+ obj);
//		    }
//		};
//		dialog.text("Game over");
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stageUI.act(delta);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		stageUI.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Gdx.gl.glViewport(0, 0, ArcaneTower.SCREEN_WIDTH, ArcaneTower.SCREEN_HEIGTH);
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
