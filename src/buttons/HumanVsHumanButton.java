package buttons;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import gui.GUI;


public class HumanVsHumanButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7433169613318480660L;
	
	// Empty: 0, X: 1, O: 0
	public int id;
	GUI gui;
	ImageIcon X;
	ImageIcon O;

	
	public HumanVsHumanButton(int id, GUI gui) {
		this.id = id;
		this.gui = gui;
		String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
		String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
		this.X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		this.O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
		this.addActionListener(this);
		setIcon(null);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		GUI.undoItem.setEnabled(true);
		
		int turn = Constants.EMPTY;
		if (GUI.board.getLastLetterPlayed() == Constants.X)
			turn = Constants.O;
		else if (GUI.board.getLastLetterPlayed() == Constants.O)
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
			gui.gameOver();
		
		try {
			this.removeActionListener(this);
		} catch (NullPointerException ex) {
			// Do nothing
		}
		
		GUI.saveUndoMove();
	}
	
	
}
