package com.arcanetower.enemies;

public class GenerateEnemies {
	
	private BasicEnemy goblin;
	private int currentWave;
	
	public GenerateEnemies(int startX, int startY, int currentWave)
	{
		this.goblin = new BasicEnemy(startX, startY);
		this.currentWave = currentWave;
	}
	
	public BasicEnemy getGoblin()
	{
		return this.goblin;
	}

}
