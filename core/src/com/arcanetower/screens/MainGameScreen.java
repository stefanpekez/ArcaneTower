package com.arcanetower.screens;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.towers.BallistaTower;
import com.arcanetower.ui.InfoLabels;
import com.arcanetower.ui.TowerPanel;
import com.arcanetower.utilities.ArrowBallista;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
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
	private Stage stageUI;
	private Stage stageDialog;
	
	private TerrainGenerator generator;
	private Image infoBar;
	private InfoLabels infoLabels;
	
	private Image corner;
	private TowerPanel towerPanel;
	private Group groupTowers;
	
	private int gameSpeed;
	
	private Music gameMusic;
	
	private int speed;
	
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
		
		this.gameMusic = Gdx.audio.newMusic(Gdx.files.internal("effects\\gameMusicNew.ogg"));
		gameMusic.setLooping(true);
		gameMusic.play();
		
		this.gameSpeed = 1;
		
		stage = new Stage();
		stageUI = new Stage();
		stageDialog = new Stage();
		towerPanel = new TowerPanel(stageUI, this);
		generator = new TerrainGenerator(stage, towerPanel, this, stageUI);
		
		infoBar = new Image(new Texture(Gdx.files.internal("infobarNB.png")));
		infoBar.setPosition(0, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		
		stageUI.addActor(infoBar);
		
		infoLabels = new InfoLabels(stageUI, generator, this.game.getBatch(), this, stage, towerPanel.getBallista());
		
		towerPanel.setInfoLabels(infoLabels);
		
		generator.setInfoLabels(infoLabels);
		
		corner = new Image(new Texture(Gdx.files.internal("corner.png")));
		corner.setPosition(ArcaneTower.SCREEN_WIDTH - 2 * 32, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		corner.addListener(new ClickListener()
				{
					@Override
					public void clicked(InputEvent event, float x, float y) {
						// TODO Auto-generated method stub
						gameMusic.stop();
					}
				});
		
		stageUI.addActor(corner);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(stageUI);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		// Makes it so that the enemies go behind the tower panel
		this.groupTowers = new Group();
		stageUI.addActor(groupTowers);
		
		groupTowers.addActor(towerPanel);
		groupTowers.addActor(towerPanel.getBallista());
		groupTowers.setZIndex(2);
		
		
		
		this.speed = 1;
		
		stage.addListener(new ClickListener(Buttons.RIGHT) {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
				towerPanel.getBallista().setDisabled(false);
				setGameSpeed(1);
			}
		});
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		switch(gameSpeed)
		{
			// PAUSE
			case 0:
				stage.act(delta * speed);
				
				for(BallistaTower bt: generator.getPlacedTowers().getPlacedTowers())
				{
					bt.stopTimer();
				}
				
				if(infoLabels.getEnemyAmount() == 0 && infoLabels.getMaxWave() != infoLabels.getCurrentWave())
				{
					
					if(infoLabels.getMaxWave() == infoLabels.getCurrentWave())
					{
					}
					else
					{
						infoLabels.setWaveButtonPause();
					}
				}
				
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				stage.draw();
				stageUI.draw();
				break;
			// RUN
			case 1:
				stage.act(delta * speed);
				stageUI.act(delta * speed);
				
				for(BallistaTower bt: generator.getPlacedTowers().getPlacedTowers())
				{
					bt.resumeTimer(this.speed);
				}
				
				Gdx.gl.glClearColor(1, 1, 1, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				infoLabels.setEnemyAmount(infoLabels.getEnemies().size());
				
				if(infoLabels.getEnemies().size() > 0)
					generator.setGoblins(infoLabels.getEnemies());
				
				if(infoLabels.getEnemyAmount() == 0 && infoLabels.getMaxWave() == infoLabels.getCurrentWave())
				{
					infoLabels.setFireworks();
				}
				
				if(infoLabels.getEnemyAmount() == 0 && infoLabels.getMaxWave() != infoLabels.getCurrentWave())
				{
					
					if(infoLabels.getMaxWave() == infoLabels.getCurrentWave())
					{
					}
					else
					{
						infoLabels.setWaveButtonPlay();
					}
				}
				
				stage.draw();
				stageUI.draw();
				break;
			// TODO: DIALOG
			case 2:
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
	
	public int getGameSpeed()
	{
		return this.gameSpeed;
	}
	
	public void setEnemySpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getEnemySpeed()
	{
		return this.speed;
	}
	
	public Music getMusic()
	{
		return this.gameMusic;
	}

}
