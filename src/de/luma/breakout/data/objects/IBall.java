package de.luma.breakout.data.objects;

/**
 * Interface for Ball
 * @author mabausch
 *
 */
public interface IBall extends IDecodable {

	/** Maximum absolute speed that a ball can reach */
	double MAX_BALL_SPEED = 10.0;

	/** */
	double getX();
	/** */
	void setX(double x);
	/** */
	double getY();
	/** */
	void setY(double y);
	/** */
	double getSpeedX();
	/** */
	void setSpeedX(double speedX);
	/** */
	double getSpeedY();
	/** */
	void setSpeedY(double speedY);
	/** */
	double getRadius();
	/** */
	void setRadius(double radius);
	/** */
	void inverseSpeedX();
	/** */
	void inverseSpeedY();
	
	/** 
	 * get the real speed |Vector|
	 * @return x
	 */
	double getAbsoluteSpeed();

}