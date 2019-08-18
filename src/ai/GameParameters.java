package ai;

import java.io.Serializable;

public class GameParameters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -302611744838922290L;
	
	private int guiStyle;
	private int gameMode;
	private int maxDepth1;
	private int maxDepth2;
	private int player1Color;
	private int player2Color;
	private int clientServerSymbol;
	private int serverPort;
	private String clientIP;
	private int clientPort;
	
	// Default values
	public GameParameters() {
		this.guiStyle = Constants.SystemStyle;
		this.maxDepth1 = Constants.BestResponse;
		this.maxDepth2 = Constants.BestResponse;
		this.player1Color = Constants.BLUE;
		this.player2Color = Constants.RED;
		this.gameMode = Constants.HumanVsAi;
		this.clientServerSymbol = Constants.X;
		this.serverPort = 4000;
		this.clientIP = "127.0.0.1";
		this.clientPort = 4001;
	}
	
	// Given values
	public GameParameters(int guiStyle, int maxDepth1, int maxDepth2, 
			int player1Color, int player2Color, int gameMode) {
		this.guiStyle = guiStyle;
		this.maxDepth1 = maxDepth1;
		this.maxDepth2 = maxDepth2;
		this.player1Color = player1Color;
		this.player2Color = player2Color;
		this.gameMode = gameMode;
		this.clientServerSymbol = Constants.X;
		this.serverPort = 4000;
		this.clientIP = "127.0.0.1";
		this.clientPort = 4001;
	}
	
	// Given values
	public GameParameters(int guiStyle, int maxDepth1, int maxDepth2, 
			int player1Color, int player2Color, int gameMode,
			int clientServerSymbol, int serverPort, String clientIP, int clientPort) {
		this.guiStyle = guiStyle;
		this.maxDepth1 = maxDepth1;
		this.maxDepth2 = maxDepth2;
		this.player1Color = player1Color;
		this.player2Color = player2Color;
		this.gameMode = gameMode;
		this.clientServerSymbol = clientServerSymbol;
		this.serverPort = serverPort;
		this.clientIP = clientIP;
		this.clientPort = clientPort;
	}

	public int getGuiStyle() {
		return guiStyle;
	}

	public void setGuiStyle(int guiStyle) {
		this.guiStyle = guiStyle;
	}

	public int getMaxDepth1() {
		return this.maxDepth1;
	}

	public void setMaxDepth1(int maxDepth1) {
		this.maxDepth1 = maxDepth1;
	}

	public int getMaxDepth2() {
		return this.maxDepth2;
	}

	public void setMaxDepth2(int maxDepth2) {
		this.maxDepth2 = maxDepth2;
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

	public int getClientServerSymbol() {
		return clientServerSymbol;
	}

	public void setClientServerSymbol(int clientServerSymbol) {
		this.clientServerSymbol = clientServerSymbol;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

}
