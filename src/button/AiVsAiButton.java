package button;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import ai.Board;
import gui.TicTacToeGUI;
import utility.Constants;
import utility.GameParameters;
import utility.ResourceLoader;


public class AiVsAiButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1970641473917018979L;

	// Empty: 0, X: 1, O: 0
	public int id;
	ImageIcon X;
	ImageIcon O;
	public int player;
	
	
	public AiVsAiButton(int id) {
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
		this.player = Constants.EMPTY;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("Clicked button.");
		
		// List<Integer> cell = GUI.getBoardCellById(id);
		// System.out.println("button cell: [" + cell.get(0) + "]" + "[" + cell.get(1) + "]");
		
		if (player == Constants.X)
			setIcon(X);
		else if (player == Constants.O)
			setIcon(O);
		
		Board.printBoard(TicTacToeGUI.board.getGameBoard());
		
		removeActionListener(this);
	
	}
	
	
}
