package com.arcanetower.screens;

import com.arcanetower.game.ArcaneTower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainMenuScreen implements Screen{
	
	private static final int BUTTON_WIDTH = 128;
	private static final int BUTTON_HEIGHT = 64;
	private static final int PLAY_BUTTON_Y_POS = 132;
	private static final int EXIT_BUTTON_Y_POS = 264;
	
	private ArcaneTower game;
	
	private Sprite playActiveSprite;
	private Sprite playInactiveSprite;
	private Sprite exitActiveSprite;
	private Sprite exitInactiveSprite;
	
	private OrthographicCamera camera;
	
	public MainMenuScreen(ArcaneTower game) {
		this.game = game;
		playActiveSprite = new Sprite(new Texture(Gdx.files.internal("playActive.png")));
		playActiveSprite.setOrigin(0, 0);
		playActiveSprite.setPosition(ArcaneTower.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, PLAY_BUTTON_Y_POS);
		
		playInactiveSprite = new Sprite(new Texture(Gdx.files.internal("playInactive.png")));
		playInactiveSprite.setOrigin(0, 0);
		playInactiveSprite.setPosition(ArcaneTower.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, PLAY_BUTTON_Y_POS);
		
		exitActiveSprite = new Sprite(new Texture(Gdx.files.internal("exitActive.png")));
		exitActiveSprite.setOrigin(0, 0);
		exitActiveSprite.setPosition(ArcaneTower.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, EXIT_BUTTON_Y_POS);
		
		exitInactiveSprite = new Sprite(new Texture(Gdx.files.internal("exitInactive.png")));
		exitInactiveSprite.setOrigin(0, 0);
		exitInactiveSprite.setPosition(ArcaneTower.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2, EXIT_BUTTON_Y_POS);
		
		playInactiveSprite.flip(false, true);
		playActiveSprite.flip(false, true);
		
		exitActiveSprite.flip(false, true);
		exitInactiveSprite.flip(false, true);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		camera = new OrthographicCamera(ArcaneTower.SCREEN_WIDTH, ArcaneTower.SCREEN_HEIGTH);
		camera.setToOrtho(true);
		camera.position.set(ArcaneTower.SCREEN_WIDTH / 2, ArcaneTower.SCREEN_HEIGTH / 2, 0);
		camera.update();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.getBatch().setProjectionMatrix(camera.combined);
		game.getBatch().begin();
		
		int x = ArcaneTower.SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2;
		
		if(Gdx.input.getX() > x && Gdx.input.getX() < x + BUTTON_WIDTH 
				&& Gdx.input.getY() < PLAY_BUTTON_Y_POS + BUTTON_HEIGHT && Gdx.input.getY() > PLAY_BUTTON_Y_POS)
		{
			
			playActiveSprite.draw(game.getBatch());
			if(Gdx.input.isTouched()) {
				game.setScreen(new MainGameScreen(game));
			}
			
		} else playInactiveSprite.draw(game.getBatch());
		
		if(Gdx.input.getX() > x && Gdx.input.getX() < x + BUTTON_WIDTH 
				&& Gdx.input.getY() < EXIT_BUTTON_Y_POS + BUTTON_HEIGHT && Gdx.input.getY() > EXIT_BUTTON_Y_POS)
		{
			
			exitActiveSprite.draw(game.getBatch());
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
			
		} else exitInactiveSprite.draw(game.getBatch());
		
		game.getBatch().end();
		
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
