package de.luma.breakout.controller;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import de.luma.breakout.communication.GAME_STATE;
import de.luma.breakout.communication.MENU_ITEM;
import de.luma.breakout.communication.ObservableGame;
import de.luma.breakout.communication.TextMapping;
import de.luma.breakout.data.PlayGrid;
import de.luma.breakout.data.menu.GameMenu;
import de.luma.breakout.data.objects.IBall;
import de.luma.breakout.data.objects.IBrick;
import de.luma.breakout.data.objects.IDecodable;
import de.luma.breakout.data.objects.impl.Slider;

/**
 * Game Controller 
 * @author mabausch
 *
 */
public class GameController extends ObservableGame implements IGameController {	

	/**
	 * This task gets scheduled by start() to make the
	 * game run with a constant FPS.
	 */
	private class GameTimerTask extends TimerTask {		
		/** */
		public GameTimerTask() {
			super();
		}		

		/**
		 * (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			updateGame();			
		}		
	}	
	private String appPath; // base directory of application 
	private PlayGrid grid;	
	private Timer timer;
	private GameTimerTask task;
	private GameMenu menu;
	private GAME_STATE state;
	private boolean isInCreativeMode;
	private int levelIndex;	
	
	private static final String LEVEL_PATH = "levels/";
	private static final int FRAME_DELAY = 10;
	private static final int DEFAULT_GRID_WIDTH = 500;
	private static final int DEFAULT_GRID_HEIGHT = 500;
	private static final int DEFAULT_SLIDER_STEP = 5;
	
	/**
	 * Default Constructor
	 */
	public GameController(String appPath) {
		super();		
		this.appPath = appPath;
	}


	/* #######################################  GAME INFRASTRUCTURE #######################################   */
	/* ###############################    Basics to make the game a game     ##############################  */
	
	/**
	 *  Initialize the game. Has to be called only one time when the game starts running 
	 */
	@Override
	public void initialize() {		
		showMainMenu();
	}
	
	/**
	 * Prepares the next frame of the game:
	 * - Move balls and do collision tests
	 * - Check game rules (game over etc.)
	 * - Request repaint
	 */
	@Override
	public void updateGame() {
		// move game objects only when not in creative mode
		if (!this.isInCreativeMode) {
			
			moveBalls();				
			
			// Check if no ball on game grid
			if (getGrid().getBalls().isEmpty()) {
				gameOver();
			}

			// check if no more bricks left
			if (getGrid().getBricks().isEmpty()) {
				winGame();
			}
		}
			
		// notify bricks of new frame (e.g. for moving bricks)
		for (IBrick brick : getGrid().getBricks()) {
			brick.onNextFrame();
		}
		
		notifyNextGameFrame();
		notifyRepaintPlayGrid();
		
		
	}
	
	/**
	 * Moves all balls, regarding collisions with bricks, the grid borders and the slider.
	 * Balls and bricks get removed by this method when the grid or a brick signals to do so.
	 */
	private void moveBalls() {
		Iterator<IBrick> itbrick;
		Iterator<IBall> itball;
		IBrick currentBrick; 
		IBall currentBall;

		itball = getGrid().getBalls().iterator();
		while (itball.hasNext()){ 
			currentBall = itball.next();

			// check for collisions with bricks (and change direction)
			itbrick = getGrid().getBricks().iterator();
			while (itbrick.hasNext()) {
				currentBrick = itbrick.next();
				if (currentBrick.tryCollision(currentBall)) {
					itbrick.remove();
				}
			}

			// check for collisions with grid borders (and change direction)
			if (getGrid().tryCollision(currentBall)) {
				itball.remove();
			}			

			// check for collisions with slider (and change direction)
			IBrick s = getGrid().getSlider();
			if (s != null) {
				s.tryCollision(currentBall);
			}

			// move balls
			currentBall.setX(currentBall.getX() + currentBall.getSpeedX());
			currentBall.setY(currentBall.getY() + currentBall.getSpeedY());
		}


	}

