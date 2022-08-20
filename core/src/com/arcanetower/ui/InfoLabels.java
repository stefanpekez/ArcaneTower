package com.arcanetower.ui;

import java.util.ArrayList;
import java.util.Random;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.enemies.NextWave;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.towers.TowerButton;
import com.arcanetower.utilities.HelpDialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class InfoLabels {
	
	private CreateFont customFont;
	
	private Label waveInfo;
	private Label currentWave;
	private Label separator;
	private Label maxWave;
	
	private Image goldImage;
	private Label goldNumber;
	private Label currency;
	
	private Image lives;
	private Label remainingLives;
	
//	private ImageButton startWave;
	private Image pauseButton;
	private Image playButton;
	private Image forwardButton;
	
	private Stage stageUI;
	private Stage stage;
	private final int startX;
	private final int startY;
	
	private NextWave nextWave;
	private TerrainGenerator generatorTerrain;
	
	private int waveCounter;
	
	private ImageButton anim;
	private MainGameScreen screen;
	private int enemyAmount;
	private Image pressSpace;
	private boolean shootFireworks;
	
	private Sound fireworksTrail;
	private Sound fireworksBang;
	
	private TextureRegionDrawable inactivePause;
	private TextureRegionDrawable activePause;
	
	private TextureRegionDrawable inactivePlay;
	private TextureRegionDrawable activePlay;
	
	private TextureRegionDrawable inactiveForward;
	private TextureRegionDrawable activeForward;
	
	public InfoLabels(Stage stageUI, TerrainGenerator generatorTerrain, SpriteBatch batch, 
			MainGameScreen mainGameScreen, Stage stage, TowerButton ballista)
	{
		customFont = new CreateFont();
		this.waveInfo = new Label("Wave:", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.currentWave = new Label("0", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.separator = new Label("/", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.maxWave = new Label("5", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		
		this.goldImage = new Image(new Texture(Gdx.files.internal("coin24.png")));
		this.goldNumber = new Label("100", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		this.currency = new Label("$", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		
		this.generatorTerrain = generatorTerrain;
		
		this.startX = this.generatorTerrain.getStartX();
		this.startY = this.generatorTerrain.getStartY();
		
		this.waveCounter = 0;
		
		this.lives = new Image(new Texture(Gdx.files.internal("heart24.png")));
		this.remainingLives = new Label("20", new Label.LabelStyle(customFont.getTextFont(), Color.LIGHT_GRAY));
		
		inactivePause = new TextureRegionDrawable(new Texture(Gdx.files.internal("pause32.png")));
		activePause = new TextureRegionDrawable(new Texture(Gdx.files.internal("pause32Active.png")));
		this.pauseButton = new Image(inactivePause);
		
		inactivePlay = new TextureRegionDrawable(new Texture(Gdx.files.internal("play32.png")));
		activePlay = new TextureRegionDrawable(new Texture(Gdx.files.internal("play32Active.png")));
		this.playButton = new Image(inactivePlay);
		
		inactiveForward = new TextureRegionDrawable(new Texture(Gdx.files.internal("fast36.png")));
		activeForward = new TextureRegionDrawable(new Texture(Gdx.files.internal("fast36Active.png")));
		this.forwardButton = new Image(inactiveForward);
		
		this.stageUI = stageUI;
		this.stage = stage;
		
		this.screen = mainGameScreen;
		this.enemyAmount = 0;
		
		this.nextWave = new NextWave(stageUI, generatorTerrain, remainingLives, waveCounter);
		
		anim = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("start24Anim.png"))));
		anim.setTransform(true);
		anim.addAction(Actions.forever(Actions.sequence(
				Actions.scaleBy(0.5f, 0.5f, 0.7f), 
				Actions.scaleBy(-0.5f, -0.5f, 0.7f))));
		
		this.pressSpace = new Image(new Texture(Gdx.files.internal("pressSpace.png")));
		pressSpace.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(1f), Actions.fadeIn(1f))));
		
		this.shootFireworks = true;
		this.fireworksTrail = Gdx.audio.newSound(Gdx.files.internal("effects\\fireworksSound.ogg"));
		this.fireworksBang = Gdx.audio.newSound(Gdx.files.internal("effects\\fireworksBang.ogg"));
		
		setPositions();
		setHovering();
		addActionToButtons(this.generatorTerrain, this.remainingLives, this.screen);
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
		this.pauseButton.setPosition(ArcaneTower.SCREEN_WIDTH - 5 * 32 - 16, ArcaneTower.SCREEN_HEIGTH - 2 * 32 + 15);
		this.playButton.setPosition(pauseButton.getX() + 36, ArcaneTower.SCREEN_HEIGTH - 2 * 32 + 15);
		this.forwardButton.setPosition(playButton.getX() + 36, ArcaneTower.SCREEN_HEIGTH - 2 * 32 + 13);
		
		this.anim.setPosition(startX + (32 - 24) / 2, startY + (32 - 24) / 2);
		
		this.pressSpace.setPosition(5 * 32 + 16, 1 * 32);
		
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
		stageUI.addActor(pauseButton);
		stageUI.addActor(playButton);
		stageUI.addActor(forwardButton);
		stage.addActor(anim);
		stageUI.addActor(pressSpace);
	}
	
	private void addActionToButtons(final TerrainGenerator generatorTerrain, final Label remainingLives, final MainGameScreen screen)
	{
		
		this.playButton.addListener(new ActorGestureListener()
		{

			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
//				if(screen.getEnemySpeed() == 1)
//					return;
				if(screen.getGameSpeed() == 1)
					return;
				
				System.out.println("play");
				
				screen.setGameSpeed(1);
//				screen.setEnemySpeed(1);
			}
		});
		
		this.pauseButton.addListener(new ActorGestureListener()
		{
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
//				if(screen.getEnemySpeed() == 0)
//					return;
				
				if(screen.getGameSpeed() == 0)
					return;
				
				System.out.println("pause");
				screen.setGameSpeed(0);
//				screen.setEnemySpeed(0);
			}
		});
		
		this.forwardButton.addListener(new ActorGestureListener()
		{
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
//				if(screen.getEnemySpeed() == 2)
//					return;
				if(screen.getGameSpeed() == 2)
					return;
							
				System.out.println("fast");
				screen.setGameSpeed(2);
//				screen.setEnemySpeed(2);
			}
		});
		
		this.anim.addListener(new ActorGestureListener() {
			
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				if(screen.getGameSpeed() == 0)
				{
					return;
				}
				if(nextWave.getCurrentWave() + 1 == Integer.parseInt(maxWave.getText().toString()) + 1)
				{
					return;
				}
				pressSpace.setVisible(false);
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("effects\\buttonClick.ogg"));
				sound.play();
				nextWave.loadEnemies();
				currentWave.setText(nextWave.getCurrentWave());
				anim.setVisible(false);
				anim.setDisabled(true);
			}
		});
		
		this.stageUI.addListener(new InputListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				switch (keycode) {
				case Input.Keys.SPACE:
					if(screen.getGameSpeed() == 0)
						return false;
					
					if(pressSpace.isVisible())
					{
						if(nextWave.getCurrentWave() + 1 == Integer.parseInt(maxWave.getText().toString()) + 1)
						{
							return false;
						}
						pressSpace.setVisible(false);
						Sound sound = Gdx.audio.newSound(Gdx.files.internal("effects\\buttonClick.ogg"));
						sound.play();
						nextWave.loadEnemies();
						currentWave.setText(nextWave.getCurrentWave());
						anim.setVisible(false);
						anim.setDisabled(true);
						return true;
					}
					return false;
			    case Input.Keys.NUM_1:
			    	if(screen.getEnemySpeed() == 0)
			    		return true;
			    	
				    System.out.println("pause");
				    screen.setGameSpeed(0);
				    screen.setEnemySpeed(0);
			    	return true;
			    case Input.Keys.NUM_2:
			    	if(screen.getEnemySpeed() == 1)
			    		return true;
			    	
			    	System.out.println("play");
				    screen.setGameSpeed(1);
				    screen.setEnemySpeed(1);
			    	return true;
			    case Input.Keys.NUM_3:
			    	if(screen.getEnemySpeed() == 2)
			    		return true;
				    System.out.println("Faster");
				    screen.setGameSpeed(1);
				    screen.setEnemySpeed(2);
			    	return true;
			    }
				return false;
			}
		});
		
	}
	
	public int getCurrentWave()
	{
		return Integer.parseInt(currentWave.getText().toString());
	}
	
	public int getMaxWave()
	{
		return Integer.parseInt(maxWave.getText().toString());
	}
	
	public Label getRemainingLives()
	{
		return this.remainingLives;
	}
	
	public void setWaveButtonPause()
	{
		pressSpace.setVisible(true);
		anim.setVisible(true);
		anim.setDisabled(false);
	}
	
	public void setWaveButtonPlay()
	{
		pressSpace.setVisible(true);
		anim.setVisible(true);
		anim.setDisabled(false);
	}
	
	public void setEnemyAmount(int enemyAmount)
	{
		this.enemyAmount = enemyAmount;
	}
	
	public int getEnemyAmount()
	{
		return this.enemyAmount;
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return this.nextWave.getEnemies();
	}
	
	public void addMoney(int amount)
	{
		this.goldNumber.setText(Integer.toString(Integer.parseInt(this.goldNumber.getText().toString()) + amount));
	}
	
	public boolean removeMoney(int amount)
	{
		if((Integer.parseInt(this.goldNumber.getText().toString()) - amount) < 0)
		{
			System.out.println("No money");
			return false;
		} else
		{
			this.goldNumber.setText(Integer.toString(Integer.parseInt(this.goldNumber.getText().toString()) - amount));
			return true;
		}
	}
	
	public int getMoney()
	{
		return Integer.parseInt(this.goldNumber.getText().toString());
	}
	
	public void setFireworks()
	{
		if(shootFireworks)
		{
			for(int i = 0; i < 50; ++i)
				shootFireworks();
			shootFireworks = false;
		}
	}
	
	public void shootFireworks()
	{
		final Random rand = new Random();
		final int randomX = rand.nextInt((23 - 1) + 1) + 1;
		final int randomY = rand.nextInt((14 - 1) + 1) + 1;
		screen.setEnemySpeed(1);
		screen.setGameSpeed(1);
		screen.getMusic().stop();
		
		final Image fireworkTrail = new Image(new Texture(Gdx.files.internal("fireworkTrail.png")));
		fireworkTrail.setPosition((randomX) * 32, -32);
		stageUI.addActor(fireworkTrail);
		
		fireworkTrail.addAction(Actions.sequence(Actions.delay(rand.nextInt((15 - 0) + 1) + 0 * 0.5f), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				fireworksTrail.play();
			}
		}), Actions.moveTo((randomX) * 32, (randomY) * 32, 1f), Actions.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				fireworksBang.play();
				fireworkTrail.remove();
				final Image fireworks = new Image(new Texture(Gdx.files.internal("fireworks.png")));
				fireworks.setPosition((randomX) * 32 - 54, (randomY) * 32 - 54);
				stageUI.addActor(fireworks);
				fireworks.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						fireworks.remove();
					}
				})));
			}
		})));
	}
	
	private void setHovering()
	{
		this.pauseButton.addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				// TODO Auto-generated method stub
				pauseButton.setDrawable(activePause);
			}
					
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				// TODO Auto-generated method stub
				pauseButton.setDrawable(inactivePause);
			}
		});
		
		this.playButton.addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				// TODO Auto-generated method stub
				playButton.setDrawable(activePlay);
			}
					
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				// TODO Auto-generated method stub
				playButton.setDrawable(inactivePlay);
			}
		});
		
		this.forwardButton.addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				// TODO Auto-generated method stub
				forwardButton.setDrawable(activeForward);
			}
					
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				// TODO Auto-generated method stub
				forwardButton.setDrawable(inactiveForward);
			}
		});
	}
	
}
