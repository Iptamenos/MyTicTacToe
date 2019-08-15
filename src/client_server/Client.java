package client_server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import ai.Move;
import gui.GUI;

public class Client extends Thread {
	
	GUI gui;
	String serverIP;
	int serverPort;
	int playerSymbol;
	
	public Client() {
			
	}
	
	public Client(GUI gui, String IP, int port, int playerSymbol) {
		this.gui = gui;
		this.serverIP = IP;
		this.serverPort = port;
		this.playerSymbol = playerSymbol;
	}
	
	@Override
	public void run() {
		
		Socket requestSocket = null;
		ObjectOutputStream out = null;
		
		try {
			requestSocket = new Socket(InetAddress.getByName(serverIP), serverPort);
			System.out.println("Client has started! Established connection with server at port: " + serverPort + "...");
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			
			Move lastMove = GUI.board.getLastMove();
			out.writeObject(lastMove);
			out.flush();
			
			out.writeInt(playerSymbol);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				out.close();
				requestSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
}
