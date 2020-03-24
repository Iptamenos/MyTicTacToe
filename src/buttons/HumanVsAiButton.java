package buttons;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.BestResponse;
import ai.Board;
import ai.Constants;
import ai.GameParameters;
import ai.MiniMaxAi;
import ai.Move;
import gui.GUI;


public class HumanVsAiButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5023834302644965957L;
	
	// Empty: 0, X: 1, O: 0
	public int id;
	GUI gui;
	ImageIcon X;
	ImageIcon O;
	MiniMaxAi aiPlayer;
	
	
	public HumanVsAiButton(int id, GUI gui, MiniMaxAi aiPlayer) {
		this.id = id;
		this.gui = gui;
		String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
		String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
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
			GUI.makeMove(cell.get(0), cell.get(1), Constants.X);
			
			// This fixes an "Undo" operation bug.
			if (!gameBoardMatchesWithGUIBoard()) {
				GUI.undo();
				return;
			}

			// System.out.println(GUI.board.getLastMove());
			// gui.getBoard().printBoard();
			
			GUI.saveUndoMove();
			
			if (!GUI.board.isTerminal()) {
				
				Move aiMove;
				if (this.aiPlayer.getMaxDepth() == Constants.BestResponse) {
					// Best Response Move
					BestResponse bestResponse = new BestResponse(GUI.board.getGameBoard());
					aiMove = bestResponse.findBestResponse();
				} else {
					// MiniMax AI Move
					// System.out.println(GUI.board.getLastMove());
					aiMove = this.aiPlayer.miniMax(GUI.board);
				}
				
				GUI.makeMove(aiMove.getRow(), aiMove.getCol(), Constants.O);
				
				int aiMoveButtonId = GUI.getIdByBoardCell(aiMove.getRow(), aiMove.getCol());
				// System.out.println("AI Move [" + aiMove.getRow() + "]" + "[" + aiMove.getCol() +"]");

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
					return;
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

	
	// It checks if the GUI board is the identical to the gameBoard 2d-array.
	// It fixes an "Undo" operation bug.
	private boolean gameBoardMatchesWithGUIBoard() {
		for (HumanVsAiButton button: GUI.humanVsAiButtons) {
			List<Integer> cell = GUI.getBoardCellById(button.id);
			if (button.getIcon() == null
				&& GUI.board.getGameBoard()[cell.get(0)][cell.get(1)] != Constants.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
}
