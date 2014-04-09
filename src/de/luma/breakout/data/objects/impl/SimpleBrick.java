package de.luma.breakout.data.objects.impl;

import java.awt.Color;

/**
 * Data class for brick with fix size.
 * @author mabausch
 *
 */
public class SimpleBrick extends AbstractBrick {	
	
	private static final  int DEFAULT_WIDTH = 50;
	private static final  int DEFAULT_HEIGHT = 20;
	
	/**
	 * constructor 
	 * @param x
	 * @param y
	 */
	public SimpleBrick(int x, int y) {
		super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		getProperties().setProperty(PROP_COLOR, Color.blue.toString());
		getProperties().setProperty(PROP_IMG_PATH, "resources\\simpleBrick.png");
	}
	
	/**
	 * Constructor which set brick to  x 0 y 0.
	 */
	public SimpleBrick() {
		this(0, 0);
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

	
	/**
	 * SimpleBrick is not resizable, setWidth will be ignored.
	 */
	@Override
	public void setWidth(int width) {
		return;
	}
	/**
	 * SimpleBrick is not resizable, setHeight will be ignored.
	 */
	@Override
	public void setHeight(int height) {
		return;
	}


}
