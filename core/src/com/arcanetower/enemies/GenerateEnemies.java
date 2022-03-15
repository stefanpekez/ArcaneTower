package com.arcanetower.enemies;

import java.util.ArrayList;

public class GenerateEnemies {
	
	private Goblin goblin;
	private ArrayList<Goblin> goblins;
	private int currentWave;
	private int startX, startY;
//	private int amount;
	
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
		this.goblins.clear();
		switch (currentWave) {
		case 0:
			for (int i = 0; i < 3; ++i) {
				goblins.add(new Goblin(startX, startY));
			}
			break;
		case 1:
			for (int i = 0; i < 6; ++i) {
				goblins.add(new Goblin(startX, startY));
			}
			break;
		case 2:
			for (int i = 0; i < 10; ++i) {
				goblins.add(new Goblin(startX, startY));
			}
			break;
		case 3:
			for (int i = 0; i < 15; ++i) {
				goblins.add(new Goblin(startX, startY));
			}
			break;
		case 4:
			for (int i = 0; i < 20; ++i) {
				goblins.add(new Goblin(startX, startY));
			}
			break;
		}
//		if(currentWave == 0)
//		{
//			for (int i = 0; i < 1 + currentWave; ++i) {
//				goblins.add(new Goblin(startX, startY));
//			}
//		}
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
