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
	private ArrayList<Goblin> goblins;
	private Label remLives;
	private Group groupEnemies;
	private SequenceAction sequenceAction;
	private int currentWave;
//	private Label gold;

	public NextWave(Stage stage, TerrainGenerator generatorTerrain, Label remLives, int currentWave)
	{
		this.generatorEnemies = new GenerateEnemies(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY(), currentWave);
		this.stage = stage;
//		this.goblins = generatorEnemies.getGoblins();
		this.goblins = new ArrayList<Goblin>();
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
		goblins.clear();
		for(Goblin g: generatorEnemies.generateEnemies())
		{
			goblins.add(g);
		}
		for(int i = 0; i < goblins.size(); ++i)
		{
			if(i == 0)
			{
				goblins.get(i).setIsFirst(true);
				
			}
//			final int counter = i;
//			Timer timer = new Timer();
//			timer.scheduleTask(new Timer.Task() {
//			    @Override
//			    public void run() {
//			    	System.out.println("x" + counter +":" + goblins.get(counter).getX());
//			    	System.out.println("test:" + goblins.get(counter).getX());
//			    }
//			}, 0f, Gdx.graphics.getDeltaTime());
			
			goblins.get(i).setPosition(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY());
			groupEnemies.setZIndex(1);
			groupEnemies.addActor(goblins.get(i));
			GenerateMovement(goblins.get(i), goblins.get(i).getX(), goblins.get(i).getY(), i);
		}
		
	}
	
	public void GenerateMovement(final Goblin goblin, float x, float y, int delay)
	{
		sequenceAction = new SequenceAction();
		if(!goblin.getIsFirst())
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
				deleteSequenceAction.addAction(Actions.moveBy(32, 0, 0.2f));
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
		
		
		goblin.addAction(sequenceAction);
	}
	
	public ArrayList<Tile> getPaths()
	{
		return this.paths;
	}
	
	public int getCurrentWave()
	{
		return this.currentWave;
	}
	
	public ArrayList<Goblin> getGoblins()
	{
		return this.goblins;
	}
}