	/**
	 * Initialize a new game timer to automatically calculate the
	 * next game frame.
	 * @return
	 */
	protected Timer resetTimer() {
		task = new GameTimerTask();		
		timer = new Timer("Game Timer");
		return timer;
	}

	/**
	 * Stop the game timer. No more frames will be calculated automatically.
	 */
	protected void cancelTimer()  {
		if (timer != null) {
			timer.cancel();
		}
	}
	
	/**
	 * Start or resume the game.
	 */
	private void start() {
		// timer 
		resetTimer();
		timer.scheduleAtFixedRate(task, 0, FRAME_DELAY);
		setState(GAME_STATE.RUNNING);

	}
	

	/**
	 * Pause the game and display the pause menu.
	 */
	private void pause() {
		cancelTimer();
		setState(GAME_STATE.PAUSED);
	
		notifyGameMenu(new MENU_ITEM[] {MENU_ITEM.MNU_NEW_GAME, MENU_ITEM.MNU_CONTINUE, MENU_ITEM.MNU_BACK_MAIN_MENU, MENU_ITEM.MNU_END},  
				TextMapping.getTextForIndex(TextMapping.TXT_GAME_PAUSED));
	}

	/**
	 * Stop the game and display the game over menu.
	 */
	private void gameOver() {
		cancelTimer();

		setState(GAME_STATE.MENU_GAMEOVER);
		notifyGameMenu(new MENU_ITEM[]{MENU_ITEM.MNU_NEW_GAME, MENU_ITEM.MNU_BACK_MAIN_MENU, MENU_ITEM.MNU_END},
				TextMapping.getTextForIndex(TextMapping.TXT_YOU_LOSE));		
	}

	/**
	 * Stop the game and set game state to 'killed'.
	 */
	private void terminate() {
		cancelTimer();

		setState(GAME_STATE.KILLED);		
	}

	/**
	 * Stop the game and display the game won menu.
	 */
	private void winGame() {
		cancelTimer();
		setState(GAME_STATE.MENU_WINGAME);
		notifyGameMenu(new MENU_ITEM[]{MENU_ITEM.MNU_NEXT_LEVEL, MENU_ITEM.MNU_NEW_GAME, MENU_ITEM.MNU_BACK_MAIN_MENU, MENU_ITEM.MNU_END}, 
				TextMapping.getTextForIndex(TextMapping.TXT_YOU_WIN));		
	}


	/* #######################################  USER INPUT HANDLING #######################################   */
	/* ###############################          menus & key events      ##############################  */

	/**
	 * Process interactive user input during the running game (e.g. from key hits)
	 */
	@Override
	public void processGameInput(PLAYER_INPUT input) {
		switch (input) {
		case LEFT:
			moveSlider(-DEFAULT_SLIDER_STEP);
			break;
		case RIGHT:
			moveSlider(+DEFAULT_SLIDER_STEP);
			break;
		case PAUSE:
			if (getState() == GAME_STATE.RUNNING) {
				pause();
			}
		}
	}
	
	/**
	 * Control slider movements since the slider has no information about the grid.
	 * @param delta Positive or negative value to move slider.
	 */
	private void moveSlider(int delta) {
		int newx = getGrid().getSlider().getX() + delta;
		if (newx < 0) {
			return;
		} else if (newx > getGrid().getWidth() - getGrid().getSlider().getWidth()) {
			return;
		} else {
			getGrid().getSlider().setX(newx);
		}
	}
	
	/**
	 * Display the main game menu.
	 */
	private void showMainMenu() {
		setState(GAME_STATE.MENU_MAIN);
		// with editor
//		notifyGameMenu(new MENU_ITEM[]{MENU_ITEM.MNU_NEW_GAME, MENU_ITEM.MNU_LEVEL_CHOOSE, MENU_ITEM.MNU_LEVEL_EDITOR, MENU_ITEM.MNU_END},
//				TextMapping.getTextForIndex(TextMapping.TXT_MAIN_MENU));
		
		notifyGameMenu(new MENU_ITEM[]{MENU_ITEM.MNU_NEW_GAME, MENU_ITEM.MNU_LEVEL_CHOOSE, MENU_ITEM.MNU_END},
				TextMapping.getTextForIndex(TextMapping.TXT_MAIN_MENU));
	}
	
