package com.arcanetower.screens;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.menubackground.MainMenu;
import com.arcanetower.menubackground.MenuBackground;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MainMenuScreen implements Screen{
	
	private static final int BUTTON_WIDTH = 128;
	private static final int BUTTON_HEIGHT = 64;
	private static final int PLAY_BUTTON_Y_POS = 132;
	private static final int EXIT_BUTTON_Y_POS = 264;
	
	private ArcaneTower game;
	
	private Stage stageMenu;
	private Stage stageBackground;
	private Image gameLogo;
	
	private MainMenu mainMenu;
	private MenuBackground gameBackground;
	
	private OrthographicCamera camera;
	
	private Music menuMusic;
	
	public MainMenuScreen(ArcaneTower game) {
		this.game = game;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera(ArcaneTower.SCREEN_WIDTH, ArcaneTower.SCREEN_HEIGTH);
		camera.setToOrtho(false, ArcaneTower.SCREEN_WIDTH, ArcaneTower.SCREEN_HEIGTH);
		camera.position.set(ArcaneTower.SCREEN_WIDTH / 2, ArcaneTower.SCREEN_HEIGTH / 2, 0);
		camera.update();
		
		this.menuMusic = Gdx.audio.newMusic(Gdx.files.internal("effects\\menuTheme.ogg"));
		this.menuMusic.setLooping(true);
		this.menuMusic.play();
		
		stageBackground = new Stage();
		
		gameBackground = new MenuBackground(stageBackground);
		
		stageMenu = new Stage();
		
		gameLogo = new Image(new Texture(Gdx.files.internal("menu\\ArcaneTowerLogo.png")));
		gameLogo.setPosition(ArcaneTower.SCREEN_WIDTH / 2 - gameLogo.getWidth() / 2, 275);
		
		stageMenu.addActor(gameLogo);
		
		mainMenu = new MainMenu(gameLogo, stageMenu, game, this.menuMusic);
		
		Gdx.input.setInputProcessor(stageMenu);
		
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stageMenu);
		inputMultiplexer.addProcessor(stageBackground);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stageMenu.act(delta);
		stageBackground.act(delta);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameBackground.getNextWave().setEnemyAmount(gameBackground.getNextWave().getEnemies().size());
		
		if(gameBackground.getNextWave().getEnemies().size() > 0)
		{
			gameBackground.getGenerator().setEnemies(gameBackground.getNextWave().getEnemies());
		}
			
		
		if(gameBackground.getNextWave().getEnemyAmount() == 0 && gameBackground.getNextWave().getMaxWave() != gameBackground.getNextWave().getCurrentWave())
		{
			
			if(gameBackground.getNextWave().getMaxWave() == gameBackground.getNextWave().getCurrentWave())
			{
			}
			else
			{
				gameBackground.getNextWave().loadEnemies();
			}
		}
		
		stageBackground.draw();
		stageMenu.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
	}
}
