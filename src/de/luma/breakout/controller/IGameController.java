package de.luma.breakout.controller;

import java.awt.Dimension;
import java.io.File;
import java.util.List;

import de.luma.breakout.communication.GAME_STATE;
import de.luma.breakout.communication.IObservableGame;
import de.luma.breakout.communication.MENU_ITEM;
import de.luma.breakout.data.menu.GameMenu;
import de.luma.breakout.data.objects.IBall;
import de.luma.breakout.data.objects.IBrick;

/**
 * Interface for Gamecontroller
 * @author mabausch
 *
 */
public interface IGameController extends IObservableGame {
	
	/**
	 * Input options while the game is running.
	 */
	enum PLAYER_INPUT {
		LEFT,
		RIGHT,
		PAUSE
	}
	
	/* #######################################  USER INPUT HANDLING #######################################   */
	/* ###############################          menus & key events      ##############################  */

	/**
	 *  Initialize the game. Has to be called only one time when the game starts running 
	 */
	void initialize();
	
	/**
	 * Calculates the next Game Frame.
	 *  (only specified for testing)
	 */
	void updateGame();
	
	/**
	 * Process interactive user input during the running game (e.g. from key hits)
	 */
	void processGameInput(PLAYER_INPUT input);
	
	/**
	 * Process the given menu input. 
	 * It is not checked whether the given menu item 
	 * is valid at the current game status.
	 * @param indexOfMenuItem one of the menu items that were 
	 * proposed by notifyGameMenu().
	 */
	void processMenuInput(MENU_ITEM indexOfMenuItem);
	
	/**
	 * Returns true if controller is in level editing mode.
	 */
	boolean getCreativeMode();
	
	/**
	 * Return current game state.
	 */
	GAME_STATE getState();
	

	/* #######################################  LEVEL HANDLING #######################################   */
	
	/**
	 * Loads level with specified location.
	 */
	boolean loadLevel(File f);
	
	
	/**
	 * Save level with default filename and location.
	 * Returns the filepath of the saved level or null in case of failure.
	 */
	String saveLevel();
	
	/**
	 * Save level to the specified location.
	 */
	boolean saveLevel(File f);
	
	/**
	 * Get a list of file paths of available levels.
	 * @return
	 */
	List<String> getLevelList();
	

	/* #######################################  GRID ACCESS HANDLING #######################################   */
	/* ############################   the same procedure as every year...    ###########################  */
	
	/**
	 * Return a list with an instance of every known brick type.
	 * @return
	 */
	List<IBrick> getBrickClasses();
	
	/**
	 * Return the GameMenu.
	 * @return title and Items
	 */
	GameMenu getGameMenu();
	
	/**
	 * Get a list of all balls.
	 */
	List<IBall> getBalls();

	/**
	 * Get a list of all bricks.
	 */
	List<IBrick> getBricks();

	/**
	 * Get the slider.
	 */
	IBrick getSlider();

	/**
	 * Set the slider.
	 */
	void setSlider(IBrick slider);

	/**
	 * Add a brick to the grid.
	 */
	void addBrick(IBrick brick);
	
	/**
	 * Add a ball to the grid.
	 */
	void addBall(IBall ball);
	
	
	/**
	 * Change play grid size.
	 */
	void setGridSize(int width, int height);
	
	/**
	 * Returns size of play grid.
	 */
	Dimension getGridSize();
	
	/** 
	 * Deletes all Objects on Grid.
	 */
	void clearGrid();
	
	
	
	
	
}
