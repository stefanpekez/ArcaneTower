package com.arcanetower.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BasicEnemy {
	
	private float x, y;
	private Image goblinImage;
	
	private Goblin goblin;
	
	public BasicEnemy(int startX, int startY)
	{
//		this.goblinImage = new Image(new Texture("goblin32.png"));
//		this.x = startX;
//		this.y = startY;
//		this.goblinImage.setPosition(startX, startY);
		
//		if()
		
		this.goblin = new Goblin(startX, startY);
	}
	
	public Goblin getGoblin()
	{
		return this.goblin;
	}
}
