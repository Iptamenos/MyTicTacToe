package gui;

import ai.Constants;
import ai.GameParameters;

public class exampleClientServer {

	GUI gui;
	static int clientServerSymbol = Constants.X;
	static int serverPort = 4000;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4001;
	
	public exampleClientServer() {
		
	}

	public static void main(String[] args) {
		GameParameters.guiStyle = Constants.SystemStyle;
		GameParameters.gameMode = Constants.ClientServer;
		GameParameters.maxDepth1 = Constants.BestResponse;
		GameParameters.maxDepth2 = Constants.BestResponse;
		GameParameters.player1Color = Constants.BLUE;
		GameParameters.player2Color = Constants.RED;
		GameParameters.clientServerSymbol = clientServerSymbol;
		GameParameters.serverPort = serverPort;
		GameParameters.clientIP = clientIP;
		GameParameters.clientPort = clientPort;

		GUI gui = new GUI("My TicTacToe");

		gui.createClientServerNewGame();
	}

}
