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
		this.gui = gui;
		this.game_params = GUI.game_params;
		String player1Color = Constants.getColorNameByNumber(this.game_params.getPlayer1Color());
		String player2Color = Constants.getColorNameByNumber(this.game_params.getPlayer2Color());
		X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
		this.addActionListener(this);
		setIcon(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Touched Human vs Human button");

		// add X or O on the board GUI
		if (gui.getTurn() == Constants.EMPTY) {
			setIcon(null);
		} else if (gui.getTurn() == Constants.X) {
			setIcon(X);
		} else if (gui.getTurn() == Constants.O) {
			setIcon(O);
		}
			
		// get cell coordinates by id
		List<Integer> cell = gui.getBoardCellById(id);
		if (cell != null)
			gui.getBoard().getGameBoard()[cell.get(0)][cell.get(1)] = gui.getTurn();
		gui.getBoard().printBoard();
		
		// change turn
		if (gui.getTurn() == Constants.X) {
			gui.setTurn(Constants.O);
		} else if (gui.getTurn() == Constants.O) {
			gui.setTurn(Constants.X);
		}
		
		// check if the game is over
		gui.checkGameOver(this);
		
	}
	
}
