package com.arcanetower.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BasicEnemy {
	
	private float x, y;
	private Image goblin;
	
	public BasicEnemy(int startX, int startY)
	{
		this.goblin = new Image(new Texture("goblin32.png"));
		this.x = startX;
		this.y = startY;
		this.goblin.setPosition(startX, startY);
	}
	
	public Image getGoblin()
	{
		return this.goblin;
	}
}
