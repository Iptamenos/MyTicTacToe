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
import gui.ResourceLoader;


public class HumanVsAiButton extends XOButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5023834302644965957L;
	
	// Empty: 0, X: 1, O: 0
	public int id;
	ImageIcon X;
	ImageIcon O;
	MiniMaxAi aiPlayer;
	
	
	public HumanVsAiButton(int id, MiniMaxAi aiPlayer) {
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
			// Board.printBoard(GUI.board.getGameBoard());
						
			if (!GUI.board.isTerminal()) {
				
				Move aiMove;
				if (this.aiPlayer.getMaxDepth() == Constants.BEST_RESPONSE) {
					// Best Response Move
					BestResponse bestResponse = new BestResponse(GUI.board.getGameBoard());
					aiMove = bestResponse.findBestResponse();
				} else {
					// MiniMax AI Move
					// System.out.println(GUI.board.getLastMove());
					aiMove = this.aiPlayer.miniMax(GUI.board);
				}
				
				GUI.makeMove(aiMove.getRow(), aiMove.getColumn(), Constants.O);
				
				int aiMoveButtonId = GUI.getIdByBoardCell(aiMove.getRow(), aiMove.getColumn());
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
					GUI.gameOver();
					return;
				} else {
					try {
						this.removeActionListener(this);
					} catch (NullPointerException ex) {
						// Do nothing
					}
				}
			} else {
				GUI.gameOver();
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
