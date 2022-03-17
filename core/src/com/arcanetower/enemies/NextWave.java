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
	private int tileID;
	private ArrayList<Tile> pathsNew;

	public NextWave(Stage stage, TerrainGenerator generatorTerrain, Label remLives, int currentWave)
	{
		this.generatorEnemies = new GenerateEnemies(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY(), currentWave);
		this.stage = stage;
		this.goblins = new ArrayList<Goblin>();
		this.generatorTerrain = generatorTerrain;
		this.paths = new ArrayList<Tile>();
		this.pathsNew = new ArrayList<Tile>();
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
			
			goblins.get(i).setPosition(generatorTerrain.getStartX() - 32, generatorTerrain.getStartY());
			groupEnemies.setZIndex(1);
			groupEnemies.addActor(goblins.get(i));
			GenerateMovement(goblins.get(i), goblins.get(i).getX(), goblins.get(i).getY(), i);
		}
		generatorEnemies.setCurrentWave(currentWave);
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
				deleteSequenceAction.addAction(Actions.moveBy(32, 0, 0.5f));
				deleteSequenceAction.addAction(Actions.run(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						int life = Integer.parseInt(remLives.getText().toString());
						--life;
						remLives.setText(Integer.toString(life));
						goblin.remove();
						goblins.remove(goblin);
						System.out.println("Izbrisan");
					}
				}));
				goblin.addAction(deleteSequenceAction);
			}
		}));
		
		
		goblin.addAction(sequenceAction);
	}
	
//	public void setSpeed(final Goblin goblin, float x, float y, int delay, final float f)
//	{
//		this.speed = f;
//		boolean calibrate = true;
		
//		if(!goblin.getIsFirst())
//		{
//			sequenceActionFast.addAction(Actions.delay(f * delay));
//		}
//		
//		for(int i = 0; i < paths.size(); ++i)
//		{
//			int tileX = (int) (x / 32);
//			int tileY = (int) (y / 32);
//			if(paths.get(i).getCoordinates().getX() == tileX && paths.get(i).getCoordinates().getY() == tileY)
//			{
//				for(int j = i; j < paths.size(); ++j)
//				{
//					pathsNew.add(paths.get(j));
//				}
//				break;
//			}
//		}
//		
//		for(int i = 1; i < pathsNew.size(); ++i)
//		{
//			
//			Tile t = pathsNew.get(i);
//			destinationX = t.getX();
//			destinationY = t.getY();
//			
//			System.out.println("x: " + x);
//			System.out.println("y: " + y);
//			System.out.println("-------------------------");
//			System.out.println("destinationX: " + destinationX);
//			System.out.println("destinationY: " + destinationY);
//			System.out.println("-------------------------");
//			System.out.println("dodji do x: " + (x + (destinationX - x)));
//			System.out.println("dodji do y: " + (y + (destinationY - y)));
//			System.out.println("pomeri za x" + (destinationX - x));
//			System.out.println("pomeri za y" + (destinationY - y));
//			
//			if(i == 1)
//			{
//				MoveByAction mba = new MoveByAction();
//				mba.setAmountX(destinationX - x);
//				mba.setAmountY(destinationY - y);
//				mba.setDuration(f);
//				sequenceActionFast.addAction(mba);
//				sequenceActionFast.addAction(Actions.moveBy(destinationX - x, destinationY - y, f));
//				x = x + destinationX - x;
//				y = y + destinationY - y;
//				continue;
//			}
//			
//			sequenceActionFast.addAction(Actions.moveBy(destinationX - x, destinationY - y, f));
//			
//			x = x + destinationX - x;
//			y = y + destinationY - y;
//			finalY = y;
//		}
//		
//		pathsNew.clear();
//		
//		sequenceActionFast.addAction(Actions.run(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				SequenceAction deleteSequenceActionFast = new SequenceAction();
//				deleteSequenceActionFast.addAction(Actions.moveTo(768 + 32, finalY, f));
//				deleteSequenceActionFast.addAction(Actions.run(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//					if(goblin.getY() == paths.get(paths.size() - 1).getY() && goblin.getX() == paths.get(paths.size() - 1).getY() + 32)
//					{
//						int life = Integer.parseInt(remLives.getText().toString());
//						--life;
//						remLives.setText(Integer.toString(life));
//						System.out.println("deleted at y: " + goblin.getY());
//						goblin.remove();
//						goblins.remove(goblin);
//						System.out.println("Izbrisan");
//					}
//				}
//				}));
//				goblin.addAction(deleteSequenceActionFast);
//			}
//		}));
//		
//		
//		goblin.addAction(sequenceActionFast);
//	}
	
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
