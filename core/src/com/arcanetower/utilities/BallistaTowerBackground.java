package com.arcanetower.utilities;

import java.util.ArrayList;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.ui.InfoLabels;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class BallistaTowerBackground extends Image {
	
	private float xpos;
	private float ypos;
	
	private float h;
	private float k;
	private float Xc;
	private float Yc;
	private float radius;
	private ShapeRenderer sr;
	private ArrowBallista arrow;
	private MoveToAction mta;
	private Timer timerNormal;
	private Timer timerFast;
	private ArrayList<Enemy> enemies;
	private boolean isHovered;
	private Sound arrowShot;
	private InfoLabels infoLabels;
	
	public BallistaTowerBackground(float xpos, float ypos, ArrayList<Enemy> enemies)
	{
		super(new Texture(Gdx.files.internal("menu\\ballistaTowerBG.png")));
		this.xpos = xpos;
		this.ypos = ypos;
		
		setPosition(xpos, ypos);
		
		this.h = xpos;
		this.k = ypos;
		Xc = h + this.getWidth() / 2;
		Yc = k + this.getHeight() / 2;
		this.radius = 128f;
		this.enemies = enemies;
		this.isHovered = false;
		
//		this.arrowShot = Gdx.audio.newSound(Gdx.files.internal("effects\\shoot.ogg"));
		
		setupTimer();
	}
	
	private void setupTimer()
	{
		timerNormal = new Timer();
		timerNormal.scheduleTask(new Timer.Task() {
		    @Override
		    public void run() {
		    	if(enemies.size() > 0)
		    	{
		    		checkRadius(enemies);
		    		for(int i = 0; i < enemies.size(); ++i)
		    		{
		    			if(enemies.get(i).getInRange())
		    			{
		    				shootArrow(enemies.get(i).getX(), enemies.get(i).getY());
					    	enemies.get(i).takeDamage(5);
						    if(enemies.get(i).getHealth() <= 0)
						    {
								enemies.get(i).remove();
								enemies.remove(i);
						        return;
						    }
		    				break;
		    			}
		    		}
		    	}
		    }
		}, 0f, 1.0f);
	}
	
	public void checkRadius(ArrayList<Enemy> enemies)
	{
		for(int i = 0; i < enemies.size(); ++i)
		{
			float Xn = Math.max(enemies.get(i).getX(), Math.min(Xc, enemies.get(i).getX() + 31f));
			float Yn = Math.max(enemies.get(i).getY(), Math.min(Yc, enemies.get(i).getY() + 31f));
		
			float Dx = Xn - Xc;
			float Dy = Yn - Yc;
		
			if(Dx * Dx + Dy * Dy <= radius * radius)
			{
				enemies.get(i).setInRange(true);
			}
			else
			{
				enemies.get(i).setInRange(false);
			}
				
		}
	}
	
	public void shootArrow(final float x, final float y)
	{
		mta = new MoveToAction();
		mta = Actions.moveTo(x  + (32 - 16) / 2, y  + (32 - 16) / 2, 0.05f);
		arrow = new ArrowBallista();
		arrow.setPosition(xpos, ypos);
		arrow.addAction(Actions.sequence(mta, Actions.run(new Runnable() {
						
			@Override
			public void run() {
				// TODO Auto-generated method stub
				arrow.remove();
			}
		})));
		getStage().addActor(arrow);
	}
	
	public void setIsHovered(boolean isHovered)
	{
		this.isHovered = isHovered;
	}
	
	public void setEnemies(ArrayList<Enemy> enemies)
	{
		this.enemies = enemies;
	}
}
