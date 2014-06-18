package de.luma.breakout.communication.messages;

public class LoadLevelMessage {
	
	private String filePath;

	public LoadLevelMessage(String filePath) {
		super();
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
