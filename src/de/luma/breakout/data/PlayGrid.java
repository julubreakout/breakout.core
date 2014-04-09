package de.luma.breakout.data;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.luma.breakout.data.objects.IBall;
import de.luma.breakout.data.objects.IBrick;
import de.luma.breakout.data.objects.IDecodable;
import de.luma.breakout.data.objects.impl.MovingBrick;
import de.luma.breakout.data.objects.impl.SimpleBrick;


/**
 * Playgrid which stores 
 *   - balls
 *   - bricks
 *   - slider
 */
public class PlayGrid implements IDecodable {
	
	private int height;
	private int width;
	
	private List<IBall> balls;
	private List<IBrick> bricks;
	private IBrick slider;
	
	/**
	 * Constructor
	 */
	public PlayGrid(int height, int width) {
		super();
		
		balls = new LinkedList<IBall>();		
		bricks = new LinkedList<IBrick>();
		
		setHeight(height);
		setWidth(width);
	}
	
	/**
	 * Clear grid 
	 * all balls
	 * all bricks
	 * and slider
	 */
	public void clearGrid() {
		getBalls().clear();
		getBricks().clear();
		setSlider(null);
	}
	
	/**
	 * Checks for Collision with border of grid
	 * @param ball
	 * @return True if ball has left the grid, false otherwise
	 */
	public boolean tryCollision(IBall ball) {
		int ballx = (int) ball.getX();
		int bally = (int) ball.getY();
		int ballr = (int) ball.getRadius();
		
		// top
		if (bally - ballr <= 0) {
			ball.setSpeedY(ball.getSpeedY() * (-1));
			ball.setY(ballr + 1);
		}
		
		// left
		if (ballx - ballr <= 0) {
			ball.setSpeedX(ball.getSpeedX() * (-1));
			ball.setX(ballr + 1);
		}
		
		// right
		if (ballx + ballr >= getWidth()) {
			ball.setSpeedX(ball.getSpeedX() * (-1));
			ball.setX(getWidth() - ballr - 1);
		}
		
		// bottom
		if (bally - ballr >= getHeight()) {
			return true;
		} 
		return false;		
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IDecodable#decode(java.lang.String)
	 */
	@Override
	public void decode(String line) {
		String[] s = line.split(",");
		
		setHeight(Integer.valueOf(s[0]));
		setWidth(Integer.valueOf(s[1]));	
		this.getBalls().clear();
	}

	/*
	 * (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IDecodable#encode()
	 */
	@Override
	public String encode() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getHeight());
		sb.append(",");
		sb.append(this.getWidth());	
		return sb.toString();
	}
	
	

	/**
	 * Return a list of Instances of all available bricks.
	 * @return
	 */
	public List<IBrick> getBrickClasses() {
		List<IBrick> retVal =  new ArrayList<IBrick>();		
		retVal.add(new MovingBrick());
		retVal.add(new SimpleBrick());
		return retVal;
	}

	/** */
	public int getHeight() {
		return height;
	}

	/** */
	public final void setHeight(int height) {
		this.height = height;
	}

	/** */
	public int getWidth() {
		return width;
	}

	/** */
	public final void setWidth(int width) {
		this.width = width;
	}

	/** */
	public List<IBall> getBalls() {	
		return balls;
	}

	/** */
	public void addBall(IBall ball) {
		getBalls().add(ball);		
	}

	/** */
	public List<IBrick> getBricks() {
		return bricks;
	}

	/** */
	public void addBrick(IBrick brick) {
		getBricks().add(brick);
	}

	/** */
	public IBrick getSlider() {
		return slider;
	}

	/** */
	public void setSlider(IBrick slider) {
		this.slider = slider;
	}

	
	
	
}
