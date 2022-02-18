package com.arcanetower.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Tile extends Image {
	
	private Point tileID;
	private boolean isPath;
	
	public Tile(TextureRegion region, boolean isPath)
	{
		super(region);
		this.tileID = new Point();
		this.isPath = isPath;
	}
	
	public void setCoordinates(int x, int y)
	{
		this.tileID.setPoint(x, y);
	}
	
	public Point getCoordinates()
	{
		return this.tileID;
	}

}
