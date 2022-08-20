package com.arcanetower.menubackground;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenu {
	
	private Image playButton;
	private Image exitButton;
	
	private Stage stageMenu;
	private Image gameLogo; 
	private ArcaneTower game;
	
	private TextureRegionDrawable inactivePlay;
	private TextureRegionDrawable activePlay;
	
	private TextureRegionDrawable inactiveExit;
	private TextureRegionDrawable activeExit;
	private Music menuTheme;
	
	public MainMenu(Image gameLogo, final Stage stageMenu, final ArcaneTower game, final Music menuTheme) {
		// TODO Auto-generated constructor stub
		this.stageMenu = stageMenu;
		this.gameLogo = gameLogo;
		this.game = game;
		this.menuTheme = menuTheme;
		
		inactivePlay = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\playInactive.png")));
		activePlay = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\playActive.png")));
		
		inactiveExit = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\exitInactive.png")));
		activeExit = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\exitActive.png")));
		
		playButton = new Image(inactivePlay);
		exitButton = new Image(inactiveExit);
		
		playButton.setPosition(gameLogo.getX() + 300, gameLogo.getY() - 96);
		exitButton.setPosition(playButton.getX(), playButton.getY() - 96);
		
		stageMenu.addActor(playButton);
		stageMenu.addActor(exitButton);
		
		playButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				menuTheme.stop();
				game.setScreen(new MainGameScreen(game));
			}
			
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
		
		exitButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Gdx.app.exit();
			}
			
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				// TODO Auto-generated method stub
				exitButton.setDrawable(activeExit);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				// TODO Auto-generated method stub
				exitButton.setDrawable(inactiveExit);
			}
		});
		
	}

}
