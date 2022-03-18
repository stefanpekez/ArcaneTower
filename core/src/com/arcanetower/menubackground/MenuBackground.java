package com.arcanetower.menubackground;

import com.arcanetower.enemies.NextWave;
import com.arcanetower.terrain.TerrainGenerator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MenuBackground {
	
	private BackgroundGenerator generatorTerrain;
	private NextWaveBackground nextWave;
	private Stage stageBackground;
	private BackgroundTowers generatorTower;

	public MenuBackground(final Stage stageBackground) {
		// TODO Auto-generated constructor stub
		this.stageBackground = stageBackground;
		this.generatorTerrain = new BackgroundGenerator(stageBackground);
		nextWave = new NextWaveBackground(stageBackground, generatorTerrain);
		
		generatorTower = new BackgroundTowers(stageBackground, nextWave.getEnemies(), generatorTerrain);
		
	}
	
	public NextWaveBackground getNextWave()
	{
		return this.nextWave;
	}
	
	public BackgroundGenerator getGenerator()
	{
		return this.generatorTerrain;
	}
}