	/**
	 * Process the given menu input. 
	 * It is not checked whether the given menu item 
	 * is valid at the current game status.
	 * @param indexOfMenuItem one of the menu items that were 
	 * proposed by notifyGameMenu().
	 */
	@Override
	public void processMenuInput(MENU_ITEM indexOfMenuItem) {
		if (indexOfMenuItem == null) {
			indexOfMenuItem = MENU_ITEM.MNU_BACK_MAIN_MENU;
		}
		switch (indexOfMenuItem) {
		case MNU_NEW_GAME:
			this.setCreativeMode(false);	
			levelIndex = 0;
			loadLevel(new File(getLevelList().get(levelIndex)));
			this.start();
			break;
		case MNU_END:
			// TODO save level and gameprocess
			terminate();	
			break;
		case MNU_CONTINUE:			
			start();
			break;
		case MNU_BACK_MAIN_MENU:			
			if (getState() != GAME_STATE.RUNNING) {
				this.setCreativeMode(false);
				showMainMenu();
			}
			break;
		case MNU_LEVEL_CHOOSE:
			cancelTimer();
			this.setState(GAME_STATE.MENU_LEVEL_SEL);			
			break;
		case MNU_LEVEL_EDITOR: 
			this.setCreativeMode(true);
			clearGrid();
			setGridSize(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);			
			this.start();			
			break;
		case MNU_NEXT_LEVEL:
			
			this.setCreativeMode(false);	
			// last level
			levelIndex++;
			if (levelIndex == getLevelList().size()) {
				break;
			}
			
			loadLevel(new File(getLevelList().get(levelIndex)));
			this.start();
			break;
		default:
			break;
		}		
	}
	
	/**
	 * this method stores actual menu Items and Title
	 * 
	 */
	@Override
	public void notifyGameMenu(MENU_ITEM[] menuItems, String title) {
		this.menu = new GameMenu(menuItems, title);
		
		super.notifyGameMenu(menuItems, title);
	}
	
	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getState()
	 */
	@Override
	public GAME_STATE getState() {
		return state;
	}
	
	
	private void setState(GAME_STATE state) {
		this.state = state;
		notifyGameStateChanged(state);
	}

	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getCreativeMode()
	 */
	@Override
	public boolean getCreativeMode() {
		return isInCreativeMode;
	}
	
	/**
	 * Enable creative mode to display the running game without
	 * moving the balls.
	 */
	private void setCreativeMode(boolean enableCreativeMode) {
		this.isInCreativeMode = enableCreativeMode;
	}
	
