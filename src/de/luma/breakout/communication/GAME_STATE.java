package de.luma.breakout.communication;

/**
 * All game states.
 */
public enum GAME_STATE {
	/** Game is running or in level editor mode */
	RUNNING,
	/** Display pause menu */
	PAUSED,		
	/** Display level selection menu */
	MENU_LEVEL_SEL,
	/** Display game over menu */
	MENU_GAMEOVER,
	/** Display game won menu */
	MENU_WINGAME,
	/** Display main menu */
	MENU_MAIN,
	/** Terminate game */
	KILLED
}
