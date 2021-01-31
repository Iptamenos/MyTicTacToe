package buttons;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Board;
import gui.TicTacToeGUI;
import utilities.Constants;
import utilities.GameParameters;
import utilities.ResourceLoader;


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
		TicTacToeGUI.undoItem.setEnabled(true);
		
		int turn = Constants.EMPTY;
		if (TicTacToeGUI.board.getLastPlayer() == Constants.X)
			turn = Constants.O;
		else if (TicTacToeGUI.board.getLastPlayer() == Constants.O)
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
		List<Integer> cell = TicTacToeGUI.getBoardCellById(id);
		if (cell != null)
			TicTacToeGUI.makeMove(cell.get(0), cell.get(1), turn);
		Board.printBoard(TicTacToeGUI.board.getGameBoard());
		
		// check if the game is over
		if (TicTacToeGUI.board.isTerminal())
			TicTacToeGUI.gameOver();
		
		try {
			this.removeActionListener(this);
		} catch (NullPointerException ex) {
			// Do nothing
		}
		
	}
	
}
