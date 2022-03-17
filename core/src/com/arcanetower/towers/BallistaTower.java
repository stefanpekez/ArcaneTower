package com.arcanetower.towers;

import java.util.ArrayList;

import com.arcanetower.enemies.Goblin;
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
	private Timer timerNormal;
	private Timer timerFast;
	private ArrayList<Goblin> goblins;
	private boolean isHovered;
	private Sound arrowShot;
	private InfoLabels infoLabels;
	
	public BallistaTower(float xpos, float ypos, ArrayList<Goblin> goblins, InfoLabels infoLabels)
	{
		super(new Texture(Gdx.files.internal("ballistaTower.png")));
		this.xpos = xpos;
		this.ypos = ypos;
		
		setPosition(xpos, ypos);
		
		this.h = xpos;
		this.k = ypos;
		Xc = h + this.getWidth() / 2;
		Yc = k + this.getHeight() / 2;
		this.radius = 128f;
		this.goblins = goblins;
		this.isHovered = false;
		
		this.arrowShot = Gdx.audio.newSound(Gdx.files.internal("effects\\shoot.ogg"));
		
		this.infoLabels = infoLabels;
		
		setupTimer();
		sr = new ShapeRenderer();
	}
	
	private void setupTimer()
	{
		timerNormal = new Timer();
		timerNormal.scheduleTask(new Timer.Task() {
		    @Override
		    public void run() {
		    	if(goblins.size() > 0)
		    	{
		    		checkRadius(goblins);
		    		for(int i = 0; i < goblins.size(); ++i)
		    		{
		    			if(goblins.get(i).getInRange())
		    			{
		    				shootArrow(goblins.get(i).getX(), goblins.get(i).getY());
					    	arrowShot.play();
						    goblins.get(i).takeDamage(5);
						    if(goblins.get(i).getHealth() <= 0)
						    {
						        Sound sound = Gdx.audio.newSound(Gdx.files.internal("effects\\goblinDeath.ogg"));
								sound.play();
								infoLabels.addMoney(goblins.get(i).getBounty());
						        goblins.get(i).remove();
						        goblins.remove(i);
						        return;
						    }
		    				break;
		    			}
		    		}
		    	}
		    }
		}, 0f, 1.5f);
		
		timerFast = new Timer();
		timerFast.scheduleTask(new Timer.Task() {
			@Override
		    public void run() {
		    	if(goblins.size() > 0)
		    	{
		    		checkRadius(goblins);
		    		for(int i = 0; i < goblins.size(); ++i)
		    		{
		    			if(goblins.get(i).getInRange())
		    			{
		    				shootArrow(goblins.get(i).getX(), goblins.get(i).getY());
					    	arrowShot.play();
						    goblins.get(i).takeDamage(5);
						    if(goblins.get(i).getHealth() <= 0)
						    {
						        Sound sound = Gdx.audio.newSound(Gdx.files.internal("effects\\goblinDeath.ogg"));
								sound.play();
								infoLabels.addMoney(goblins.get(i).getBounty());
						        goblins.get(i).remove();
						        goblins.remove(i);
						        return;
						    }
		    				break;
		    			}
		    		}
		    	}
		    }
		}, 0f, (1.5f / 2f));
		timerFast.stop();
	}
	
	public void stopTimer()
	{
		timerNormal.stop();
		timerFast.stop();
	}
	
	public void resumeTimer(int speed)
	{
		if(speed == 1)
		{
			timerNormal.start();
			timerFast.stop();
		}
			
		else
		{
			timerNormal.stop();
			timerFast.start();
		}
			
	}
	
	public void checkRadius(ArrayList<Goblin> goblins)
	{
		for(int i = 0; i < goblins.size(); ++i)
		{
			float Xn = Math.max(goblins.get(i).getX(), Math.min(Xc, goblins.get(i).getX() + 31f));
			float Yn = Math.max(goblins.get(i).getY(), Math.min(Yc, goblins.get(i).getY() + 31f));
		
			float Dx = Xn - Xc;
			float Dy = Yn - Yc;
		
			if(Dx * Dx + Dy * Dy <= radius * radius)
			{
				goblins.get(i).setInRange(true);
			}
			else
			{
				goblins.get(i).setInRange(false);
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
	
	public void setGoblins(ArrayList<Goblin> goblins)
	{
		this.goblins = goblins;
	}
}
