package ai;

public class GameParameters {
	
	private int gameMode;
	private int maxDepth;
	private String player1Color;
	private String player2Color;
	
	public GameParameters() {
		// Default values
		this.maxDepth = 3;
		this.player1Color = "BLUE";
		this.player2Color = "RED";
		this.gameMode = Constants.HumanVsAi;
	}

	public int getMaxDepth() {
		return this.maxDepth;
	}

	public void setMaxDepth(int difficulty) {
		this.maxDepth = difficulty;
	}
	
	public String getPlayer1Color() {
		return player1Color;
	}

	public void setPlayer1Color(String player1Color) {
		this.player1Color = player1Color;
	}
	
	public String getPlayer2Color() {
		return player2Color;
	}

	public void setPlayer2Color(String player2Color) {
		this.player2Color = player2Color;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}


}
