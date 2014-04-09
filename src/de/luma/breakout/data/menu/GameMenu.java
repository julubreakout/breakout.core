package de.luma.breakout.data.menu;

import de.luma.breakout.communication.MENU_ITEM;

public class GameMenu {

	private MENU_ITEM[] menuItems;
	private String title;

	public GameMenu(MENU_ITEM[] menuItems, String title) {
		super();
		this.menuItems = menuItems;
		this.title = title;
	}	
	
	public MENU_ITEM[] getMenuItems() {
		return menuItems;
	}

	public String getTitle() {
		return title;
	}
}
