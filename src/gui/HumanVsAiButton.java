package gui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.BestResponse;
import ai.Board;
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
			
			if (!GUI.board.isTerminal()) {
				
				// TODO: Find a way to get rid of this function!
				if (!guiMatchesWithGameBoard())
					return;
				
				Move aiMove;
				if (this.aiPlayer.getMaxDepth() == Constants.BestResponse) {
					// Best Response Move
					BestResponse bestResponse = new BestResponse(GUI.board.getGameBoard());
					aiMove = bestResponse.findBestResponse();
				} else {
					// MiniMax AI Move
					aiMove = this.aiPlayer.miniMax(GUI.board);
				}
				
				GUI.board.makeMove(aiMove.getRow(), aiMove.getCol(), Constants.O);
				
				int aiMoveButtonId = GUI.getIdByBoardCell(aiMove.getRow(), aiMove.getCol());
//				System.out.println("AI Move [" + aiMove.getRow() + "]" + "[" + aiMove.getCol() +"]");

				for (HumanVsAiButton button: GUI.humanVsAiButtons) {
					button.aiPlayer = this.aiPlayer;
					if (button.id == aiMoveButtonId) {
						button.setIcon(O);
						button.removeActionListener(button);
					}
				}

				Board.printBoard(GUI.board.getGameBoard());
				
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
			} else {
				gui.gameOver();
			}
			
		}
		
	}
	

	// This function resolves an "Undo" operation bug. 
	// Bug description:
	// In the "Human Vs Ai" mode, when the game ended with an "Undo" click,
	// the new game started with a symbol on the GUI board, instead of being empty.
	private boolean guiMatchesWithGameBoard() {
		for (HumanVsAiButton button: GUI.humanVsAiButtons) {
			int id = button.id;
			List<Integer> cell = GUI.getBoardCellById(id); 
			int row = cell.get(0);
			int col = cell.get(1);
			if (GUI.board.getGameBoard()[row][col] != Constants.EMPTY
					&& button.getIcon() == null) {
				GUI.board.getGameBoard()[row][col] = Constants.EMPTY;
				return false;
			}
		}
		return true;
	}

	
}
