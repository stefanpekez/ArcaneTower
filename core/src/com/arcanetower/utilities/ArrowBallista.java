package com.arcanetower.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ArrowBallista extends Image{
	
	private Rectangle bounds;
	
	public ArrowBallista()
	{
		super(new Texture(Gdx.files.internal("ArrowBallista16.png")));
		this.setPosition(getOriginX(), getOriginY());
		this.bounds = new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
	}
	
	public Rectangle getBounds()
	{
        return this.bounds;
    }
	
	public void setXY(float pX,float pY) {
        setPosition(pX, pY);
        bounds.setX((int)pX);
        bounds.setY((int)pY);
    }

}
