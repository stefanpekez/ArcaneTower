package com.arcanetower.towers;

import java.util.ArrayList;

import com.arcanetower.enemies.Enemy;
import com.arcanetower.ui.InfoLabels;
import com.arcanetower.utilities.ArrowBallista;
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

public class BallistaTower extends Image {
	
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
	private Timer timer;
	private Timer timerNormal;
	private Timer timerFast;
	private ArrayList<Enemy> enemies;
	private boolean isHovered;
	private Sound arrowShot;
	private InfoLabels infoLabels;
	private ArrayList<ArrowBallista> arrowsShot;
	private int speed;
	
	public BallistaTower(float xpos, float ypos, ArrayList<Enemy> enemies, InfoLabels infoLabels, int speed)
	{
		super(new Texture(Gdx.files.internal("ballistaTower.png")));
		this.xpos = xpos;
		this.ypos = ypos;
		this.speed = speed;
		
		setPosition(xpos, ypos);
		
		this.h = xpos;
		this.k = ypos;
		Xc = h + this.getWidth() / 2;
		Yc = k + this.getHeight() / 2;
		this.radius = 128f;
		this.enemies = enemies;
		this.isHovered = false;
		
		this.arrowShot = Gdx.audio.newSound(Gdx.files.internal("effects\\shoot.ogg"));
		this.arrowsShot = new ArrayList<ArrowBallista>();
		
		this.infoLabels = infoLabels;
		
		setupTimer();
		sr = new ShapeRenderer();
	}
	
	private void setupTimer()
	{
//		timer = new Timer();
//			timer.scheduleTask(new Timer.Task() {
//			    @Override
//			    public void run() {
//			    	if(enemies.size() > 0)
//			    	{
//			    		checkRadius(enemies);
//			    		for(int i = 0; i < enemies.size(); ++i)
//			    		{
//			    			if(enemies.get(i).getInRange())
//			    			{
//			    				shootArrow(enemies.get(i).getX(), enemies.get(i).getY());
//						    	arrowShot.play();
//						    	enemies.get(i).takeDamage(5);
//							    if(enemies.get(i).getHealth() <= 0)
//							    {
//							    	enemies.get(i).playDeathSound();
//									infoLabels.addMoney(enemies.get(i).getBounty());
//									enemies.get(i).remove();
//									enemies.remove(i);
//							        return;
//							    }
//			    				break;
//			    			}
//			    		}
//			    	}
//			    }
//			}, 0f, 1.5f);
			
			
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
		    				System.out.println("shoot normal");
		    				shootArrow(enemies.get(i).getX(), enemies.get(i).getY());
					    	arrowShot.play();
					    	enemies.get(i).takeDamage(5);
						    if(enemies.get(i).getHealth() <= 0)
						    {
						    	enemies.get(i).playDeathSound();
								infoLabels.addMoney(enemies.get(i).getBounty());
								enemies.get(i).remove();
								enemies.remove(i);
						        return;
						    }
		    				break;
		    			}
		    		}
		    	}
		    }
		}, 0, 1.5f);
		timerNormal.stop();
		
		timerFast = new Timer();
		timerFast.scheduleTask(new Timer.Task() {
		    @Override
		    public void run() {
		    	if(enemies.size() > 0)
		    	{
		    		checkRadius(enemies);
		    		for(int i = 0; i < enemies.size(); ++i)
		    		{
		    			if(enemies.get(i).getInRange())
		    			{
		    				System.out.println("shoot fast");
		    				shootArrow(enemies.get(i).getX(), enemies.get(i).getY());
					    	arrowShot.play();
					    	enemies.get(i).takeDamage(5);
						    if(enemies.get(i).getHealth() <= 0)
						    {
						    	enemies.get(i).playDeathSound();
								infoLabels.addMoney(enemies.get(i).getBounty());
								enemies.get(i).remove();
								enemies.remove(i);
						        return;
						    }
		    				break;
		    			}
		    		}
		    	}
		    }
		}, 1.5f / 2, 1.5f / 2);
		timerFast.stop();
	}
	
	public void stopTimer()
	{
		timerNormal.stop();
		timerFast.stop();
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public void resumeTimer(int speed)
	{
		if(speed == 1)
		{
			timerNormal.start();
			timerFast.stop();
		} else
		{
			timerNormal.stop();
			timerFast.start();
		}
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
		getStage().addActor(arrow);
		arrow.setPosition(xpos, ypos);
		arrow.addAction(Actions.sequence(mta, Actions.run(new Runnable() {
						
			@Override
			public void run() {
				// TODO Auto-generated method stub
				arrow.remove();
			}
		})));
	}
	
	
	@Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(isHovered == false)
        	return;

        batch.end();
        
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setColor(Color.BLACK);
        
        sr.begin(ShapeRenderer.ShapeType.Line);


        sr.circle(h + this.getWidth() / 2, k + this.getHeight() / 2, radius, 500);
        sr.end();

        batch.begin();
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
