package com.arcanetower.enemies;

import java.util.ArrayList;

public class GenerateEnemies {
	
	private Goblin goblin;
	private ArrayList<Goblin> goblins;
	private int currentWave;
	private int startX, startY;
	private int rate;
	private int thresh;
	private float growthRate;
	
	public GenerateEnemies(int startX, int startY, int currentWave)
	{
		this.goblin = new Goblin(startX, startY);
		this.startX = startX;
		this.startY = startY;
		this.goblins = new ArrayList<Goblin>();
		this.currentWave = currentWave;
		this.rate = 3;
		this.thresh = 15;
		this.growthRate = 0.5f;
		generateEnemies();
	}
	
	public ArrayList<Goblin> generateEnemies()
	{
		this.goblins.clear();
		
		if(currentWave == 0)
			for (int i = 0; i < rate; ++i)
				goblins.add(new Goblin(startX, startY));
		else
		{
			if(rate >= thresh)
			{
				growthRate = 0.2f;
			}
			rate = rate + (int) (growthRate * rate);
			
			for (int i = 0; i < rate; ++i)
				goblins.add(new Goblin(startX, startY));
		}
		
		return goblins;
	}
	
	public Goblin getGoblin()
	{
		return this.goblin;
	}
	
	public int getCurrentWave()
	{
		return this.currentWave;
	}
	
	public void setCurrentWave(int currentWave)
	{
		this.currentWave = currentWave;
	}

	public ArrayList<Goblin> getGoblins() {
		return goblins;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}
	
	

}
