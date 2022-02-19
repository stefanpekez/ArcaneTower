package com.arcanetower.terrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class TerrainGenerator {
	
	private Stage stage;
	private Texture textureGrass;
	private TextureRegion textureRegionGrass;
	
	private Texture texturePath;
	private TextureRegion textureRegionPath;
	
	private HashMap<Integer, Tile> gridMap;
	
	private ArrayList<Tile> tiles;
	
	private int lastId;
	private int endTileId;
	private boolean stopGeneration;
	
	public TerrainGenerator(Stage stage)
	{
		this.stage = stage;
		this.gridMap = new HashMap<Integer, Tile>();
		stopGeneration = false;
		generateStartingTerrain();
		generatePath();
		generateGrass();
//		writeTiles();
	}
	
	public Tile generateStartingTerrain()
	{
		Random rand = new Random();
		
		int randomStart = rand.nextInt((13 - 1) + 1) + 1;
		int randomEnd = rand.nextInt((13 - 1) + 1) + 1;
		
		textureGrass = new Texture("grass.png");
		textureRegionGrass = new TextureRegion(textureGrass, 0, 0, 32, 32);
		
		texturePath = new Texture("path.png");
		textureRegionPath = new TextureRegion(texturePath, 0, 0, 32, 32);
		
		Tile tile = new Tile(textureRegionPath, true);
		tile.setPosition(0, 5 * 32);
		tile.setCoordinates(0, 5);
		final Point tmp = tile.getCoordinates();
		final boolean tmpBool = tile.getIsPath();
		tile.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                 System.out.println("clicked path");
                 System.out.println("x = " + tmp.getX());
                 System.out.println("y = " + tmp.getY());
                 System.out.println("IsPath = " + tmpBool);
             }
         });
		stage.addActor(tile);
		gridMap.put(5, tile);
		
		Tile tileStart = new Tile(textureRegionPath, true);
		tileStart.setPosition(1 * 32, tile.getCoordinates().getY() * 32);
		tileStart.setCoordinates(1, tile.getCoordinates().getY());
		final Point tmpStart = tileStart.getCoordinates();
		final boolean tmpBoolStart = tileStart.getIsPath();
		tile.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                 System.out.println("clicked path");
                 System.out.println("x = " + tmpStart.getX());
                 System.out.println("y = " + tmpStart.getY());
                 System.out.println("IsPath = " + tmpBoolStart);
             }
         });
		stage.addActor(tileStart);
		gridMap.put(5 + 15, tileStart);
		lastId = 5 + 15;
		
