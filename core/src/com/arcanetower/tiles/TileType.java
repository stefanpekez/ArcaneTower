package com.arcanetower.tiles;

public enum TileType {
	
	Grass("grass", 0), Path("path", 1);
	
	String textureName;
	int type;
	
	TileType(String textureName, int type)
	{
		this.textureName = textureName;
		this.type = type;
	}
}
