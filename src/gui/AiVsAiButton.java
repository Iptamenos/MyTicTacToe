package gui;

import javax.swing.ImageIcon;

import ai.Constants;
import ai.GameParameters;
import ai.MiniMaxAi;


public class AiVsAiButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1970641473917018979L;

	// Empty: 0, X: 1, O: 0
	int id;
	ImageIcon X;
	ImageIcon O;
	
	public AiVsAiButton(int id, MiniMaxAi aiPlayer1, MiniMaxAi aiPlayer2) {
		this.id = id;
		String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
		String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
		X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
//		this.addActionListener(this);
		setIcon(null);
	}
	
}
