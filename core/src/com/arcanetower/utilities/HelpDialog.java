package com.arcanetower.utilities;

import java.util.ArrayList;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HelpDialog {

	private Stage stageUI;
	
	private Image mainFrame;
	private ImageButton cancelButton;
	private MainGameScreen mainGameScreen;
	private Group groupDialog;
	private ArrayList<Enemy> enemies;
	
	public HelpDialog(Stage stageUI, MainGameScreen mainGameScreen, ArrayList<Enemy> enemies) {
		// TODO Auto-generated constructor stub
		this.stageUI = stageUI;
		
		this.mainFrame = new Image(new Texture(Gdx.files.internal("dialog448.png")));
		this.cancelButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("cancelButton.png"))));
		this.mainGameScreen = mainGameScreen;
		
//		this.groupDialog = new Group();
		this.enemies = enemies;
//		stageUI.addActor(groupTowers);
		
		
		
		setPositions();
		addActions();
		addToStage();
	}
	
	private void setPositions()
	{
		mainFrame.setPosition(ArcaneTower.SCREEN_WIDTH / 2 - mainFrame.getWidth() / 2, (ArcaneTower.SCREEN_HEIGTH / 2 - mainFrame.getHeight() / 2) - 32);
		cancelButton.setPosition(700, 412);
	}
	
	private void addToStage()
	{
//		groupDialog.addActor(mainFrame);
//		groupDialog.addActor(cancelButton);
//		groupDialog.setZIndex(20);
		stageUI.addActor(mainFrame);
		stageUI.addActor(cancelButton);
	}
	
	private void addActions()
	{
		
		cancelButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				for(Enemy e: enemies)
					e.setVisible(true);
				
				mainFrame.remove();
				cancelButton.remove();
				mainGameScreen.setGameSpeed(1);
			}
		});
	}
}
