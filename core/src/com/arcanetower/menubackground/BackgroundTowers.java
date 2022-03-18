package com.arcanetower.menubackground;

import java.util.ArrayList;
import java.util.Random;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.tiles.Tile;
import com.arcanetower.towers.BallistaTower;
import com.arcanetower.utilities.ObstacleType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BackgroundTowers {
	
	private Stage stageBackground;
	private int ballistaAmount;
	private ArrayList<Enemy> enemies;
	private ArrayList<Tile> paths;
	private ArrayList<Tile> grass;
	private BackgroundGenerator generatorTerrain;
	private ArrayList<Tile> grassTiles;
	
	public BackgroundTowers(Stage stageBackground, ArrayList<Enemy> enemies, BackgroundGenerator generatorTerrain) {
		// TODO Auto-generated constructor stub
		this.stageBackground = stageBackground;
		this.ballistaAmount = 5;
		this.enemies = enemies;
		this.paths = new ArrayList<Tile>();
		this.grass = new ArrayList<Tile>();
		this.generatorTerrain = generatorTerrain;
		this.grassTiles = generatorTerrain.getGrassTiles();
		
		loadTiles();
		determineSides();
		setupBallistas();
	}
	
	private void loadTiles()
	{
		for(Integer i: this.generatorTerrain.getListOfPathIds())
		{
			paths.add(this.generatorTerrain.getGridMap().get(i));
		}
	}
	
	private void determineSides()
	{
		for(Tile t: paths)
		{
			int currentID = t.getTileNum();
			boolean skipEast = false;
			boolean skipWest = false;
			
			if(!((currentID + 17) < 458))
				skipEast = true;
			
			if(!((currentID - 17) > 0))
				skipWest = true;
			
			// NORTH
			if(generatorTerrain.getGridMap().get(currentID + 1).getIsPath())
			{
				t.getSides()[0] = false;
			} else
			{
				t.getSides()[0] = true;
			}
			
			// EAST
			if(!skipEast)
			{
				if(generatorTerrain.getGridMap().get(currentID + 17).getIsPath())
				{
					t.getSides()[1] = false;
				} else
				{
					t.getSides()[1] = true;
				}
			}
			
			
			// SOUTH
			if(generatorTerrain.getGridMap().get(currentID - 1).getIsPath())
			{
				t.getSides()[2] = false;
			} else
			{
				t.getSides()[2] = true;
			}
			
			// WEST
			if(!skipWest)
			{
				if(generatorTerrain.getGridMap().get(currentID - 17).getIsPath())
				{
					t.getSides()[3] = false;
				} else
				{
					t.getSides()[3] = true;
				}
			}
			
			
		}
	}
	
	private void setupBallistas()
	{
		Random rand = new Random();
		float buildX = 0;
		float buildY = 0;
		
		boolean doneBuilding = false;
		boolean doneForReal = false;
		for(int i = 0; i < 5; ++i)
		{
			
			int randomPath = rand.nextInt(paths.size()-1);
			int buildNums[] = new int[2];
			int counter = 0;
			Tile path = paths.get(randomPath);
			
			for(int j = 0; j < path.getSides().length; ++j)
			{
				if(path.getSides()[j])
				{
					buildNums[counter] = j;
					++counter;
				}
			}
			
			doneBuilding = false;
			for(int j = 0; j < 2; ++j)
			{
				if(doneBuilding)
					break;
				
				switch (buildNums[j]) {
					case 0:
						for(Tile t: grassTiles)
						{
							if(t.getObstacle() == ObstacleType.Tree)
							{
								continue;
							}
							if(t.getHasTower())
							{
								doneForReal = true;
								continue;
							}
							if((t.getTileNum() == path.getTileNum() + 1))
							{
								System.out.println("north");
								t.setHasTower(true);
								buildX = path.getX();
								buildY = path.getY() + 32f;
								doneBuilding = true;
								doneForReal = false;
								break;
							}
						}
							
						break;
					case 1:
						for(Tile t: grassTiles)
						{
							if(t.getObstacle() == ObstacleType.Tree)
							{
								continue;
							}
							if(t.getHasTower())
							{
								doneForReal = true;
								continue;
							}
							
							if((t.getTileNum() == path.getTileNum() + 17))
							{
								System.out.println("east");
								t.setHasTower(true);
								buildX = path.getX() + 32f;
								buildY = path.getY();
								doneBuilding = true;
								doneForReal = false;
								break;
							}
						}
							
						break;
					case 2:
						for(Tile t: grassTiles)
						{
							if(t.getObstacle() == ObstacleType.Tree)
							{
								continue;
							}
							if(t.getHasTower())
							{
								doneForReal = true;
								continue;
							}
							
							if((t.getTileNum() == path.getTileNum() - 1))
							{
								System.out.println(t.getTileNum());
								System.out.println(path.getTileNum() - 1);
								System.out.println("south");
								t.setHasTower(true);
								buildX = path.getX();
								buildY = path.getY() - 32f;
								doneBuilding = true;
								doneForReal = false;
								break;
							}
						}
							
						break;
					case 3:
						for(Tile t: grassTiles)
						{
							if(t.getObstacle() == ObstacleType.Tree)
							{
								continue;
							}
							
							if(t.getHasTower())
							{
								doneForReal = true;
								continue;
							}
							
							if((t.getTileNum() == path.getTileNum() - 17))
							{
								System.out.println("west");
								t.setHasTower(true);
								buildX = path.getX() - 32f;
								buildY = path.getY();
								doneBuilding = true;
								doneForReal = false;
								break;
							}
						}
						break;
				}
			}
			
			if(doneForReal)
			{
				continue;
			}
			final BallistaTowerBackground bt = new BallistaTowerBackground(buildX, buildY, enemies);
			stageBackground.addActor(bt);
		}
		
	}
}
