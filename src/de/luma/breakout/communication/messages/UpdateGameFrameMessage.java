package de.luma.breakout.communication.messages;

import java.awt.Dimension;
import java.util.List;

import de.luma.breakout.communication.GAME_STATE;
import de.luma.breakout.data.objects.IBall;
import de.luma.breakout.data.objects.IBrick;

public class UpdateGameFrameMessage {
	
	private GAME_STATE gameState;
	private List<String> levelList;
	private int score;
	private Dimension gridSize;
	private List<IBall> balls;
	private List<IBrick> bricks;
	private IBrick slider;

	
	public UpdateGameFrameMessage(GAME_STATE gameState, List<String> levelList,
			int score, Dimension gridSize, List<IBall> balls,
			List<IBrick> bricks, IBrick slider) {
		super();
		this.gameState = gameState;
		this.levelList = levelList;
		this.score = score;
		this.gridSize = gridSize;
		this.balls = balls;
		this.bricks = bricks;
		this.slider = slider;
	}
	
	public GAME_STATE getGameState() {
		return gameState;
	}
	public void setGameState(GAME_STATE gameState) {
		this.gameState = gameState;
	}
	public List<String> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<String> levelList) {
		this.levelList = levelList;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Dimension getGridSize() {
		return gridSize;
	}
	public void setGridSize(Dimension gridSize) {
		this.gridSize = gridSize;
	}
	public List<IBall> getBalls() {
		return balls;
	}
	public void setBalls(List<IBall> balls) {
		this.balls = balls;
	}
	public List<IBrick> getBricks() {
		return bricks;
	}
	public void setBricks(List<IBrick> bricks) {
		this.bricks = bricks;
	}
	public IBrick getSlider() {
		return slider;
	}
	public void setSlider(IBrick slider) {
		this.slider = slider;
	}


}
