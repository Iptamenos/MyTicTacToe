package buttons;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import gui.GUI;
import gui.ResourceLoader;


public class HumanVsHumanButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7433169613318480660L;
	
	// Empty: 0, X: 1, O: 0
	public int id;
	ImageIcon X;
	ImageIcon O;

	
	public HumanVsHumanButton(int id) {
		setFocusable(false);
		this.id = id;
		String player1Color = String.valueOf(GameParameters.player1Color).charAt(0) 
				+ String.valueOf(GameParameters.player1Color).toLowerCase().substring(1);
		String player2Color = String.valueOf(GameParameters.player2Color).charAt(0) 
				+ String.valueOf(GameParameters.player2Color).toLowerCase().substring(1);
		this.X = new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color)));
		this.O = new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color)));
		this.addActionListener(this);
		setIcon(null);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		GUI.undoItem.setEnabled(true);
		
		int turn = Constants.EMPTY;
		if (GUI.board.getLastPlayer() == Constants.X)
			turn = Constants.O;
		else if (GUI.board.getLastPlayer() == Constants.O)
			turn = Constants.X;
		
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
		
		// check if the game is over
		if (GUI.board.isTerminal())
			GUI.gameOver();
		
		try {
			this.removeActionListener(this);
		} catch (NullPointerException ex) {
			// Do nothing
		}
		
	}
	
}
