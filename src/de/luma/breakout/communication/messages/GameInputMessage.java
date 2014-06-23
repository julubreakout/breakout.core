package de.luma.breakout.communication.messages;

import de.luma.breakout.controller.IGameController.PLAYER_INPUT;

public class GameInputMessage implements IActorMessage {

	private PLAYER_INPUT input;

	public GameInputMessage(PLAYER_INPUT input) {
		super();
		this.input = input;
	}

	public PLAYER_INPUT getInput() {
		return input;
	}

	public void setInput(PLAYER_INPUT input) {
		this.input = input;
	}
	
	
}
