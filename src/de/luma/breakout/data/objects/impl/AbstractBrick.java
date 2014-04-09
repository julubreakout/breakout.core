package de.luma.breakout.data.objects.impl;

import java.util.Properties;

import de.luma.breakout.data.objects.IBall;
import de.luma.breakout.data.objects.IBrick;

/**
 * Class for ground component of Game
 * @author mabausch
 *
 */
public abstract class AbstractBrick implements IBrick {

	private final static int HEIGHT = 3;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int hitCount;
	
	private Properties properties;

	/**
	 * Default constructor
	 */
	public AbstractBrick() {
		super();
		properties = new Properties();
	}

	/**
	 * constructor with all arguments
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public AbstractBrick(int x, int y, int width, int height) {
		this();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	    this.hitCount = 0;
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#onNextFrame()
	 */
	@Override
	public void onNextFrame() {	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#getX()
	 */
	@Override
	public int getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#setX(int)
	 */
	@Override
	public void setX(int x) {
		this.x = x;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#getY()
	 */
	@Override
	public int getY() {
		return y;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#setY(int)
	 */
	@Override
	public void setY(int y) {
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#setWidth(int)
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#setHeight(int)
	 */
	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Get count of previous collisions with a ball.
	 */
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * Set count of previous collisions with a ball.
	 */
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}	
	
	/**
	 * Returns true if this brick can be deleted in the next game frame.
	 */
	protected boolean isBrickDead() {
		// is deleted by one hit of the ball
		if (getHitCount() >= 1) {
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#tryCollision(de.luma.breakout.data.objects.IBall)
	 */
	@Override
	public boolean tryCollision(IBall b) {		
		// increase hit counter if brick was hit by a ball
		if (tryCollisionRectangle(b)) {
			setHitCount(getHitCount()+1);
		}
		
		return isBrickDead();
	}


	/**
	 * This mthod return ture if a balls X is Between left and right border of this brick
	 * @param ballx
	 * @return
	 */
	protected boolean matchesXRange(int ballx) {
		return ballx > this.getX() && ballx < this.getX() + this.getWidth();		
	}

	/**
	 * This method returns true if a balls Y is Between top and bottom of this brick
	 * @param bally
	 * @return  
	 */
	protected boolean matchesYRange(int bally) {
		return bally > this.getY() && bally < this.getY() + this.getHeight();
	}

	/**
	 * This mthod checks if the value is betweeen min and max
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	protected boolean isBetween(int value, int min, int max) {
		return value >= min && value <= max;
	}

	/**
	 * Hit-test helper for rectangular bricks. Changes ball movement
	 * when a collision was detected.
	 * @param b
	 * @return True if ball was hit, false if not.
	 */
	protected boolean tryCollisionRectangle(IBall b) {
		boolean isHit = false;
		int ballx = (int) b.getX();
		int bally = (int) b.getY();
		int ballr = (int) b.getRadius();

		// Collision with top border
		if (matchesXRange(ballx) 
				&& isBetween(bally + ballr, this.getY(), this.getY() + this.getHeight())
				&& b.getSpeedY() > 0) {
				// ballx matches bricks width
			
			// invert speedy
			b.inverseSpeedY(); 
			b.setY(this.getY() - b.getRadius() - 1);
			isHit = true;
		}

		// Collision with bottom border
		if (matchesXRange(ballx) 
				&& isBetween(bally - ballr, this.getY(), this.getY() + this.getHeight())
				&& b.getSpeedY() < 0) {
				// ballx matches bricks width
			
			// invert speedy
			b.inverseSpeedY();
			b.setY(this.getY() + this.getHeight() + b.getRadius() + 1);
			isHit = true;
		}

		// Collision with left border
		if (matchesYRange(bally) 
				&& isBetween(ballx + ballr, this.getX(), this.getX() + this.getWidth())
				&& b.getSpeedX() > 0) { 
			
			// invert speedx
			b.inverseSpeedX(); 
			b.setX(this.getX() - b.getRadius() - 1);
			isHit = true;
		}


		// Collision with right border
		if (matchesYRange(bally) 
				&& isBetween(ballx - ballr, this.getX(), this.getX() + this.getWidth())
				&& b.getSpeedX() < 0) { 
			
			// invert speedx
			b.inverseSpeedX();
			b.setX(this.getX() + this.getWidth() + b.getRadius() + 1);
			isHit = true;
		}

		return isHit; 
	}


	/**
	 * Encodes the basic parameters of a brick (x, y, width, height) as a string.
	 * 
	 * @return Returns a string builder that can be used to append custom 
	 * brick properties. No comma is inserted at the end of the string.
	 */
	protected StringBuilder encodeBasic() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%d,", this.getX()));
		sb.append(String.format("%d,", this.getY()));
		sb.append(String.format("%d,", this.getWidth()));
		sb.append(String.format("%d", this.getHeight()));

		return sb;
	}
	
	/**
	 * Decodes the basic parametres of a brick (x, y, width, height) from a string.
	 * 
	 * @return Returns the line in parametre splited by , into String[]
	 */
	protected String[] decodeBasic(String line) {
		String[] s = line.split(",");
		setX(Integer.valueOf(s[0]));
		setY(Integer.valueOf(s[1]));
		setWidth(Integer.valueOf(s[2]));
		setHeight(Integer.valueOf(s[HEIGHT]));	
		
		return s;
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IBrick#getProperties()
	 */
	@Override
	public Properties getProperties() {
		return properties;
	}

}
