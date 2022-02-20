package com.arcanetower.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class CreateFont {
	
	private int viewportHeight;
	BitmapFont textFont;
	
	public CreateFont()
	{
		FileHandle fontFile = Gdx.files.internal("NotoSans-Regular.ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.borderColor = Color.BLACK;
	    parameter.borderStraight = true;
	    parameter.borderWidth = 1.2f;
	    parameter.size = 24;
	    textFont = generator.generateFont(parameter);
	    generator.dispose();
	}
	
	public BitmapFont getTextFont()
	{
		return this.textFont;
	}
}
