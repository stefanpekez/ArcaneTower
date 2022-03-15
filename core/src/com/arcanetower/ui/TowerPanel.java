package com.arcanetower.ui;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.arcanetower.towers.TowerButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TowerPanel extends Image{
	
	private static TowerPanel instance = null;
	
	public static TowerPanel getInstance(Stage stage, MainGameScreen screen) {
		if(instance == null) {
			instance = new TowerPanel(stage, screen);
		}
		return instance;
	}
	
	private TextureRegionDrawable ballistaIcon;
	private TowerButton ballista;
	
	private Pixmap pm;
	
//	private Stage stage;
	private MainGameScreen screen;
	
	public TowerPanel()
	{
		
	}
	
	public TowerPanel(Stage stage, MainGameScreen screen) 
	{
		super(new Texture(Gdx.files.internal("towerpanelNB.png")));
		setPosition(ArcaneTower.SCREEN_WIDTH - 2 * 32, 0);
		
//		this.stage = stage;
		this.screen = screen;
		
		this.ballistaIcon = new TextureRegionDrawable(new Texture(Gdx.files.internal("ballistaIcon.png")));
		this.ballista = new TowerButton(this.ballistaIcon);
		this.pm = null;
		
		addActions();
		setPositions();
		addToStage();
	}
	
	public void addActions()
	{
		
		this.ballista.addListener(new ChangeListener()
		{
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Texture place = new Texture(Gdx.files.internal("ballistaRange.png"));
				if (!place.getTextureData().isPrepared()) {
					place.getTextureData().prepare();
				}
				pm = place.getTextureData().consumePixmap();
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 128, 128));
				ballista.setDisabled(true);
				System.out.println(ballista.isDisabled());
				screen.setGameSpeed(0);
			}
		});
	}
	
	public void setPositions()
	{
		this.ballista.setPosition(ArcaneTower.SCREEN_WIDTH - 2 * 32 + 8, this.getHeight() - 64);
	}
	
	public void addToStage()
	{
	}
	
	public TowerButton getBallista()
	{
		return this.ballista;
	}
	
	public Pixmap getPixmap()
	{
		return this.pm;
	}
}
