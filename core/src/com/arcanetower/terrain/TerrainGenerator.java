package com.arcanetower.terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.game.ArcaneTower;
import com.arcanetower.screens.MainGameScreen;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.arcanetower.towers.BallistaTower;
import com.arcanetower.towers.PlacedTowers;
import com.arcanetower.towers.TowerButton;
import com.arcanetower.ui.InfoLabels;
import com.arcanetower.ui.TowerPanel;
import com.arcanetower.utilities.ArrowBallista;
import com.arcanetower.utilities.TowerType;
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

public class TerrainGenerator {
	
	private Stage stage;
	private HashMap<Integer, Tile> gridMap;
	private ArrayList<Integer> pathID;
	
	private int lastId;
	private boolean stopGeneration;
	
	private int startX;
	private int startY;
	
	private Drawable grassTile;
	private Drawable pathTile;
	
	private final Image grassHighlight;
	private final Image pathHighlight;
	
	private boolean hidePath;
	
	private PlacedTowers placed;
	
	private TowerButton ballista;
	
	private MainGameScreen screen;
	
	private ArrayList<ArrowBallista> arrows;
	private ArrayList<Enemy> enemies;
	private Stage stageUI;
	private InfoLabels infoLabels;
	
	public TerrainGenerator(Stage stage, TowerPanel towerPanel, MainGameScreen screen, Stage stageUI)
	{
		this.stage = stage;
		this.gridMap = new HashMap<Integer, Tile>();
		this.grassTile = new TextureRegionDrawable(new Texture(Gdx.files.internal("grassNoBorder.png")));
		this.pathTile = new TextureRegionDrawable(new Texture(Gdx.files.internal("pathNoBorder.png")));
		
		this.grassHighlight = new Image(new Texture(Gdx.files.internal("grassHovered.png")));
		this.grassHighlight.setVisible(false);
		
		this.pathHighlight = new Image(new Texture(Gdx.files.internal("pathHovered.png")));
		this.pathHighlight.setVisible(false);
		
		this.pathID = new ArrayList<Integer>();
		
		this.placed = new PlacedTowers();
		
		this.ballista = towerPanel.getBallista();
		
		this.screen = screen;
		this.stageUI = stageUI;
		this.enemies = new ArrayList<Enemy>();
		
		stopGeneration = false;
		generateStartingTerrain();
		generatePath();
		generateGrass();
//		writePathID();
//		writeTiles();
	}
	
