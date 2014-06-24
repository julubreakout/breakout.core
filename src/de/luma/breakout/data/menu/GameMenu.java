package de.luma.breakout.data.menu;

import java.util.Arrays;
import de.luma.breakout.communication.MENU_ITEM;

public class GameMenu {

	private final MENU_ITEM[] menuItems;
	private final String title;

	public GameMenu(final MENU_ITEM[] menuItems, final String title) {
		super();
		this.menuItems = Arrays.copyOf(menuItems, menuItems.length);
		this.title = title;
	}	
	
	public MENU_ITEM[] getMenuItems() {
		return menuItems;
	}

	public String getTitle() {
		return title;
	}
}
