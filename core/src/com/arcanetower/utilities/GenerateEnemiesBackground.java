package com.arcanetower.utilities;

import java.util.ArrayList;
import java.util.Random;

import com.arcanetower.enemies.Enemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GenerateEnemiesBackground {
	
	private ArrayList<Enemy> enemies;
	private int currentWave;
	private int startX, startY;
	private int rate;
	private int thresh;
	private float growthRate;
	private int skeletonAmount;
	private Random rand;
	private ArrayList<Integer> skeletonPositions;
	
	public GenerateEnemiesBackground(int startX, int startY, int currentWave)
	{
		this.startX = startX;
		this.startY = startY;
		this.enemies = new ArrayList<Enemy>();
		this.currentWave = currentWave;
		this.rate = 3;
		this.thresh = 15;
		this.growthRate = 0.5f;
		this.skeletonAmount = 0;
		
		rand = new Random();
		this.skeletonPositions = new ArrayList<Integer>();
		
		generateEnemies();
	}
	
	public ArrayList<Enemy> generateEnemies()
	{
		this.enemies.clear();
		
		if(currentWave == 0)
			for (int i = 0; i < rate; ++i)
					enemies.add(new Enemy(new Texture(Gdx.files.internal("menu\\goblin32BG.png")), startX, startY, 
							10, 5, 1, Gdx.audio.newSound(Gdx.files.internal("effects\\goblinDeath.ogg"))));
		else
		{
			if(rate >= 15)
			{
				growthRate = 0f;
			}
			rate = rate + (int) (growthRate * rate);
			skeletonAmount = (int) (rate * 0.25f);
			
			for(int i = 0; i < skeletonAmount; ++i)
			{
				skeletonPositions.add(rand.nextInt(((rate - 1) - i) + 1) + i);
			}
			
			for (int i = 0; i < rate; ++i)
			{
				if(skeletonPositions.contains(i))
				{
					enemies.add(new Enemy(new Texture(Gdx.files.internal("menu\\skeleton32BG.png")), startX, startY, 
							25, 10, 2, Gdx.audio.newSound(Gdx.files.internal("effects\\skeletonDeath.ogg"))));
					--skeletonAmount;
					continue;
				} else
				{
					enemies.add(new Enemy(new Texture(Gdx.files.internal("menu\\goblin32BG.png")), startX, startY, 
							10, 5, 1, Gdx.audio.newSound(Gdx.files.internal("effects\\goblinDeath.ogg"))));
				}
			}
				
		}
		
		return enemies;
	}
	
	public int getCurrentWave()
	{
		return this.currentWave;
	}
	
	public void setCurrentWave(int currentWave)
	{
		this.currentWave = currentWave;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}
}
