package com.arcanetower.towers;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TowerButton extends ImageButton {

	private boolean isClicked;
	
	public TowerButton(TextureRegionDrawable drawable)
	{
		super(drawable);
		this.isClicked = false;
	}
	
	public boolean getIsClicked()
	{
		return this.isClicked;
	}
	
	public void setIsClicked(boolean clicked)
	{
		this.isClicked = clicked;
	}
}
