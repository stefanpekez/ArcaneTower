package com.arcanetower.enemies;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class NextWave {
	
	private Stage stage;
	private BasicEnemy goblin;

	public NextWave(Stage stage, BasicEnemy goblin)
	{
		this.stage = stage;
		this.goblin = goblin;
		GenerateMovement();
		stage.addActor(this.goblin.getGoblin());
	}
	
	public void GenerateMovement()
	{
		float destX = this.goblin.getGoblin().getX() + 32;
		float destY = this.goblin.getGoblin().getY();
		
		this.goblin.getGoblin().addAction(Actions.moveTo(destX, destY, 1f));
	}
}
