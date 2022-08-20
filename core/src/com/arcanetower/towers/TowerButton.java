package com.arcanetower.towers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TowerButton extends Image {

	private boolean isClicked;
//	private ShapeRenderer sr;
	private boolean isHovered;
	private boolean isDisabled;
	private int speedBeforeSelect;
	
	public TowerButton(TextureRegionDrawable drawable)
	{
		super(drawable);
		this.isClicked = false;
//		this.sr = new ShapeRenderer();
		this.isHovered = false;
		this.isDisabled = false;
	}
	
	public void setSpeedBeforeSelect(int speedBeforeSelect)
	{
		this.speedBeforeSelect = speedBeforeSelect;
	}
	
	public int getSpeedBeforeSelect()
	{
		return this.speedBeforeSelect;
	}
	
	public boolean getIsClicked()
	{
		return this.isClicked;
	}
	
	public void setIsClicked(boolean clicked)
	{
		this.isClicked = clicked;
	}
	
//	@Override
//	public void draw(Batch batch, float parentAlpha) {
//		// TODO Auto-generated method stub
//		super.draw(batch, parentAlpha);
//		
//		if(isHovered == false)
//        	return;
//
//        batch.end();
//        
//        sr.setProjectionMatrix(batch.getProjectionMatrix());
//        sr.setColor(Color.BLACK);
//        
//        sr.begin(ShapeRenderer.ShapeType.Line);
//
//
//        sr.circle(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2, 128f, 500);
//        sr.end();
//
//        batch.begin();
//	}
	
	public void setIsHovered(boolean isHovered)
	{
		this.isHovered = isHovered;
	}
	
	public void setDisabled(boolean isDisabled)
	{
		this.isDisabled = isDisabled;
	}
	
	public boolean isDisabled()
	{
		return this.isDisabled;
	}
}
