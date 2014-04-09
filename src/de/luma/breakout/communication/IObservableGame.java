package de.luma.breakout.communication;


/**
 * Interface for Game Observer.
 * @author mabausch
 *
 */
public interface IObservableGame {

	/**
	 * Add an Observer which listens to Game state changes
	 * @param obs
	 */
	void addObserver(IGameObserver obs);

	/**
	 * Remove an Observer
	 * @param obs
	 */
	void removeObserver(IGameObserver obs);

	/**
	 * performs am updateRepaintPlayGrid for all Observers
	 * which listen to updatRepaint method
	 * needed for GUI 
	 */
	void notifyRepaintPlayGrid();

	/**
	 * performs an updateGameFrame for all Observers
	 * which listen to updateGameFrame method
	 */
	void notifyNextGameFrame();

	/**
	 * notify all Observers which listen that game state has changed
	 */
	void notifyGameStateChanged(GAME_STATE state);

	/**
	 * notify all Observers which listen that the grid size has changed.
	 */
	void notifyOnResize();

	/**
	 * notify all Observers which listen that a Menu has changed.
	 * @param menuItems
	 * @param title
	 */
	void notifyGameMenu(MENU_ITEM[] menuItems, String title);

}