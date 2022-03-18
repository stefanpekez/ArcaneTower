package com.arcanetower.menubackground;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.arcanetower.terrain.TerrainGenerator;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.arcanetower.towers.BallistaTower;
import com.arcanetower.towers.PlacedTowers;
import com.arcanetower.towers.TowerButton;
import com.arcanetower.ui.InfoLabels;
import com.arcanetower.ui.TowerPanel;
import com.arcanetower.utilities.ObstacleType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class BackgroundGenerator {
	
	private Stage stageBackground;
	private HashMap<Integer, Tile> gridMap;
	private ArrayList<Integer> pathID;
	private ArrayList<Tile> grassTiles;
	
	private int lastId;
	private boolean stopGeneration;
	
	private int startX;
	private int startY;
	
	private ArrayList<Enemy> enemies;
	
	private Drawable grassTile;
	private Drawable pathTile;
	
	private TextureRegionDrawable grassTree1;
	private TextureRegionDrawable grassTree2;
	
	private TextureRegionDrawable rock1;
	private TextureRegionDrawable rock2;
	
	private ArrayList<Integer> grassID;
	
	public BackgroundGenerator(Stage stageBackground)
	{
		this.stageBackground = stageBackground;
		this.gridMap = new HashMap<Integer, Tile>();
		this.grassTile = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\grassTexNoBorderBG.png")));
		this.pathTile = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\dirtTexBG.png")));
		
		this.grassTree1 = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\tree32BG.png")));
		this.grassTree2 = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\treeRed32BG.png")));
		
		this.rock1 = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\smallRock32BG.png")));
		this.rock2 = new TextureRegionDrawable(new Texture(Gdx.files.internal("menu\\rock32BG.png")));
		
		this.pathID = new ArrayList<Integer>();
		this.grassID = new ArrayList<Integer>();
		
		this.grassTiles = new ArrayList<Tile>();
		
		stopGeneration = false;
		generateStartingTerrain();
		generatePath();
		generateGrass();
		generateTrees();
		generateRocks();
//		writePathID();
//		writeTiles();
	}
	
	public Tile generateStartingTerrain()
	{
		Random rand = new Random();
		
		int randomStart = rand.nextInt((15 - 1) + 1) + 1;
		
		final Tile tile = new Tile(pathTile, true);
		tile.setPosition(0, randomStart * 32);
		tile.setCoordinates(0, randomStart);
		tile.setIsPath(true);
		
		
		stageBackground.addActor(tile);
		gridMap.put(randomStart, tile);
		tile.setTileNum(randomStart);
		pathID.add(randomStart);
		
		startX = 0;
		startY = randomStart * 32;
		
		final Tile tileStart = new Tile(pathTile, true);
		tileStart.setPosition(1 * 32, tile.getCoordinates().getY() * 32);
		tileStart.setCoordinates(1, tile.getCoordinates().getY());
		tileStart.setIsPath(true);
		
		stageBackground.addActor(tileStart);
		gridMap.put(randomStart + 17, tileStart);
		pathID.add(randomStart + 17);
		tileStart.setTileNum(randomStart + 17);
		lastId = randomStart + 17;
		
		return tileStart;
	}

	public void generatePath()
	{
		Random rand = new Random();
		
		for(int i = 0; i < 100; ++i)
		{
			final Tile pathNew = new Tile(pathTile, true);
			
			Tile pathOld = gridMap.get(lastId);
			int oldX = pathOld.getCoordinates().getX();
			int oldY = pathOld.getCoordinates().getY();
			
			int randomNum = generatePathDirection(oldX, oldY, rand);
			
			
			if(stopGeneration)
				return;
			
			switch(randomNum)
			{
				case 0:
					if(gridMap.keySet().contains(lastId + 1))
	            		continue;
	            	if(gridMap.keySet().contains(lastId - 16))
	            		continue;
	            	
					pathNew.setPosition(oldX * 32, (oldY + 1) * 32);
					pathNew.setCoordinates(oldX, oldY + 1);
					pathNew.setIsPath(true);
					
	                stageBackground.addActor(pathNew);
	                gridMap.put(lastId + 1, pathNew);
	                pathID.add(lastId + 1);
	                pathNew.setTileNum(lastId + 1);
	                lastId = lastId + 1;
					break;
				case 1:
					if(gridMap.keySet().contains(lastId + 17))
	            		continue;
					
					pathNew.setPosition((oldX + 1) * 32, oldY * 32);
					pathNew.setCoordinates((oldX + 1), oldY);
					pathNew.setIsPath(true);
	            	
	                stageBackground.addActor(pathNew);
	                gridMap.put(lastId + 17, pathNew);
	                pathID.add(lastId + 17);
	                pathNew.setTileNum(lastId + 17);
	                lastId = lastId + 17;
					break;
				case 2:
					if(gridMap.keySet().contains(lastId - 1))
	            		continue;
	            	if(gridMap.keySet().contains(lastId - 18))
	            		continue;
					
					pathNew.setPosition(oldX * 32, (oldY - 1) * 32);
					pathNew.setCoordinates(oldX, oldY - 1);
					pathNew.setIsPath(true);
					
	                stageBackground.addActor(pathNew);
	                gridMap.put(lastId - 1, pathNew);
	                pathID.add(lastId - 1);
	                pathNew.setTileNum(lastId - 1);
	                lastId = lastId - 1;
					break;
			}
		}
	}
	
	private int generatePathDirection(int x, int y, Random random)
	{
		
		// TODO: make it so the path is smoother. Maybe add difficulty settings
		int randomNum = random.nextInt((2 - 0) + 1) + 0;
		boolean generateSecondNorth = false;
		boolean generateSecondEast = false;
		boolean generateSecondSouth = false;
		
		boolean generateThirdNorth = false;
		boolean generateThirdEast = false;
		boolean generateThirdSouth = false;
		
		if(generateThirdNorth)
		{
			generateThirdNorth = false;
			return 0;
		} else if(generateThirdEast)
		{
			generateThirdEast = false;
			return 1;
		} else if(generateThirdSouth)
		{
			generateThirdSouth = false;
			return 2;
		}
		
		if(generateSecondNorth)
		{
			generateSecondNorth = false;
			generateThirdNorth = true;
			return 0;
		} else if(generateSecondEast)
		{
			generateSecondEast = false;
			generateThirdEast = true;
			return 1;
		} else if(generateSecondSouth)
		{
			generateSecondSouth = false;
			generateThirdSouth = true;
			return 2;
		}
		
		if(x == 26)
		{
			stopGeneration = true;
			return 0;
		}
		
		switch(randomNum) 
		{
			case 0:
				if(y == 15)
				{
					int newDirection = random.nextInt((2 - 1) + 1) + 1;
					generateSecondNorth = true;
					return newDirection;
				} else
//					System.out.println("Direction north");
				generateSecondNorth = true;
				break;
			case 1:
//				System.out.println("Direction east");
				generateSecondEast = true;
				return randomNum;
			case 2:
				if(y == 1)
				{
					int newDirection = random.nextInt((1 - 0) + 1) + 0;
//					if(newDirection == 1)
//						System.out.println("Direction east");
//					else
//						System.out.println("Direction north");
					generateSecondSouth = true;
					return newDirection;
				} else
//					System.out.println("Direction south");
				generateSecondSouth = true;
				break;
		}
		
		return randomNum;
	}
	
	private void generateGrass()
	{
		
		int tileCount = 0;
		for (int x = 0; x * 32 < ArcaneTower.SCREEN_WIDTH; ++x)
		{
			for (int y = 0; y * 32 < ArcaneTower.SCREEN_HEIGTH; ++y)
			{
				if(gridMap.keySet().contains(tileCount))
				{
					++tileCount;
					continue;
				}
				final Tile tile = new Tile(grassTile, false);
				tile.setPosition(x * 32, y * 32);
        		tile.setCoordinates(x, y);
        		
        		grassTiles.add(tile);
        		grassID.add(tileCount);
        		stageBackground.addActor(tile);
        		gridMap.put(tileCount, tile);
        		tile.setTileNum(tileCount);
        		++tileCount;
			}
		}
	}
	
	public int getStartX()
	{
		return this.startX;
	}
	
	public int getStartY()
	{
		return this.startY;
	}
	
	public ArrayList<Integer> getListOfPathIds()
	{
		return this.pathID;
	}
	
	public ArrayList<Integer> getListOfGrassIds()
	{
		return this.grassID;
	}
	
	public HashMap<Integer, Tile> getGridMap()
	{
		return this.gridMap;
	}
	
	public void setEnemies(ArrayList<Enemy> enemies)
	{
		this.enemies = enemies;
//		for(BallistaTower bt: placed.getPlacedTowers())
//			bt.setEnemies(enemies);
	}
	
	public ArrayList<Tile> getGrassTiles()
	{
		return this.grassTiles;
	}
	
	public void generateTrees()
	{
		int rateTrees = 50;
		Random rand = new Random();
		int tileIndex = 0;
		int type = 0;
		
		Image tree = null;
		for(int i = 0; i < rateTrees; ++i)
		{
			type = rand.nextInt((2 - 0) + 1) + 0;
			tileIndex = rand.nextInt(grassTiles.size() - 1);
			if(grassTiles.get(tileIndex).getObstacle() == ObstacleType.Tree)
			{
				continue;
			}
			
			if(grassTiles.get(tileIndex).getHasTower())
			{
				continue;
			}
			
			if(type == 0)
			{
				tree = new Image(grassTree1);
			}
			else if(type == 1)
			{
				tree = new Image(grassTree2);
			}
			else
			{
				tree = new Image(grassTree1);
			}
			grassTiles.get(tileIndex).setObstacle(ObstacleType.Tree);
			tree.setPosition(grassTiles.get(tileIndex).getX(), grassTiles.get(tileIndex).getY());
//			grassTiles.get(tileIndex).remove();
			stageBackground.addActor(tree);
		}
	}
	
	public void generateRocks()
	{
		int rateRocks = 20;
		Random rand = new Random();
		int tileIndex = 0;
		int type = 0;
		
		Image rock = null;
		for(int i = 0; i < rateRocks; ++i)
		{
			type = rand.nextInt((2 - 0) + 1) + 0;
			tileIndex = rand.nextInt(grassTiles.size() - 1);
			if(grassTiles.get(tileIndex).getObstacle() == ObstacleType.Tree)
			{
				continue;
			}
			
			if(grassTiles.get(tileIndex).getHasTower())
			{
				continue;
			}
			
			if(type == 0)
			{
				rock = new Image(rock1);
			}
			else if(type == 1)
			{
				rock = new Image(rock2);
			}
			else
			{
				rock = new Image(rock1);
			}
			
			grassTiles.get(tileIndex).setObstacle(ObstacleType.Tree);
			rock.setPosition(grassTiles.get(tileIndex).getX(), grassTiles.get(tileIndex).getY());
			grassTiles.get(tileIndex).remove();
			stageBackground.addActor(rock);
		}
	}
}