	public Tile generateStartingTerrain()
	{
		Random rand = new Random();
		
		int randomStart = rand.nextInt((13 - 1) + 1) + 1;
		
		final Tile tile = new Tile(pathTile, true);
		tile.setPosition(0, randomStart * 32);
		tile.setCoordinates(0, randomStart);
		final Point tmp = tile.getCoordinates();
		final boolean tmpBool = tile.getIsPath();
		final int tmpCount = randomStart;
		tile.addListener(new ClickListener(Buttons.RIGHT) {
            public void clicked(InputEvent event, float x, float y) {
                 System.out.println("clicked path" + tmpCount);
                 System.out.println("x = " + tmp.getX());
                 System.out.println("y = " + tmp.getY());
                 System.out.println("IsPath = " + tmpBool);
                 
                 Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
				ballista.setDisabled(false);
				screen.setGameSpeed(1);
             }
            
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	// TODO Auto-generated method stub
            	if(pointer == -1)
            	{
            		tile.addActor(pathHighlight);
            		pathHighlight.setVisible(true);
            	}
            	
            }
            
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	// TODO Auto-generated method stub
            	if(pointer != -1)
            	{
            		pathHighlight.setVisible(false);
            		pathHighlight.remove();
            		stage.draw();
            	} else
            		grassHighlight.setVisible(false);
            	
            }
         });
		stage.addActor(tile);
		gridMap.put(randomStart, tile);
		tile.setTileNum(randomStart);
		pathID.add(randomStart);
		
		startX = 0;
		startY = randomStart * 32;
		
		final Tile tileStart = new Tile(pathTile, true);
		tileStart.setPosition(1 * 32, tile.getCoordinates().getY() * 32);
		tileStart.setCoordinates(1, tile.getCoordinates().getY());
		final Point tmpStart = tileStart.getCoordinates();
		final boolean tmpBoolStart = tileStart.getIsPath();
		final int tmpCountStart = randomStart + 15;
		tileStart.addListener(new ClickListener(Buttons.RIGHT) {
			@Override
            public void clicked(InputEvent event, float x, float y) {
            	System.out.println("clicked path" + tmpCountStart);
                System.out.println("x = " + tmpStart.getX());
                System.out.println("y = " + tmpStart.getY());
                System.out.println("IsPath = " + tmpBoolStart);
                
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
				ballista.setDisabled(false);
				screen.setGameSpeed(1);
                 
             }
			@Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            	// TODO Auto-generated method stub
            	if(pointer == -1)
            	{
            		tileStart.addActor(pathHighlight);
            		pathHighlight.setVisible(true);
            	}
            	
            	
            }
            
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            	// TODO Auto-generated method stub
            	if(pointer != -1)
            	{
            		pathHighlight.setVisible(false);
            		pathHighlight.remove();
            	} else
            		grassHighlight.setVisible(false);
            	
            }
            
         });
		stage.addActor(tileStart);
		gridMap.put(randomStart + 15, tileStart);
		pathID.add(randomStart + 15);
		tileStart.setTileNum(randomStart + 15);
		lastId = randomStart + 15;
		
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
	            	if(gridMap.keySet().contains(lastId - 14))
	            		continue;
	            	
					pathNew.setPosition(oldX * 32, (oldY + 1) * 32);
					pathNew.setCoordinates(oldX, oldY + 1);
	            	final Point tmp = pathNew.getCoordinates();
	            	final boolean tmpBool = pathNew.getIsPath();
	            	final int tmpCountNorth = lastId + 1;
	            	pathNew.addListener(new ClickListener(Buttons.RIGHT) {
	                   public void clicked(InputEvent event, float x, float y) {
	                        System.out.println("clicked path" + tmpCountNorth);
	                        System.out.println("x = " + tmp.getX());
	                        System.out.println("y = " + tmp.getY());
	                        System.out.println("IsPath = " + tmpBool);
	                        
	                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
    						ballista.setDisabled(false);
    						screen.setGameSpeed(1);
	                    }
	                   
	                   @Override
	                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                    	// TODO Auto-generated method stub
	                    	if(pointer == -1)
	                    	{
	                    		pathNew.addActor(pathHighlight);
	                    		pathHighlight.setVisible(true);
	                    	}
	                    	
	                    	
	                    }
	                    
	                    @Override
	                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    	// TODO Auto-generated method stub
	                    	if(pointer != -1)
	                    	{
	                    		pathHighlight.setVisible(false);
	                    		pathHighlight.remove();
	                    	} else
	                    		grassHighlight.setVisible(false);
	                    	
	                    }
	                });
	                stage.addActor(pathNew);
	                gridMap.put(lastId + 1, pathNew);
	                pathID.add(lastId + 1);
	                pathNew.setTileNum(lastId + 1);
	                lastId = lastId + 1;
					break;
				case 1:
					if(gridMap.keySet().contains(lastId + 15))
	            		continue;
					
					pathNew.setPosition((oldX + 1) * 32, oldY * 32);
					pathNew.setCoordinates((oldX + 1), oldY);
	            	final Point tmpEast = pathNew.getCoordinates();
	            	final boolean tmpBoolEast = pathNew.getIsPath();
	            	final int tmpCountEast = lastId + 15;
	            	pathNew.addListener(new ClickListener(Buttons.RIGHT) {
	                   public void clicked(InputEvent event, float x, float y) {
	                        System.out.println("clicked path" + tmpCountEast);
	                        System.out.println("x = " + tmpEast.getX());
	                        System.out.println("y = " + tmpEast.getY());
	                        System.out.println("IsPath = " + tmpBoolEast);
	                        
	                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
    						ballista.setDisabled(false);
    						screen.setGameSpeed(1);
	                    }
	                   
	                   @Override
	                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                    	// TODO Auto-generated method stub
	                    	if(pointer == -1)
	                    	{
	                    		pathNew.addActor(pathHighlight);
	                    		pathHighlight.setVisible(true);
	                    	}
	                    	
	                    	
	                    }
	                    
	                    @Override
	                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    	// TODO Auto-generated method stub
	                    	if(pointer != -1)
	                    	{
	                    		pathHighlight.setVisible(false);
	                    		pathHighlight.remove();
	                    	} else
	                    		grassHighlight.setVisible(false);
	                    	
	                    }
	                });
	            	
	                stage.addActor(pathNew);
	                gridMap.put(lastId + 15, pathNew);
	                pathID.add(lastId + 15);
	                pathNew.setTileNum(lastId + 15);
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
	            	final int tmpCountSouth = lastId - 1;
	            	pathNew.addListener(new ClickListener(Buttons.RIGHT) {
	                   public void clicked(InputEvent event, float x, float y) {
	                        System.out.println("clicked path" + tmpCountSouth);
	                        System.out.println("x = " + tmpSouth.getX());
	                        System.out.println("y = " + tmpSouth.getY());
	                        System.out.println("IsPath = " + tmpBoolSouth);
	                        
	                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
    						ballista.setDisabled(false);
    						screen.setGameSpeed(1);
	                    }
	                   
	                   @Override
	                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                    	// TODO Auto-generated method stub
	                    	if(pointer == -1)
	                    	{
	                    		pathNew.addActor(pathHighlight);
	                    		pathHighlight.setVisible(true);
	                    	}
	                    	
	                    }
	                    
	                    @Override
	                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    	// TODO Auto-generated method stub
	                    	if(pointer != -1)
	                    	{
	                    		pathHighlight.setVisible(false);
	                    		pathHighlight.remove();
	                    	} else
	                    		grassHighlight.setVisible(false);
	                    	
	                    }
	                });
	                stage.addActor(pathNew);
	                gridMap.put(lastId - 1, pathNew);
	                pathID.add(lastId - 1);
	                pathNew.setTileNum(lastId - 1);
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
		
		switch(randomNum) 
		{
			case 0:
				if(y == 13)
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
		for (int x = 0; x * 32 < ArcaneTower.SCREEN_WIDTH - 2 * 32; ++x)
		{
			for (int y = 0; y * 32 < ArcaneTower.SCREEN_HEIGTH - 2 * 32; ++y)
			{
				if(gridMap.keySet().contains(tileCount))
				{
					++tileCount;
					continue;
				}
				final Tile tile = new Tile(grassTile, false);
				tile.setPosition(x * 32, y * 32);
        		tile.setCoordinates(x, y);
        		final Point tmp = tile.getCoordinates();
        		final boolean tmpBool = tile.getIsPath();
        		final int tmpCount = tileCount;
        		tile.addListener(new ClickListener() {
        			
        			@Override
        			public void clicked(InputEvent event, float x, float y) {
        				// TODO Auto-generated method stub
        			}
                    
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    	// TODO Auto-generated method stub
                    	if(pointer == -1)
                    	{
                    		tile.addActor(grassHighlight);
                        	grassHighlight.setVisible(true);
                    	}
                    }
                    
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    	// TODO Auto-generated method stub
                    	if(pointer != -1)
                    	{
                    		grassHighlight.setVisible(false);
                            grassHighlight.remove();
                    	} else
                    		pathHighlight.setVisible(false);
                    }
                 });
        		
        		tile.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						// TODO Auto-generated method stub
						TowerPanel.getInstance(stage, screen).getBallista().setDisabled(false);
//						System.out.println("clicked grass" + tmpCount);
//						System.out.println("x = " + tile.getX());
//						System.out.println("y = " + tile.getY());
//						System.out.println("tileID = " + tile.getTileNum());
//                        System.out.println("x = " + tmp.getX());
//                        System.out.println("y = " + tmp.getY());
//                        System.out.println("IsPath = " + tmpBool);
						if(ballista.isDisabled() && !tile.getHasTower() && infoLabels.removeMoney(50))
						{
							placed.getPlacedTowers().add(new BallistaTower(tile.getX(), tile.getY(), enemies, infoLabels));
//							System.out.println(placed.getPlacedTowers().size());
							
							final BallistaTower bt = placed.getPlacedTowers().get(placed.getPlacedTowers().size()-1);
							bt.addListener(new ClickListener()
							{
								public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
									bt.setIsHovered(true);
								};
								
								public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
									bt.setIsHovered(false);
								};
							});
							
							Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
							ballista.setDisabled(false);
							
							tile.setTower(true, TowerType.Ballista);
							screen.setGameSpeed(1);
							stageUI.addActor(bt);
						}
					}
				});
        		stage.addActor(tile);
        		gridMap.put(tileCount, tile);
        		tile.setTileNum(tileCount);
        		++tileCount;
			}
		}
	}
	
	public ArrayList<ArrowBallista> getArrows()
	{
		return arrows;
	}
	
	public int getStartX()
	{
		return this.startX;
	}
	
	public int getStartY()
	{
		return this.startY;
	}
	
	public boolean getHidePath()
	{
		return this.hidePath;
	}
	
	
	public ArrayList<Integer> getListOfPathIds()
	{
		return this.pathID;
	}
	
//	private void writePathID()
//	{
//		for(Integer i: this.pathID)
//		{
//			System.out.println(i);
//		}
//	}
	
	public HashMap<Integer, Tile> getGridMap()
	{
		return this.gridMap;
	}
	
	public void setGoblins(ArrayList<Enemy> enemies)
	{
		this.enemies = enemies;
		for(BallistaTower bt: placed.getPlacedTowers())
			bt.setEnemies(enemies);
	}
	
	public PlacedTowers getPlacedTowers()
	{
		return placed;
	}
	
	public void setInfoLabels(InfoLabels infoLabels)
	{
		this.infoLabels = infoLabels;
	}
}
