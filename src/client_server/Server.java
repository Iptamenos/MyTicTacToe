package client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import ai.Move;
import buttons.ClientServerButton;
import gui.GUI;

public class Server extends Thread {
	
	int serverPort;
	
	public Server() {
		
	}
	
	public Server(int port) {
		this.serverPort = port;
	}
	
	@Override
	public void run() {
		
		ServerSocket serverSocket = null;
		Socket connection = null;
		ObjectInputStream in = null;
		try {
			System.out.println("Server thread with id " + this.getId() + 
					": Server listening at port: " + serverPort + "...");
			serverSocket = new ServerSocket(serverPort);

			while (true) {

				connection = serverSocket.accept();
				System.out.println("Server accepted connection!");
				in = new ObjectInputStream(connection.getInputStream());
				
				Move lastMove = (Move) in.readObject();
				int id = GUI.getIdByBoardCell(lastMove.getRow(), lastMove.getColumn());
				// System.out.println(id);
				
				int oppossingPlayerSymbol = in.readInt();
				
				GUI.board.setLastPlayer(oppossingPlayerSymbol);
				
				for (ClientServerButton button: GUI.clientServerButtons) {
					if (button.id == id) {
						// Programmatically press the button.
						button.programmaticallyPressed = true;
						button.doClick();
						button.programmaticallyPressed = false;
					}
				}
				// GUI.board.printBoard();

				if (GUI.board.isTerminal()) {
					break;
				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				connection.close();
				serverSocket.close();
			} catch (BindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		GUI.gameOver();

	}

}
