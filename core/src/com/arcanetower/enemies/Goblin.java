package com.arcanetower.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Goblin extends Image{
	
	private int health;
	private boolean isFirst;
//	private int startX;
//	private int startY;
	
	public Goblin(int startX, int startY)
	{
		super(new Texture("goblin32.png"));
		setPosition(startX, startY);
		
		this.isFirst = false;
//		this.startX = startX;
//		this.startY = startY;
		this.health = 10;
	}
	
	public boolean getIsFirst()
	{
		return this.isFirst;
	}
	
	public void setIsFirst(boolean first)
	{
		this.isFirst = first;
	}
}
