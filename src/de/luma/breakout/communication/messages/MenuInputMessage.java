package de.luma.breakout.communication.messages;

import de.luma.breakout.communication.MENU_ITEM;

public class MenuInputMessage implements IActorMessage {
	
	private MENU_ITEM indexOfMenuItem;

	public MenuInputMessage(MENU_ITEM indexOfMenuItem) {
		super();
		this.indexOfMenuItem = indexOfMenuItem;
	}

	public MENU_ITEM getIndexOfMenuItem() {
		return indexOfMenuItem;
	}

	public void setIndexOfMenuItem(MENU_ITEM indexOfMenuItem) {
		this.indexOfMenuItem = indexOfMenuItem;
	}
}
