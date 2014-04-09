package de.luma.breakout.communication;


/**
 * Class to map text to id and menuitem.
 *   TODO unicode in other language.
 * @author mabausch
 *
 */
public final class TextMapping {
	
	// prevent TextMapping from instantiation
	private TextMapping() {}

	/** */
	public static final int TXT_YOU_LOSE = 1;
	/** */
	public static final int TXT_YOU_WIN = 2;
	/** */
	public static final int TXT_MAIN_MENU = 3;
	/** */
	public static final int TXT_NEW_GAME = 4;
	/** */
	public static final int TXT_CONTINUE = 5;
	/** */
	public static final int TXT_LEVEL_CHOOSE = 6;
	/** */
	public static final int TXT_NEXT_LEVEL = 7;
	/** */
	public static final int TXT_LEVEL_EDITOR = 8;
	/** */
	public static final int TXT_END = 9;
	/** */
	public static final int TXT_GAME_PAUSED = 10;
	/** */
	public static final int TXT_BACK_TO_MAIN_MENU = 11;
	
	/**
	 * This mthod returns a String for Txt index
	 * @param indexOfTxt
	 * @return
	 */
	public static String getTextForIndex(int indexOfTxt) {
		switch (indexOfTxt) {
		case TXT_YOU_LOSE:
			return "Sie haben verloren!";
		case TXT_YOU_WIN:
			return "Sie haben gewonnen!";
		case TXT_MAIN_MENU:
			return "Hauptmenü";
		case TXT_NEW_GAME:
			return "Neues Spiel";
		case TXT_CONTINUE:
			return "Weiterspielen";
		case TXT_NEXT_LEVEL:
			return "Next Level";
		case TXT_LEVEL_CHOOSE:
			return "Level wählen";
		case TXT_LEVEL_EDITOR:
			return "Level Editor";
		case TXT_END:
			return "Spiel beenden";
		case TXT_GAME_PAUSED:
			return "Spiel angehalten";
		case TXT_BACK_TO_MAIN_MENU:
			return "Zurück zum Hauptmenü";
		default:
			return "?";			
		}
	}
	
	/**
	 * This method returns a String for Menu Item index
	 * @param mnuEnum
	 * @return
	 */
	public static String getTextForMenuEnum(MENU_ITEM mnuEnum) {
		switch (mnuEnum) {
		case MNU_NEW_GAME:
			return getTextForIndex(TXT_NEW_GAME);
		case MNU_END:
			return getTextForIndex(TXT_END);
		case MNU_CONTINUE:
			return getTextForIndex(TXT_CONTINUE);
		case MNU_BACK_MAIN_MENU:
			return getTextForIndex(TXT_BACK_TO_MAIN_MENU);
		case MNU_NEXT_LEVEL:
			return getTextForIndex(TXT_NEXT_LEVEL);
		case MNU_LEVEL_CHOOSE:
			return getTextForIndex(TXT_LEVEL_CHOOSE);
		case MNU_LEVEL_EDITOR:
			return getTextForIndex(TXT_LEVEL_EDITOR);
		default:
			return "?";
		}
	}
	
}
