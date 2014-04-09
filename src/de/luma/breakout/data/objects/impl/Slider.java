package de.luma.breakout.data.objects.impl;

import de.luma.breakout.data.objects.IBall;


/**
 * Data class for  Slider Component.
 * @author mabausch
 *
 */
public class Slider extends AbstractBrick {
	
	private static final int SPEED_FACTOR = 3;
	
	/**
	 * 
	 */
	public Slider() {
		super();
	}
	
	/**
	 * construcot with all arguments
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Slider(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/*
	 * (non-Javadoc)
	 * @see de.luma.breakout.data.objects.impl.AbstractBrick#tryCollision(de.luma.breakout.data.objects.IBall)
	 */
	@Override
	public boolean tryCollision(IBall b) {
		boolean ret = tryCollisionRectangle(b);
		if (ret && b.getAbsoluteSpeed() < IBall.MAX_BALL_SPEED) {
			double delta = b.getX() - (this.getX() + this.getWidth() / 2);
			// -0.5  to 0.5
			double diversionFactor = delta / this.getWidth();  
			b.setSpeedX(b.getSpeedX() + SPEED_FACTOR * Math.sin(diversionFactor));
		}

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IDecodable#decode(java.lang.String)
	 */
	@Override
	public void decode(String line) {
		super.decodeBasic(line);
	}

	/*
	 * (non-Javadoc)
	 * @see de.luma.breakout.data.objects.IDecodable#encode()
	 */
	@Override
	public String encode() {		
		return super.encodeBasic().toString();
	}

}
