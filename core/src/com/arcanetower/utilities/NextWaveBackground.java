package com.arcanetower.utilities;

import java.util.ArrayList;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.enemies.GenerateEnemies;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Tile;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class NextWaveBackground {
	
	private BackgroundGenerator generatorTerrain;
	private GenerateEnemiesBackground generatorEnemies;
	private Stage stageBackground;
	private ArrayList<Tile> paths;
	private float destinationX;
	private float destinationY;
	private ArrayList<Enemy> enemies;
	private SequenceAction sequenceAction;
	private int currentWave;
	private int tileID;
	private int enemyAmount;
	
	private int maxWave;

	public NextWaveBackground(Stage stageBackground, BackgroundGenerator generatorTerrain)
	{
		this.generatorEnemies = new GenerateEnemiesBackground(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY(), 0);
		this.stageBackground = stageBackground;
		this.enemies = new ArrayList<Enemy>();
		this.generatorTerrain = generatorTerrain;
		this.paths = new ArrayList<Tile>();
		this.currentWave = 0;
		
		this.maxWave = 15;
		
		loadTiles();
		
		destinationX = generatorTerrain.getStartX();
		destinationY = generatorTerrain.getStartY();
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
			this.stageBackground.addActor(enemies.get(i));
			GenerateMovement(enemies.get(i), enemies.get(i).getX(), enemies.get(i).getY(), i);
		}
		generatorEnemies.setCurrentWave(currentWave);
	}
	
	public void GenerateMovement(final Enemy enemy, float x, float y, int delay)
	{
		sequenceAction = new SequenceAction();
		if(!enemy.getIsFirst())
		{
			sequenceAction.addAction(Actions.delay(0.3f * delay));
		}
		for(int i = 0; i < paths.size(); ++i)
		{
			//Gets the next pathTile
			Tile t = paths.get(i);
			destinationX = t.getX();
			destinationY = t.getY();
			
			sequenceAction.addAction(Actions.moveBy(destinationX - x, destinationY - y, 0.3f));
			
			x = x + destinationX - x;
			y = y + destinationY - y;
		}
		
		sequenceAction.addAction(Actions.run(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SequenceAction deleteSequenceAction = new SequenceAction();
				deleteSequenceAction.addAction(Actions.moveBy(32, 0, 0.3f));
				deleteSequenceAction.addAction(Actions.run(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						enemy.remove();
						enemies.remove(enemy);
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
	
	public void setEnemyAmount(int enemyAmount)
	{
		this.enemyAmount = enemyAmount;
	}
	
	public int getEnemyAmount()
	{
		return this.enemyAmount;
	}
	
	public int getMaxWave()
	{
		return this.maxWave;
	}
}
