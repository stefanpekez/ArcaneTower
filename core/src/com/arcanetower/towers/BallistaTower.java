package com.arcanetower.towers;

import java.util.ArrayList;

import com.arcanetower.enemies.Goblin;
import com.arcanetower.utilities.ArrowBallista;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;

public class BallistaTower extends Image {
	
	private float xpos;
	private float ypos;
	
	private float h;
	private float k;
	private float radius;
	private ShapeRenderer sr;
	private ArrowBallista arrow;
	private MoveToAction mta;
	private Timer timer;
	private ArrayList<Goblin> goblins;
	
	public BallistaTower(float xpos, float ypos, ArrayList<Goblin> goblins)
	{
		super(new Texture("ballistaTower.png"));
		this.xpos = xpos;
		this.ypos = ypos;
		
		setPosition(xpos, ypos);
		
		this.h = xpos;
		this.k = ypos;
		this.radius = 128f;
		this.goblins = goblins;
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
			        	goblins.get(0).takeDamage(5);
			        	if(goblins.get(0).getHealth() <= 0)
			        	{
			        		goblins.get(0).remove();
			        		goblins.remove(0);
			        		return;
			        	}
			        }
		    	}
		    }
		}, 0f, 1f);
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
		float pos = (float) Math.sqrt(Math.pow(x-(h + this.getWidth() / 2), 2) + Math.pow(y-(k + this.getHeight() / 2), 2));
		if(pos < radius)
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

        batch.end();
        
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setColor(Color.BLUE);
        
        sr.begin(ShapeRenderer.ShapeType.Line);


        sr.circle(h + this.getWidth() / 2, k + this.getHeight() / 2, radius);
        sr.end();

        batch.begin();
    }
}
