package client_server;

import ai.Constants;
import ai.GameParameters;
import gui.GUI;

public class exampleClientServer {

	GUI gui;
	static int clientServerSymbol = Constants.X;
	static int serverPort = 4000;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4001;
	
	public exampleClientServer() {
		
	}

	public static void main(String[] args) {
		GUI.game_params = new 
			GameParameters(Constants.SystemStyle, 3, 3, Constants.BLUE, Constants.RED, Constants.ClientServer, 
							clientServerSymbol, serverPort, clientIP, clientPort);
		GUI gui = new GUI("My TicTacToe");
		gui.clientServerSymbol = clientServerSymbol;
		gui.serverPort = serverPort;
		gui.clientIP = clientIP;
		gui.clientPort = clientPort;

		gui.createClientServerNewGame();
	}

}
