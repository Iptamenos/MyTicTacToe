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
		this.game_params = GUI.game_params;
		this.gui = gui;
		String player1Color = Constants.getColorNameByNumber(this.game_params.getPlayer1Color());
		String player2Color = Constants.getColorNameByNumber(this.game_params.getPlayer2Color());
		this.X = new ImageIcon(this.getClass().getResource("/img/X/" + player1Color + ".png"));
		this.O = new ImageIcon(this.getClass().getResource("/img/O/" + player2Color + ".png"));
		this.addActionListener(this);
		setIcon(null);
		this.aiPlayer = aiPlayer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GUI.undoItem.setEnabled(true);

		setIcon(X);
		
		// get cell coordinates by id
		List<Integer> cell = GUI.getBoardCellById(id);
		
		if (cell != null) {
			GUI.board.makeMove(cell.get(0), cell.get(1), Constants.X);
//			gui.getBoard().printBoard();
			
			GUI.saveUndoMove();

			if (!gui.checkGameOver(this)) {

				// AI Move
				Move aiMove = aiPlayer.miniMax(GUI.board);
				GUI.board.makeMove(aiMove.getRow(), aiMove.getCol(), Constants.O);
				
				int aiMoveButtonId = GUI.getIdByBoardCell(aiMove.getRow(), aiMove.getCol());
				for (HumanVsAiButton button: GUI.humanVsAiButtons) {
					button.aiPlayer = this.aiPlayer;
					if (button.id == aiMoveButtonId) {
						button.setIcon(O);
						button.removeActionListener(button);
					}
				}

				GUI.board.printBoard();
				
				gui.checkGameOver(this);
			}
			
		}
		
	}
	
}
