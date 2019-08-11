package ai;

public class GameParameters {
	
	private int gameMode;
	private int maxDepth;
	private int player1Color;
	private int player2Color;
	
	// Default values
	public GameParameters() {
		this.maxDepth = 3;
		this.player1Color = Constants.BLUE;
		this.player2Color = Constants.RED;
		this.gameMode = Constants.HumanVsAi;
	}
	
	// Given values
	public GameParameters(int maxDepth, int player1Color, int player2Color, int gameMode) {
		this.maxDepth = maxDepth;
		this.player1Color = player1Color;
		this.player2Color = player2Color;
		this.gameMode = gameMode;
	}
	
	public int getMaxDepth() {
		return this.maxDepth;
	}

	public void setMaxDepth(int difficulty) {
		this.maxDepth = difficulty;
	}
	
	public int getPlayer1Color() {
		return player1Color;
	}

	public void setPlayer1Color(int player1Color) {
		this.player1Color = player1Color;
	}
	
	public int getPlayer2Color() {
		return player2Color;
	}

	public void setPlayer2Color(int player2Color) {
		this.player2Color = player2Color;
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}


}
