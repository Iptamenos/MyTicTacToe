package gui;

import ai.Constants;
import ai.GameParameters;
import enumerations.Color;
import enumerations.GameMode;
import enumerations.GuiStyle;

public class exampleClientServer2 {

	static int clientServerSymbol = Constants.O;
	static int serverPort = 4001;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4000;
	
	public exampleClientServer2() {
		
	}

	public static void main(String[] args) {
		GameParameters.guiStyle = GuiStyle.SYSTEM_STYLE;
		GameParameters.gameMode = GameMode.CLIENT_SERVER;
		GameParameters.maxDepth1 = Constants.BEST_RESPONSE;
		GameParameters.maxDepth2 = Constants.BEST_RESPONSE;
		GameParameters.player1Color = Color.BLUE;
		GameParameters.player2Color = Color.RED;
		GameParameters.clientServerSymbol = clientServerSymbol;
		GameParameters.serverPort = serverPort;
		GameParameters.clientIP = clientIP;
		GameParameters.clientPort = clientPort;
		
		@SuppressWarnings("unused")
		GUI gui = new GUI("My TicTacToe ClientServer 2");

		GUI.createClientServerNewGame();
	}

}
