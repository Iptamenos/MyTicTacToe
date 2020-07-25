package buttons;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import client_server.Client;
import gui.GUI;
import gui.ResourceLoader;


public class ClientServerButton extends XOButton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7433169613318480660L;
	
	// Empty: 0, X: 1, O: 0
	public int id;
	ImageIcon X;
	ImageIcon O;
	String serverIP;
	int serverPort;
	Client client;
	int playerLetter;
	public boolean programmaticallyPressed = false;
	
	
	public ClientServerButton(int id, String serverIP, int serverPort, int playerSymbol) {
		setFocusable(false);
		this.id = id;
		String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
		String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
		this.X = new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color)));
		this.O = new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color)));
		this.addActionListener(this);
		setIcon(null);
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.client = new Client(serverIP, serverPort, playerSymbol);
		this.playerLetter = playerSymbol;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// GUI.undoItem.setEnabled(false);  // uncomment only if needed
		
		int turn = Constants.EMPTY;
		if (GUI.board.getLastPlayer() == Constants.X)
			turn = Constants.O;
		else if (GUI.board.getLastPlayer() == Constants.O)
			turn = Constants.X;
		
		if (turn != playerLetter)
			return;
		
		if (programmaticallyPressed) {
			turn = GUI.board.getLastPlayer(); 
			GUI.board.changeLastSymbolPlayed();
		}
		
		// add X or O on the board GUI
		if (turn == Constants.EMPTY) {
			setIcon(null);
		} else if (turn == Constants.X) {
			setIcon(X);
		} else if (turn == Constants.O) {
			setIcon(O);
		}
			
		// get cell coordinates by id
		List<Integer> cell = GUI.getBoardCellById(id);
		if (cell != null)
			GUI.makeMove(cell.get(0), cell.get(1), turn);
		Board.printBoard(GUI.board.getGameBoard());
		
		if (!programmaticallyPressed) {
			this.client = new Client(serverIP, serverPort, playerLetter);
			this.client.run();
			
			// check if the game is over
			if (GUI.board.isTerminal()) {
				GUI.gameOver();
			}
		}
		try {
			this.removeActionListener(this);
		} catch (NullPointerException ex) {
			// Do nothing
		}
		GUI.clientServerButtons[id] = this;
	}
	
	
}