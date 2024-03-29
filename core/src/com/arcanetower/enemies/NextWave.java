package com.arcanetower.enemies;

import java.util.ArrayList;

import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Tile;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class NextWave {
	
	private GenerateEnemies generatorEnemies;
	private Stage stage;
	private ArrayList<Tile> paths;
	private TerrainGenerator generatorTerrain;
	private float destinationX;
	private float destinationY;
	private ArrayList<Enemy> enemies;
	private Label remLives;
	private Group groupEnemies;
	private SequenceAction sequenceAction;
	private int currentWave;
//	private Label gold;
	private int tileID;
	private ArrayList<Tile> pathsNew;

	public NextWave(Stage stage, TerrainGenerator generatorTerrain, Label remLives, int currentWave)
	{
		this.generatorEnemies = new GenerateEnemies(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY(), currentWave);
		this.stage = stage;
		this.enemies = new ArrayList<Enemy>();
		this.generatorTerrain = generatorTerrain;
		this.paths = new ArrayList<Tile>();
		this.remLives = remLives;
		this.groupEnemies = new Group();
		this.currentWave = currentWave;
		
		loadTiles();
		
		destinationX = generatorTerrain.getStartX();
		destinationY = generatorTerrain.getStartY();
		
		this.stage.addActor(this.groupEnemies);
	}
	
	private void loadTiles()
	{
		for(Integer i: this.generatorTerrain.getListOfPathIds())
		{
			paths.add(this.generatorTerrain.getGridMap().get(i));
		}
	}
	
	public void loadEnemies()
	{
		++currentWave;
		enemies.clear();
		for(Enemy e: generatorEnemies.generateEnemies())
		{
			enemies.add(e);
		}
		for(int i = 0; i < enemies.size(); ++i)
		{
			if(i == 0)
			{
				enemies.get(i).setIsFirst(true);
				
			}
			
			enemies.get(i).setPosition(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY());
			groupEnemies.setZIndex(1);
			groupEnemies.addActor(enemies.get(i));
			GenerateMovement(enemies.get(i), enemies.get(i).getX(), enemies.get(i).getY(), i);
		}
		generatorEnemies.setCurrentWave(currentWave);
	}
	
	public void GenerateMovement(final Enemy enemy, float x, float y, int delay)
	{
		sequenceAction = new SequenceAction();
		if(!enemy.getIsFirst())
		{
			sequenceAction.addAction(Actions.delay(0.5f * delay));
		}
		for(int i = 0; i < paths.size(); ++i)
		{
			//Gets the next pathTile
			Tile t = paths.get(i);
			destinationX = t.getX();
			destinationY = t.getY();
			
			sequenceAction.addAction(Actions.moveBy(destinationX - x, destinationY - y, 0.5f));
			
			x = x + destinationX - x;
			y = y + destinationY - y;
		}
		
		sequenceAction.addAction(Actions.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SequenceAction deleteSequenceAction = new SequenceAction();
				deleteSequenceAction.addAction(Actions.moveBy(32, 0, 0.5f));
				deleteSequenceAction.addAction(Actions.run(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int life = Integer.parseInt(remLives.getText().toString());
						life = life - enemy.getHeartDMG();
						remLives.setText(Integer.toString(life));
						enemy.remove();
						enemies.remove(enemy);
						System.out.println("Izbrisan");
					}
				}));
				enemy.addAction(deleteSequenceAction);
			}
		}));
		
		
		enemy.addAction(sequenceAction);
	}
	
	public ArrayList<Tile> getPaths()
	{
		return this.paths;
	}
	
	public int getCurrentWave()
	{
		return this.currentWave;
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return this.enemies;
	}
	
	public int getTileID()
	{
		return this.tileID;
	}
	
	public void setTileID(int tileID)
	{
		this.tileID = tileID;
	}
	
	public ArrayList<Tile> getPathsNew()
	{
		return this.pathsNew;
	}
}
