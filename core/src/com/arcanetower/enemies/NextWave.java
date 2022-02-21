package com.arcanetower.enemies;

import java.util.ArrayList;

import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

public class NextWave {
	
	private Stage stage;
	private ArrayList<Tile> paths;
	private TerrainGenerator generatorTerrain;
	private float destinationX;
	private float destinationY;
	private final Image goblin;
	private Label remLives;

	public NextWave(Stage stage, BasicEnemy goblin, TerrainGenerator generatorTerrain, Label remLives)
	{
		this.stage = stage;
		this.goblin = goblin.getGoblin();
		this.generatorTerrain = generatorTerrain;
		this.paths = new ArrayList<Tile>();
		this.remLives = remLives;
		
		loadTiles();
		
		this.goblin.setPosition(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY());
		destinationX = generatorTerrain.getStartX();
		destinationY = generatorTerrain.getStartY();
		this.stage.addActor(this.goblin);
		GenerateMovement();
		
	}
	
	private void loadTiles()
	{
		for(Integer i: this.generatorTerrain.getListOfPathIds())
		{
			paths.add(this.generatorTerrain.getGridMap().get(i));
		}
	}
	
	public void GenerateMovement()
	{
		SequenceAction sequenceAction = new SequenceAction();
		for(int i = 0; i < paths.size(); ++i)
		{
			//Gets the next pathTile
			Tile t = paths.get(i);
			destinationX = t.getX();
			destinationY = t.getY();
			
			sequenceAction.addAction(Actions.moveTo(destinationX, destinationY, 0.07f));
		}
		
		sequenceAction.addAction(Actions.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SequenceAction deleteSequenceAction = new SequenceAction();
				deleteSequenceAction.addAction(Actions.moveBy(32, 0, 0.07f));
				deleteSequenceAction.addAction(Actions.run(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int life = Integer.parseInt(remLives.getText().toString());
						--life;
						remLives.setText(Integer.toString(life));
						goblin.remove();
						System.out.println("Izbrisan");
					}
				}));
				goblin.addAction(deleteSequenceAction);
			}
		}));
		
		
		this.goblin.addAction(sequenceAction);
	}
	
	public ArrayList<Tile> getPaths()
	{
		return this.paths;
	}
}