	/* #######################################  LEVEL HANDLING #######################################   */
	
	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#saveLevel()
	 */
	@Override
	public String saveLevel() {		
		String filepath = LEVEL_PATH + "userLevel" + System.nanoTime() + ".lvl";
		if (saveLevel(new File(filepath))) {
			return filepath;
		} else {
			return null;
		}
	}	
	
	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#saveLevel(java.io.File)
	 */
	@Override
	public boolean saveLevel(File f)  {
		PrintWriter out = null;
		try {
			Locale.setDefault(new Locale("en", "US"));			
			OutputStreamWriter w;			
			w = new OutputStreamWriter(new FileOutputStream(f));
			
			out = new PrintWriter(new BufferedWriter(w));
			
			// save Grid Properties
			out.println(getGrid().encode());
				
			// save bricks
			for (IBrick brick : getGrid().getBricks()) {
				out.print(brick.getClass().getName());
				out.print(':');
				out.println(brick.encode());
			}
			
			// save balls
			for (IBall b : getGrid().getBalls()) {
				out.print(b.getClass().getName());
				out.print(':');
				out.println(b.encode());
			}
			
			// save slider - last object, no newline at the end!
			out.print(getGrid().getSlider().getClass().getName());
			out.print(':');
			out.print(getGrid().getSlider().encode());
			
			return true;
			
		} catch (FileNotFoundException e) {					
			return false;
		} finally {
			if (out != null) {
				out.close();
			}
		}		
	}
	

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#loadLevel(java.io.File)
	 */
	@Override
	public boolean loadLevel(File f) {
		Scanner s = null;
		try {
			Locale.setDefault(new Locale("en", "US"));
			s = new Scanner(f);
			
			clearGrid();
					
			// decode Grid Properties
			String line = s.nextLine();			
			this.getGrid().decode(line);
			
			while (s.hasNextLine()) {
				line = s.nextLine();
				
				String className = line.substring(0, line.indexOf(':'));				
				Class<?> classObj = this.getClass().getClassLoader().loadClass(className);
				
				IDecodable obj = (IDecodable) classObj.newInstance();
				obj.decode(line.substring(className.length()+1));			
								
				if (obj instanceof IBall) {
					getGrid().getBalls().add((IBall) obj);
				} else if (obj instanceof Slider) {
					getGrid().setSlider((Slider) obj);
				} else if (obj instanceof IBrick) {
					getGrid().getBricks().add((IBrick) obj);
				}
			}
			
			// for full coverage
			setGridSize(getGrid().getWidth(), getGrid().getHeight());
			notifyOnResize();			
			
		} catch(Exception e) {			
			return false;
		} finally {
			// in case of exception
			if (s != null) { 
				s.close();
			}
		} 
		return true;
	}
	
	
	/**
	 * Get a list of file path of available levels.
	 *  add a offset if your not working localy
	 * @param offset
	 * @return
	 */
	public List<String> getLevelList() {
		File f = new File(appPath + LEVEL_PATH);
		System.out.println("load levels from: " + f.getAbsolutePath());
		List<String> retVal = new ArrayList<String>();
		
		for (String s : f.list()) {
			System.out.println("found level:" + s);
			if (s.endsWith(".lvl")) {
				retVal.add(f.getPath() + "/" + s);
			}
		}
		return retVal;
	}
	
	/* #######################################  GRID ACCESS HANDLING #######################################   */
	/* ############################   the same procedure as every year...    ###########################  */

	private PlayGrid getGrid() {
		if (grid == null) {
			grid = new PlayGrid(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);
		}
		return grid;
	}

	/**
	 * set the Size of the grid and
	 * resize Slider position.
	 * calls notifyOnReszie()
	 * @param width
	 * @param height
	 */
	public void setGridSize(int width, int height) {
		// set grid size
		getGrid().setWidth(width);
		getGrid().setHeight(height);
		
		// fit slider position to new size
		if (getSlider() != null) {
			getSlider().setY(height - getSlider().getHeight() -DEFAULT_SLIDER_STEP);
			getSlider().setX(0);
		}
		
		notifyOnResize();
	}
	
	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getGridSize()
	 */
	public Dimension getGridSize() {
		return new Dimension(getGrid().getWidth(), getGrid().getHeight());
	}

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getBrickClasses()
	 */
	public List<IBrick> getBrickClasses() {
		return getGrid().getBrickClasses();
	}
	
	/**
	 * (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getGameMenu()
	 */
	public GameMenu getGameMenu() {
		return menu;
	}
	
	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getBalls()
	 */
	public List<IBall> getBalls() {	
		return getGrid().getBalls();
	}

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#addBall(de.luma.breakout.data.objects.Ball)
	 */
	public void addBall(IBall ball) {
		getGrid().addBall(ball);		
	}

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getBricks()
	 */
	public List<IBrick> getBricks() {
		return getGrid().getBricks();
	}

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#addBrick(de.luma.breakout.data.objects.AbstractBrick)
	 */
	public void addBrick(IBrick brick) {
		getGrid().addBrick(brick);
	}

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#getSlider()
	 */
	public IBrick getSlider() {
		return getGrid().getSlider();
	}

	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#setSlider(de.luma.breakout.data.objects.Slider)
	 */
	public void setSlider(IBrick slider) {
		getGrid().setSlider(slider);
	}
	
	
	/** (non-Javadoc)
	 * @see de.luma.breakout.controller.IGameController#clearGrid()
	 */
	@Override
	public void clearGrid() {
		getGrid().clearGrid();	
		
	}

}