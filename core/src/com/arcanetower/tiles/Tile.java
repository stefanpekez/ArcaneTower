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
	private boolean[] sides;
//	private ArrayList<Boolean> sides;
	
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
		this.sides = new boolean[4];
		this.sides[0] = false;
		this.sides[1] = false;
		this.sides[2] = false;
		this.sides[3] = false;
	}
	
	public void setCoordinates(int x, int y)
	{
		this.tileID.setPoint(x, y);
	}
	
	public Point getCoordinates()
	{
		return this.tileID;
	}
	
	public void setIsPath(boolean isPath)
	{
		this.isPath = isPath;
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
	
	public boolean[] getSides()
	{
		return this.sides;
	}

}
