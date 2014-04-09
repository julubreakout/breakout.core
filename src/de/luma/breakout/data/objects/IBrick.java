package de.luma.breakout.data.objects;

import java.util.Properties;

/**
 * Interface for Brick.
 * @author mabausch
 *
 */
public interface IBrick extends IDecodable {

	String PROP_COLOR = "color";
	String PROP_IMG_PATH = "imgPath";

	/**
	 * This method gets called by the game controller
	 * at every new frame that is calculated.
	 */
	void onNextFrame();

	/** */
	int getX();

	/** */
	void setX(int x);

	/** */
	int getY();

	/** */
	void setY(int y);

	/** */
	int getWidth();

	/** */
	void setWidth(int width);

	/** */
	int getHeight();

	/** */
	void setHeight(int height);

	/**
	 * This method checks if a collision happens with a ball
	 * @return true if collision happens
	 *         false otherwise
	 */
	boolean tryCollision(IBall b);

	/**
	 * returns the Properties like ImagePath usw. 
	 * @return Propertie Instance
	 */
	Properties getProperties();

}