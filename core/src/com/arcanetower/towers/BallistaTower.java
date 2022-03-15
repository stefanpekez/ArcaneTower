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
	private Timer timer;
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
		timer = new Timer();
		timer.scheduleTask(new Timer.Task() {
		    @Override
		    public void run() {
		    	if(goblins.size() > 0)
		    	{
		    		if(checkRadius(goblins.get(0).getX(), goblins.get(0).getY()))
			        {
		    			shootArrow(goblins.get(0).getX(), goblins.get(0).getY());
		    			arrowShot.play();
			        	goblins.get(0).takeDamage(5);
			        	if(goblins.get(0).getHealth() <= 0)
			        	{
			        		Sound sound = Gdx.audio.newSound(Gdx.files.internal("effects\\goblinDeath.ogg"));
							sound.play();
			        		goblins.get(0).remove();
			        		goblins.remove(0);
			        		infoLabels.addMoney(10);
			        		return;
			        	}
			        }
		    	}
		    }
		}, 0f, 1.5f);
	}
	
	public void stopTimer()
	{
		timer.stop();
	}
	
	public void resumeTimer()
	{
		timer.start();
	}
	
	public boolean checkRadius(float x, float y)
	{
		float Xn = Math.max(x, Math.min(Xc, x + 31f));
		float Yn = Math.max(y, Math.min(Yc, y + 31f));
		
		float Dx = Xn - Xc;
		float Dy = Yn - Yc;
		
		if(Dx * Dx + Dy * Dy <= radius * radius)
			return true;
		return false;
	}
	
	public void shootArrow(float x, float y)
	{
		// TODO: shoot goblins
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
		this.getStage().addActor(arrow);
		
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
