package com.arcanetower.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Enemy extends Image{
	
	private int health;
	private boolean isFirst;
	private Rectangle bounds;
	private boolean inRange;
	private int bounty;
	private int heartDmg;
	private Sound deathSound;
	
	public Enemy(Texture enemyTexture, int startX, int startY, int health, int bounty, int heartDmg , Sound deathSound)
	{
		super(enemyTexture);
		setPosition(startX, startY);
		
		this.isFirst = false;
		this.inRange = false;
		this.health = health;
		this.bounds = new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
		this.bounty = bounty;
		this.heartDmg = heartDmg;
		this.deathSound = deathSound;
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
	
	public boolean getInRange()
	{
		return this.inRange;
	}
	
	public void setInRange(boolean inRange)
	{
		this.inRange = inRange;
	}
	
	public int getBounty()
	{
		return this.bounty;
	}
	
	public int getHeartDMG()
	{
		return this.heartDmg;
	}
	
	public Sound getDeathSound()
	{
		return this.deathSound;
	}
	
	public void playDeathSound()
	{
		this.deathSound.play();
	}

}
