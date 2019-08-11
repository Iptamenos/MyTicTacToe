package gui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.Constants;
import ai.GameParameters;
import ai.MiniMaxAi;
import ai.Move;


public class HumanVsAiButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5023834302644965957L;
	
	// Empty: 0, X: 1, O: 0
	int id;
	GUI gui;
	ImageIcon X;
	ImageIcon O;
	MiniMaxAi aiPlayer;
	GameParameters game_params;
	
	public HumanVsAiButton(int id, GUI gui, MiniMaxAi aiPlayer) {
		this.id = id;
		this.gui = gui;
		this.game_params = GUI.game_params;
		String player1Color = Constants.getColorNameByNumber(this.game_params.getPlayer1Color());
		String player2Color = Constants.getColorNameByNumber(this.game_params.getPlayer2Color());
		X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
		this.addActionListener(this);
		setIcon(null);
		this.aiPlayer = aiPlayer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setIcon(X);
		
		// get cell coordinates by id
		List<Integer> cell = gui.getBoardCellById(id);
		
		if (cell != null) {
			gui.getBoard().makeMove(cell.get(0), cell.get(1), Constants.X);
//			gui.getBoard().printBoard();

			if (!gui.checkGameOver(this)) {

				// AI Move
				Move aiMove = aiPlayer.miniMax(gui.getBoard());
				gui.getBoard().makeMove(aiMove.getRow(), aiMove.getCol(), Constants.O);
				this.gui.getBoard().copyBoard(gui.getBoard());
				
				int aiMoveButtonId = GUI.getIdByBoardCell(aiMove.getRow(), aiMove.getCol());
				for (HumanVsAiButton button: gui.getHumanVsAiButtons()) {
					button.aiPlayer = this.aiPlayer;
					if (button.id == aiMoveButtonId) {
						button.setIcon(O);
						button.removeActionListener(button);
					}
				}

				gui.getBoard().printBoard();
				
				// change turn
				if (gui.getTurn() == Constants.X) {
					gui.setTurn(Constants.O);
				} else if (gui.getTurn() == Constants.O) {
					gui.setTurn(Constants.X);
				}
				
				gui.checkGameOver(this);
			}
			
		}
		
	}
	
}
