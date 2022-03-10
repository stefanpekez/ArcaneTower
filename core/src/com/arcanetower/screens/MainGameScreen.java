package com.arcanetower.screens;

import java.util.Random;

import javax.swing.JOptionPane;

import com.arcanetower.enemies.BasicEnemy;
import com.arcanetower.enemies.NextWave;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.arcanetower.ui.CreateFont;
import com.arcanetower.ui.InfoLabels;
import com.arcanetower.ui.TowerPanel;
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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
	
	private Image corner;
	
	private Image towerBar;
	private TowerPanel towerPanel;
	
	private Group groupTowers;
	
	private int gameSpeed;
	
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
		
		this.gameSpeed = 1;
		
		stage = new Stage();
		stageUI = new Stage();
		towerPanel = new TowerPanel(stageUI, this);
		generator = new TerrainGenerator(stage, towerPanel, this);
		
		infoBar = new Image(new Texture("infobarNB.png"));
		infoBar.setPosition(0, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		
		stageUI.addActor(infoBar);
		
		infoLabels = new InfoLabels(stageUI, generator, this.game.getBatch(), this, stage);
		
		corner = new Image(new Texture("corner.png"));
		corner.setPosition(ArcaneTower.SCREEN_WIDTH - 2 * 32, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		
		stageUI.addActor(corner);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(stageUI);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		this.groupTowers = new Group();
		stageUI.addActor(groupTowers);
		
		groupTowers.addActor(towerPanel);
		groupTowers.addActor(towerPanel.getBallista());
		groupTowers.setZIndex(2);
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		switch(gameSpeed)
		{
			//PAUSE
			case 0:
				stage.act(delta);
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				stage.draw();
				stageUI.draw();
				break;
			//RUN
			case 1:
				stage.act(delta);
				stageUI.act(delta);
				
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				stage.draw();
				stageUI.draw();
				break;
		}
		
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
	
	public void setGameSpeed(int speed)
	{
		this.gameSpeed = speed;
	}

}
