package com.arcanetower.towers;

import java.util.ArrayList;

public class PlacedTowers {
	
	private ArrayList<BallistaTower> towers;
	
	public PlacedTowers()
	{
		this.towers = new ArrayList<BallistaTower>();
	}
	
	public void addTower(BallistaTower bt)
	{
		this.towers.add(bt);
	}
	
	public ArrayList<BallistaTower> getPlacedTowers()
	{
		return this.towers;
	}
}
