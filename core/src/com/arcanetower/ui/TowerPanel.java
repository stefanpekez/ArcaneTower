package com.arcanetower.ui;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.arcanetower.towers.TowerButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	
	private InfoLabels infoLabels;
	
	private TextureRegionDrawable inactiveBallista;
	private TextureRegionDrawable activeBallista;
	
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
		
		inactiveBallista = new TextureRegionDrawable(new Texture(Gdx.files.internal("ballistaIcon.png")));
		activeBallista = new TextureRegionDrawable(new Texture(Gdx.files.internal("ballistaIconActive.png")));
		
		this.ballista = new TowerButton(inactiveBallista);
		this.pm = null;
		
		addActions();
		setPositions();
		addToStage();
	}
	
	public void addActions()
	{
		
		this.ballista.addListener(new ClickListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				// TODO Auto-generated method stub
				ballista.setDrawable(activeBallista);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				// TODO Auto-generated method stub
				ballista.setDrawable(inactiveBallista);
			}
		});
		
		this.ballista.addListener(new ActorGestureListener()
		{
			@Override
			public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				if(infoLabels.getMoney() - 50 < 0)
					return;
				
				Texture place = new Texture(Gdx.files.internal("ballistaRange.png"));
				if (!place.getTextureData().isPrepared()) {
					place.getTextureData().prepare();
				}
				ballista.setSpeedBeforeSelect(screen.getGameSpeed());
				pm = place.getTextureData().consumePixmap();
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 128, 128));
				ballista.setDisabled(true);
//				System.out.println(ballista.isDisabled());
				screen.setGameSpeed(0);
//				screen.setEnemySpeed(0);
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
	
	public void setInfoLabels(InfoLabels infoLabels)
	{
		this.infoLabels = infoLabels;
	}
}
