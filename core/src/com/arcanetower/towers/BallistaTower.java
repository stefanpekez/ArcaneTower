package com.arcanetower.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BallistaTower extends ImageButton {
	
	public BallistaTower(float clickedTileX, float clickedTileY)
	{
		super(new TextureRegionDrawable(new Texture("ballistaTower.png")));
		setPosition(clickedTileX, clickedTileY);
	}
}
