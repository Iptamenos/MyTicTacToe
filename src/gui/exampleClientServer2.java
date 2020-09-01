package gui;

import ai.Constants;
import ai.GameMode;
import ai.GameParameters;

public class exampleClientServer2 {

	static int clientServerSymbol = Constants.O;
	static int serverPort = 4001;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4000;
	
	public exampleClientServer2() {
		
	}

	public static void main(String[] args) {
		GameParameters.guiStyle = Constants.SystemStyle;
		GameParameters.gameMode = GameMode.CLIENT_SERVER;
		GameParameters.maxDepth1 = Constants.BEST_RESPONSE;
		GameParameters.maxDepth2 = Constants.BEST_RESPONSE;
		GameParameters.player1Color = Constants.BLUE;
		GameParameters.player2Color = Constants.RED;
		GameParameters.clientServerSymbol = clientServerSymbol;
		GameParameters.serverPort = serverPort;
		GameParameters.clientIP = clientIP;
		GameParameters.clientPort = clientPort;
		
		@SuppressWarnings("unused")
		GUI gui = new GUI("My TicTacToe ClientServer 2");

		GUI.createClientServerNewGame();
	}

}
