package client_server;

import ai.Constants;
import ai.GameParameters;
import gui.GUI;

public class exampleClientServer2 {

	GUI gui;
	static int clientServerSymbol = Constants.O;
	static int serverPort = 4001;
	static String clientIP = "127.0.0.1";
	static int clientPort = 4000;
	
	public exampleClientServer2() {
		
	}

	public static void main(String[] args) {
		GUI.game_params = new 
			GameParameters(Constants.NimbusStyle, 3, 3, Constants.BLUE, Constants.RED, Constants.ClientServer,
							clientServerSymbol, serverPort, clientIP, clientPort);
		GUI gui = new GUI("My TicTacToe 2");
		gui.clientServerSymbol = clientServerSymbol;
		gui.serverPort = serverPort;
		gui.clientIP = clientIP;
		gui.clientPort = clientPort;
		
		gui.createClientServerNewGame();
	}

}
