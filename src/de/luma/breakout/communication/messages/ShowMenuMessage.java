package de.luma.breakout.communication.messages;

import de.luma.breakout.communication.MENU_ITEM;

public class ShowMenuMessage implements IActorMessage {
	private MENU_ITEM[] menuItems;
	
	public ShowMenuMessage(MENU_ITEM[] menuItems, String title) {
		super();
		this.menuItems = menuItems;
		this.title = title;
	}
	private String title;
	
	public MENU_ITEM[] getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(MENU_ITEM[] menuItems) {
		this.menuItems = menuItems;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
