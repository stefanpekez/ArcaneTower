package com.arcanetower.enemies;

import java.util.ArrayList;
import java.util.Iterator;

public class GenerateEnemies {
	
	private Goblin goblin;
	private ArrayList<Goblin> goblins;
	private int currentWave;
	private int startX, startY;
	private int amount;
	
	public GenerateEnemies(int startX, int startY, int currentWave)
	{
		this.goblin = new Goblin(startX, startY);
		this.startX = startX;
		this.startY = startY;
		this.goblins = new ArrayList<Goblin>();
		this.currentWave = currentWave;
		generateEnemies();
	}
	
	public ArrayList<Goblin> generateEnemies()
	{
		if(currentWave == 0)
		{
			for (int i = 0; i < 5; ++i) {
				goblins.add(new Goblin(startX, startY));
			}
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
