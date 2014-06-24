package de.luma.breakout.communication.messages;

import java.util.Arrays;

import de.luma.breakout.communication.MENU_ITEM;

public class ShowMenuMessage implements IActorMessage {
	private final MENU_ITEM[] menuItems;
	private String title;
	
	public ShowMenuMessage(final MENU_ITEM[] menuItems, final String title) {
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
