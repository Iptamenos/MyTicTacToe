package buttons;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import gui.GUI;
import gui.ResourceLoader;


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
		String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
		String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
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
		
		Board.printBoard(GUI.board.getGameBoard());
		
		removeActionListener(this);
	
	}
	
	
}
