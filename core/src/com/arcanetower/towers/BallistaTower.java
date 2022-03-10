package com.arcanetower.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BallistaTower extends Image {
	
	private float xpos;
	private float ypos;
	
	public BallistaTower(float xpos, float ypos)
	{
		super(new Texture("ballistaTower.png"));
		this.xpos = xpos;
		this.ypos = ypos;
		
		setPosition(xpos, ypos);
	}
}
