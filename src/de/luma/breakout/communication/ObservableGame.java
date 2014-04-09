package de.luma.breakout.communication;

import java.util.LinkedList;
import java.util.List;

/**
 * Observer for GUI information interaction with game Controller.
 * @author mabausch
 *
 */
public class ObservableGame implements IObservableGame {
		
	private List<IGameObserver> observerList = new LinkedList<IGameObserver>();
	
	/** */
	public ObservableGame() {
		super();
	}

	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#addObserver(de.luma.breakout.communication.IGameObserver)
	 */
	@Override
	public void addObserver(IGameObserver obs) {
		observerList.add(obs);
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#removeObserver(de.luma.breakout.communication.IGameObserver)
	 */
	@Override
	public void removeObserver(IGameObserver obs) {
		observerList.remove(obs);
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#notifyRepaintPlayGrid()
	 */
	@Override
	public void notifyRepaintPlayGrid() {		
		for (IGameObserver obs : observerList) {
			obs.updateRepaintPlayGrid();
		}		
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#notifyNextGameFrame()
	 */
	@Override
	public void notifyNextGameFrame() {
		for (IGameObserver obs : observerList) {
			obs.updateGameFrame();
		}		
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#notifyGameStateChanged(de.luma.breakout.communication.ObservableGame.GAME_STATE)
	 */
	@Override
	public void notifyGameStateChanged(GAME_STATE state) {
		for (IGameObserver obs : observerList) {
			obs.updateGameState(state);
		}	
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#notifyOnResize()
	 */	
	@Override
	public void notifyOnResize() {
		for (IGameObserver obs : observerList) {
			obs.updateOnResize();
		}	
	}
	
	/* (non-Javadoc)
	 * @see de.luma.breakout.communication.IObservableGame#notifyGameMenu(de.luma.breakout.communication.ObservableGame.MENU_ITEM[], java.lang.String)
	 */
	@Override
	public void notifyGameMenu(MENU_ITEM[] menuItems, String title) {
		for (IGameObserver obs : observerList) {
			obs.updateGameMenu(menuItems, title);
		}	
	}
}


