package com.arcanetower.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Tile extends ImageButton {
	
	private Point tileID;
	private boolean isPath;
	private int tileNum;
	
	public Tile(Drawable drawable)
	{
		super(drawable);
	}
	
	public Tile(Drawable drawable, boolean isPath)
	{
		super(drawable);
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
	
	public void setIsPath()
	{
		this.isPath = true;
	}
	
	public boolean getIsPath()
	{
		return this.isPath;
	}
	
	public void setTileNum(int num)
	{
		this.tileNum = num;
	}
	
	public int getTileNum()
	{
		return this.tileNum;
	}

}
