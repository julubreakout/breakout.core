package de.luma.breakout.view.tui;

import java.io.InputStream;
import java.util.ConcurrentModificationException;
import java.util.Locale;
import java.util.Scanner;

import org.apache.log4j.Logger;

import de.luma.breakout.communication.GAME_STATE;
import de.luma.breakout.communication.IGameObserver;
import de.luma.breakout.communication.MENU_ITEM;
import de.luma.breakout.communication.PLAYER_INPUT;
import de.luma.breakout.communication.TextMapping;
import de.luma.breakout.controller.IGameController;
import de.luma.breakout.data.objects.IBall;
import de.luma.breakout.data.objects.IBrick;

/**
 * TUI for Game.
 * @author mabausch
 *
 */
public class UITextView implements IGameObserver {

	private class GameInput implements Runnable {

		private static final String CHAR_MOVE_LEFT = "a";
		private static final String CHAR_MOVE_RIGHT = "d";
		private static final String CHAR_PAUSE_GAME = "p";

		/**
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {	

			// exit condition for thread
			while (controller.getState() != GAME_STATE.KILLED && input.hasNextLine()) {

				String strInput = input.nextLine();

				// listen to Game Inputs
				if (controller.getState() == GAME_STATE.RUNNING) {

					if (strInput.equals(CHAR_MOVE_LEFT)) {
						controller.processGameInput(PLAYER_INPUT.LEFT);
					} else if (strInput.equals(CHAR_MOVE_RIGHT)) {
						controller.processGameInput(PLAYER_INPUT.RIGHT);
					} else if (strInput.equals(CHAR_PAUSE_GAME)) {
						controller.processGameInput(PLAYER_INPUT.PAUSE);
					}


					// listen to menu Inputs
				} else {	
					if (strInput.matches("[0-9]+")){
						int in = Integer.valueOf(strInput);

						if (in >= 0 && in < MENU_ITEM.values().length) {
							controller.processMenuInput(MENU_ITEM.values()[in]);						
						}
					}
				}

			}

		}

	}

	private IGameController controller;
	private Scanner input;
	private Thread gameInputThread;	

	/**
	 * Consturctor
	 */
	public UITextView() {
		this(System.in);
	}

	/**
	 * Constructor with inputstream were to write to.
	 * @param inputSource
	 */
	public UITextView(InputStream inputSource) {
		super();
		Locale.setDefault(new Locale("en", "US"));
		input = new Scanner(inputSource);

		startGameDaemon();
	}

	/**
	 * Consturctor
	 * @param inputSource
	 */
	public UITextView(String inputSource) {
		super();
		Locale.setDefault(new Locale("en", "US"));
		input = new Scanner(inputSource);

		startGameDaemon();
	}

	private void startGameDaemon() {
		gameInputThread = new Thread(new GameInput());
		gameInputThread.setDaemon(true);
		gameInputThread.setName("GameInput_Thread");
		gameInputThread.start();
	}

	/**
	 * (non-Javadoc)
	 * @see communication.IGameObserver#updateRepaintPlayGrid()
	 */
	@Override
	public void updateRepaintPlayGrid() {	
		try {
				for (IBrick brick : getController().getBricks()) {
					printMsg("TUI: brick (%d, %d)", brick.getX(), brick.getY());
				}
				IBrick s = getController().getSlider();
				if (s != null) {
					printMsg("TUI: slider (%d, %d)", s.getX(), s.getY());
				}

				for (IBall ball : getController().getBalls()) {
					printMsg("TUI: ball (%.1f, %.1f)  speed: (%.1f, %.1f) \n---", ball.getX(), ball.getY(), ball.getSpeedX(), ball.getSpeedY());
				}	
		} catch (ConcurrentModificationException ex) {
			// fuck
		}
	}

	/**
	 * (non-Javadoc)
	 * @see communication.IGameObserver#updateGameState(communication.ObservableGame.GAME_STATE)
	 */
	@Override
	public void updateGameState(GAME_STATE state) {
		printMsg("TUI: game state changed: " + state.name());
	}

	/** */
	public IGameController getController() {
		return controller;
	}

	/** */
	public void setController(IGameController controller) {
		this.controller = controller;
	}

	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.communication.IGameObserver#updateGameMenu(de.luma.breakout.communication.ObservableGame.MENU_ITEM[], java.lang.String)
	 */
	@Override
	public void updateGameMenu(MENU_ITEM[] menuItems, String title) {		

		printMsg("MENU: -----  " + title + " ----- ");
		for (MENU_ITEM m : menuItems) {
			printMsg("[%d] %s", m.ordinal(), TextMapping.getTextForMenuEnum(m));
		}

	}

	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.communication.IGameObserver#updateGameFrame()
	 */
	@Override
	public void updateGameFrame() {
		// not used yet
	}

	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.communication.IGameObserver#updateOnResize()
	 */
	@Override
	public void updateOnResize() { }

	private Logger getLogger() {
		return Logger.getLogger("de.luma.breakout.view.tui.UITextView");
	}

	private void printMsg(String format, Object... b) {
		getLogger().info(String.format(format, b));
	}

	


}