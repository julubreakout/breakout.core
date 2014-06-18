package de.luma.breakout.communication.messages;

import java.util.List;

import de.luma.breakout.communication.GAME_STATE;

public class UpdateGameStateMessage {

	private GAME_STATE state;
	private int score;
	private List<String> levelList;

	public UpdateGameStateMessage(GAME_STATE state, int score,
			List<String> levelList) {
		super();
		this.state = state;
		this.score = score;
		this.levelList = levelList;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<String> getLevelList() {
		return levelList;
	}

	public void setLevelList(List<String> levelList) {
		this.levelList = levelList;
	}

	public GAME_STATE getState() {
		return state;
	}

	public void setState(GAME_STATE state) {
		this.state = state;
	}

}
