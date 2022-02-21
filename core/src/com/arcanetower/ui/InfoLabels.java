package com.arcanetower.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.arcanetower.enemies.GenerateEnemies;
import com.arcanetower.enemies.NextWave;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.terrain.TerrainGenerator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class InfoLabels {
	
	private Label waveInfo;
	private Label currentWave;
	private Label separator;
	private Label maxWave;
	
	private Image goldImage;
	private Label goldNumber;
	private Label currency;
	
	private Image lives;
	private Label remainingLives;
	
	private ImageButton playButton;
	private ImageButton forwardButton;
	
	private Stage stageUI;
	private final int startX;
	private final int startY;
	
	private GenerateEnemies generatorEnemy;
	private NextWave nextWave;
	private TerrainGenerator generatorTerrain;
	
	private int waveCounter;
	
	private boolean nextWaveCreated = false;
	
	public InfoLabels(Stage stageUI, TerrainGenerator generatorTerrain, SpriteBatch batch)
	{
		CreateFont customFont = new CreateFont();
		this.waveInfo = new Label("Wave:", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.currentWave = new Label("0", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.separator = new Label("/", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.maxWave = new Label("5", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		
		this.goldImage = new Image(new Texture("coin24.png"));
		this.goldNumber = new Label("0", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.currency = new Label("$", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		
		this.generatorTerrain = generatorTerrain;
		
		this.startX = this.generatorTerrain.getStartX();
		this.startY = this.generatorTerrain.getStartY();
		
		this.waveCounter = 0;
		
		this.lives = new Image(new Texture("heart24.png"));
		this.remainingLives = new Label("20", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		
		Drawable drawablePlay = new TextureRegionDrawable(new Texture("play32.png"));
		this.playButton = new ImageButton(drawablePlay);
		
		Drawable drawableForward = new TextureRegionDrawable(new Texture("forward32.png"));
		this.forwardButton = new ImageButton(drawableForward);
		
		this.stageUI = stageUI;
		setPositions();
		addActionToButtons(this.generatorTerrain, this.remainingLives);
		addToStage();
	}
	
	private void setPositions()
	{
		this.waveInfo.setPosition(32, ArcaneTower.SCREEN_HEIGTH - 1 * 32);
		this.currentWave.setPosition(waveInfo.getX() + waveInfo.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 1 * 32);
		this.separator.setPosition(currentWave.getX() + currentWave.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 1 * 32);
		this.maxWave.setPosition(separator.getX() + separator.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 1 * 32);
		this.goldImage.setPosition(32, ArcaneTower.SCREEN_HEIGTH - 2 * 32 + 5);
		this.goldNumber.setPosition(goldImage.getX() + goldImage.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		this.currency.setPosition(goldNumber.getX() + goldNumber.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 2 * 32);
		this.lives.setPosition(this.maxWave.getX() + this.maxWave.getWidth() + 100, ArcaneTower.SCREEN_HEIGTH - 1 * 32 + 4);
		this.remainingLives.setPosition(this.lives.getX() + this.lives.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 1 * 32);
		this.playButton.setPosition(ArcaneTower.SCREEN_WIDTH - 3 * 32, ArcaneTower.SCREEN_HEIGTH - 2 * 32 + 15);
		this.forwardButton.setPosition(playButton.getX() + playButton.getWidth() + 10, ArcaneTower.SCREEN_HEIGTH - 2 * 32 + 15);
	}
	
	private void addToStage()
	{
		stageUI.addActor(waveInfo);
		stageUI.addActor(currentWave);
		stageUI.addActor(separator);
		stageUI.addActor(maxWave);
		stageUI.addActor(goldImage);
		stageUI.addActor(goldNumber);
		stageUI.addActor(currency);
		stageUI.addActor(lives);
		stageUI.addActor(remainingLives);
		stageUI.addActor(playButton);
		stageUI.addActor(forwardButton);
	}
	
	private void addActionToButtons(final TerrainGenerator generatorTerrain, final Label remainingLives)
	{
		this.playButton.addListener(new ActorGestureListener()
		{
			
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				if(waveCounter + 1 == 6)
				{
					return;
				}
				++waveCounter;
				currentWave.setText(Integer.toString(waveCounter));
				
				generatorEnemy = new GenerateEnemies(startX, startY, waveCounter);
				nextWave = new NextWave(stageUI, generatorEnemy.getGoblin(), generatorTerrain, remainingLives);
				
			}
		});
		
	}
	
	public Label getRemainingLives()
	{
		return this.remainingLives;
	}
	
}
