package gui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Constants;
import ai.GameParameters;


public class HumanVsHumanButton extends XOButton {

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

	public HumanVsHumanButton(int id, GUI gui) {
		this.id = id;
		this.game_params = GUI.game_params;
		this.gui = gui;
		String player1Color = Constants.getColorNameByNumber(this.game_params.getPlayer1Color());
		String player2Color = Constants.getColorNameByNumber(this.game_params.getPlayer2Color());
		this.X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		this.O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
		this.addActionListener(this);
		setIcon(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GUI.undoItem.setEnabled(true);

		// add X or O on the board GUI
		if (GUI.board.getTurn() == Constants.EMPTY) {
			setIcon(null);
		} else if (GUI.board.getTurn() == Constants.X) {
			setIcon(X);
		} else if (GUI.board.getTurn() == Constants.O) {
			setIcon(O);
		}
			
		// get cell coordinates by id
		List<Integer> cell = GUI.getBoardCellById(id);
		if (cell != null)
			GUI.board.makeMove(cell.get(0), cell.get(1), GUI.board.getTurn());
		GUI.board.printBoard();
		
		// check if the game is over
		if (GUI.board.isTerminal()) {
			gui.gameOver();
		} else {
			try {
				this.removeActionListener(this);
			} catch (NullPointerException ex) {
				// Do nothing
			}
		}
		
		GUI.saveUndoMove();
	}
	
}