//		Tile tileEnd = new Tile(textureRegionPath, true);
//		tileEnd.setPosition(24 * 32, randomEnd * 32);
//		tileEnd.setCoordinates(24, randomEnd);
//		final Point tmpEnd = tileEnd.getCoordinates();
//		final boolean tmpBoolEnd = tileEnd.getIsPath();
//		tileEnd.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                 System.out.println("clicked path");
//                 System.out.println("x = " + tmpEnd.getX());
//                 System.out.println("y = " + tmpEnd.getY());
//                 System.out.println("IsPath = " + tmpBoolEnd);
//             }
//         });
//		stage.addActor(tileEnd);
//		gridMap.put(24 * 15 + randomEnd, tileEnd);
//		endTileId = 24 * 15 + randomEnd;
		
		return tileStart;
	}

	public void generatePath()
	{
		Random rand = new Random();
		
		for(int i = 0; i < 100; ++i)
		{
		
			Tile pathNew = new Tile(textureRegionPath, true);
			
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
	            	if(gridMap.keySet().contains(lastId - 14))
	            		continue;
	            	
					pathNew.setPosition(oldX * 32, (oldY + 1) * 32);
					pathNew.setCoordinates(oldX, oldY + 1);
	            	final Point tmp = pathNew.getCoordinates();
	            	final boolean tmpBool = pathNew.getIsPath();
	            	pathNew.addListener(new ClickListener() {
	                   public void clicked(InputEvent event, float x, float y) {
	                        System.out.println("clicked path");
	                        System.out.println("x = " + tmp.getX());
	                        System.out.println("y = " + tmp.getY());
	                        System.out.println("IsPath = " + tmpBool);
	                    }
	                });
	                stage.addActor(pathNew);
	                gridMap.put(lastId + 1, pathNew);
	                lastId = lastId + 1;
					break;
				case 1:
					if(gridMap.keySet().contains(lastId + 15))
	            		continue;
					
					pathNew.setPosition((oldX + 1) * 32, oldY * 32);
					pathNew.setCoordinates((oldX + 1), oldY);
	            	final Point tmpEast = pathNew.getCoordinates();
	            	final boolean tmpBoolEast = pathNew.getIsPath();
	            	pathNew.addListener(new ClickListener() {
	                   public void clicked(InputEvent event, float x, float y) {
	                        System.out.println("clicked path");
	                        System.out.println("x = " + tmpEast.getX());
	                        System.out.println("y = " + tmpEast.getY());
	                        System.out.println("IsPath = " + tmpBoolEast);
	                    }
	                });
	            	
	                stage.addActor(pathNew);
	                gridMap.put(lastId + 15, pathNew);
	                lastId = lastId + 15;
					break;
				case 2:
					if(gridMap.keySet().contains(lastId - 1))
	            		continue;
	            	if(gridMap.keySet().contains(lastId - 16))
	            		continue;
					
					pathNew.setPosition(oldX * 32, (oldY - 1) * 32);
					pathNew.setCoordinates(oldX, oldY - 1);
	            	final Point tmpSouth = pathNew.getCoordinates();
	            	final boolean tmpBoolSouth = pathNew.getIsPath();
	            	pathNew.addListener(new ClickListener() {
	                   public void clicked(InputEvent event, float x, float y) {
	                        System.out.println("clicked path");
	                        System.out.println("x = " + tmpSouth.getX());
	                        System.out.println("y = " + tmpSouth.getY());
	                        System.out.println("IsPath = " + tmpBoolSouth);
	                    }
	                });
	                stage.addActor(pathNew);
	                gridMap.put(lastId - 1, pathNew);
	                lastId = lastId - 1;
					break;
			}
		}
		
		
		
	}
	
	public void writeTiles()
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		ids.addAll(gridMap.keySet());
		
		Collections.sort(ids);
		
		for(Integer i: ids)
		{
			Tile tile = gridMap.get(i);
			System.out.println("Tile" + i);
			System.out.println("x = " + tile.getCoordinates().getX());
			System.out.println("y = " + tile.getCoordinates().getY());
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
		
		if(x == 24)
		{
			stopGeneration = true;
			return 0;
		}
		
//		if(x == 23)
//		{
//			Tile endTile = gridMap.get(endTileId);
//			if(y < endTile.getCoordinates().getY())
//			{
//				return 0;
//			} else if(y > endTile.getCoordinates().getY())
//			{
//				return 2;
//			} else
//				return 1;
//		}
		
		switch(randomNum) 
		{
			case 0:
				if(y == 13)
				{
					int newDirection = random.nextInt((2 - 1) + 1) + 1;
					if(newDirection == 1)
						System.out.println("Direction east");
					else
						System.out.println("Direction south");
					generateSecondNorth = true;
					return newDirection;
				} else
					System.out.println("Direction north");
				generateSecondNorth = true;
				break;
			case 1:
				System.out.println("Direction east");
				generateSecondEast = true;
				return randomNum;
			case 2:
				if(y == 1)
				{
					int newDirection = random.nextInt((1 - 0) + 1) + 0;
					if(newDirection == 1)
						System.out.println("Direction east");
					else
						System.out.println("Direction north");
					generateSecondSouth = true;
					return newDirection;
				} else
					System.out.println("Direction south");
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
				Tile tile = new Tile(textureRegionGrass, false);
				tile.setPosition(x * 32, y * 32);
        		tile.setCoordinates(x, y);
        		final Point tmp = tile.getCoordinates();
        		final boolean tmpBool = tile.getIsPath();
        		tile.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                         System.out.println("clicked grass");
                         System.out.println("x = " + tmp.getX());
                         System.out.println("y = " + tmp.getY());
                         System.out.println("IsPath = " + tmpBool);
                     }
                 });
        		stage.addActor(tile);
        		gridMap.put(tileCount, tile);
        		++tileCount;
			}
		}
          
	}
}
