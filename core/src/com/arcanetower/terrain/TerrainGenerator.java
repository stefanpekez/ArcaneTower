package com.arcanetower.terrain;

import java.util.Random;

import com.arcanetower.game.ArcaneTower;
import com.arcanetower.tiles.Point;
import com.arcanetower.tiles.Tile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TerrainGenerator {
	
	private Stage stage;
	private Texture textureGrass;
	private TextureRegion textureRegionGrass;
	
	private Texture texturePath;
	private TextureRegion textureRegionPath;
	
	public TerrainGenerator(Stage stage)
	{
		this.stage = stage;
		generateStartingTerrain();
		generateRemainingPath();
	}
	
	public void generateStartingTerrain()
	{
		Random rand = new Random();
		
		int randomNum = rand.nextInt((14 - 0) + 1) + 0;
		
		textureGrass = new Texture("grass.png");
		textureRegionGrass = new TextureRegion(textureGrass, 0, 0, 32, 32);
		
		texturePath = new Texture("path.png");
		textureRegionPath = new TextureRegion(texturePath, 0, 0, 32, 32);
		
		int initializeStart = 0;
		for (int x = 0; x * 32 < ArcaneTower.SCREEN_WIDTH; ++x) {
            for (int y = 0; y * 32 < ArcaneTower.SCREEN_HEIGTH; ++y) {
            	if(initializeStart == 0)
            	{
            		Tile tile = new Tile(textureRegionPath, true);
            		tile.setPosition(0, randomNum * 32);
            		tile.setCoordinates(x, randomNum);
            		final Point tmp = tile.getCoordinates();
            		tile.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                             System.out.println("clicked path");
                             System.out.println("x = " + tmp.getX());
                             System.out.println("y = " + tmp.getY());
                         }
                     });
            		++initializeStart;
            		--y;
            		stage.addActor(tile);
            		continue;
            	}
            	if(randomNum * 32 == y * 32 && x == 0)
            	{
            		continue;
            	}
            	Tile tile = new Tile(textureRegionGrass, false);
            	tile.setPosition(x * 32, y * 32);
            	tile.setCoordinates(x, y);
            	final Point tmp = tile.getCoordinates();
            	tile.addListener(new ClickListener() {
                   public void clicked(InputEvent event, float x, float y) {
                        System.out.println("clicked grass");
                        System.out.println("x = " + tmp.getX());
                        System.out.println("y = " + tmp.getY());
                    }
                });
                stage.addActor(tile);
            }
        }
	}

	public void generateRemainingPath()
	{
		
	}
}
