package de.luma.breakout.communication;


/**
 * This class have to be implemented by Displayclasses which want to be
 * notified by Gamecontroller for changes in Game
 */
public interface IGameObserver {
	
	/**
	 * process a repaint grid notification.
	 */
	void updateRepaintPlayGrid();
	
	/**
	 * process a game state changed notification.
	 * @param Game state
	 */
	void updateGameState(GAME_STATE state);
	
	/**
	 * process a show menu notification.
	 * @param menuitems
	 * @param title
	 */
	void updateGameMenu(MENU_ITEM[] menuItems, String title);
	
	/**
	 * process a next game frame notification.
	 */
	void updateGameFrame();
	
	/**
	 * process a grid resized notification.
	 */
	void updateOnResize();
	
}
