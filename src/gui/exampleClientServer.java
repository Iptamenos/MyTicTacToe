package gui;

import enumeration.Color;
import enumeration.GameMode;
import enumeration.GuiStyle;
import utility.Constants;
import utility.GameParameters;

public class exampleClientServer {

	static int clientServerSymbol = Constants.X;
	static int serverPort = 4000;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4001;
	
	public exampleClientServer() {
		
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
		TicTacToeGUI gui = new TicTacToeGUI("My TicTacToe ClientServer 1");

		TicTacToeGUI.createClientServerNewGame();
	}

}
