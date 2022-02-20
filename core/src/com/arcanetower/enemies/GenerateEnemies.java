package com.arcanetower.enemies;

public class GenerateEnemies {
	
	private BasicEnemy goblin;
	
	public GenerateEnemies(int startX, int startY)
	{
		this.goblin = new BasicEnemy(startX - 32, startY);
	}
	
	public BasicEnemy getGoblin()
	{
		return this.goblin;
	}

}
