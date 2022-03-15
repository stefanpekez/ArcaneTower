package com.arcanetower.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Goblin extends Image{
	
	private int health;
	private boolean isFirst;
	private Rectangle bounds;
//	private int startX;
//	private int startY;
	
	public Goblin(int startX, int startY)
	{
		super(new Texture(Gdx.files.internal("goblin32.png")));
		setPosition(startX, startY);
		
		this.isFirst = false;
//		this.startX = startX;
//		this.startY = startY;
		this.health = 100;
		this.bounds = new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	public boolean getIsFirst()
	{
		return this.isFirst;
	}
	
	public void setIsFirst(boolean first)
	{
		this.isFirst = first;
	}
	
	public Rectangle getBounds()
	{
		return this.bounds;
	}
	
	public void takeDamage(int dmg)
	{
		this.health = this.health - dmg;
	}
	
	public int getHealth()
	{
		return this.health;
	}
}
