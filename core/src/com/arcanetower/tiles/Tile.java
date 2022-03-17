package com.arcanetower.tiles;

import com.arcanetower.utilities.TowerType;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Tile extends ImageButton {
	
	private Point tileID;
	private boolean isPath;
	private int tileNum;
	private boolean hasTower;
	private TowerType towerType;
	
	public Tile(Drawable drawable)
	{
		super(drawable);
	}
	
	public Tile(Drawable drawable, boolean isPath)
	{
		super(drawable);
		this.tileID = new Point();
		this.isPath = isPath;
		this.hasTower = false;
		this.towerType = TowerType.None;
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
	
	public boolean getHasTower()
	{
		return this.hasTower;
	}
	
	public void setTower(boolean hasTower, TowerType towerType)
	{
		this.hasTower = hasTower;
		this.towerType = towerType;
	}

}
