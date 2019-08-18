package gui;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import client_server.Client;


public class ClientServerButton extends XOButton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7433169613318480660L;
	
	// Empty: 0, X: 1, O: 0
	int id;
	GUI gui;
	ImageIcon X;
	ImageIcon O;
	GameParameters game_params;
	String serverIP;
	int serverPort;
	Client client;
	int playerSymbol;
	boolean programmaticallyPressed = false;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isProgrammaticallyPressed() {
		return programmaticallyPressed;
	}

	public void setProgrammaticallyPressed(boolean programmaticallyPressed) {
		this.programmaticallyPressed = programmaticallyPressed;
	}

	public ClientServerButton(int id, GUI gui, String serverIP, int serverPort, int playerSymbol) {
		this.id = id;
		this.game_params = GUI.game_params;
		this.gui = gui;
		String player1Color = Constants.getColorNameByNumber(this.game_params.getPlayer1Color());
		String player2Color = Constants.getColorNameByNumber(this.game_params.getPlayer2Color());
		this.X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		this.O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
		this.addActionListener(this);
		setIcon(null);
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.client = new Client(gui, serverIP, serverPort, playerSymbol);
		this.playerSymbol = playerSymbol;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// GUI.undoItem.setEnabled(false);  // uncomment only if needed
		
		int turn = Constants.EMPTY;
		if (GUI.board.getLastSymbolPlayed() == Constants.X)
			turn = Constants.O;
		else if (GUI.board.getLastSymbolPlayed() == Constants.O)
			turn = Constants.X;
		
		if (turn != playerSymbol)
			return;
		
		if (programmaticallyPressed) {
			turn = GUI.board.getLastSymbolPlayed(); 
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
			GUI.board.makeMove(cell.get(0), cell.get(1), turn);
		Board.printBoard(GUI.board.getGameBoard());
		
		if (!programmaticallyPressed) {
			this.client = new Client(gui, serverIP, serverPort, playerSymbol);
			this.client.run();
		}
		
		if (!programmaticallyPressed) {
			// check if the game is over
			if (GUI.board.isTerminal()) {
				gui.gameOver();
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
