package buttons;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import gui.GUI;


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
		this.id = id;
		String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
		String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
		X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
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
		
		Board.printBoard(GUI.board.getGameBoard());
		
		removeActionListener(this);
	
	}
	
	
}
