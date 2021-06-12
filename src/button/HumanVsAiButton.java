package button;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;

import ai.BestResponse;
import ai.Board;
import ai.MiniMaxAi;
import ai.Move;
import gui.TicTacToeGUI;
import utility.Constants;
import utility.GameParameters;
import utility.ResourceLoader;


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
		TicTacToeGUI.undoItem.setEnabled(true);
		
		setIcon(X);
		
		// get cell coordinates by id
		List<Integer> cell = TicTacToeGUI.getBoardCellById(id);
		
		if (cell != null) {
			TicTacToeGUI.makeMove(cell.get(0), cell.get(1), Constants.X);
			
			// This fixes an "Undo" operation bug.
			if (!gameBoardMatchesWithGUIBoard()) {
				TicTacToeGUI.undo();
				return;
			}
			
			// System.out.println(GUI.board.getLastMove());
			// Board.printBoard(GUI.board.getGameBoard());
						
			if (!TicTacToeGUI.board.isTerminal()) {
				
				Move aiMove;
				if (this.aiPlayer.getMaxDepth() == Constants.BEST_RESPONSE) {
					// Best Response Move
					BestResponse bestResponse = new BestResponse(TicTacToeGUI.board.getGameBoard());
					aiMove = bestResponse.findBestResponse();
				} else {
					// MiniMax AI Move
					// System.out.println(GUI.board.getLastMove());
					aiMove = this.aiPlayer.miniMax(TicTacToeGUI.board);
				}
				
				TicTacToeGUI.makeMove(aiMove.getRow(), aiMove.getColumn(), Constants.O);
				
				int aiMoveButtonId = TicTacToeGUI.getIdByBoardCell(aiMove.getRow(), aiMove.getColumn());
				// System.out.println("AI Move [" + aiMove.getRow() + "]" + "[" + aiMove.getCol() +"]");

				for (HumanVsAiButton button: TicTacToeGUI.humanVsAiButtons) {
					button.aiPlayer = this.aiPlayer;
					if (button.id == aiMoveButtonId) {
						button.setIcon(O);
						button.removeActionListener(button);
					}
				}

				Board.printBoard(TicTacToeGUI.board.getGameBoard());
				
				// check if the game is over
				if (TicTacToeGUI.board.isTerminal()) {
					TicTacToeGUI.gameOver();
					return;
				} else {
					try {
						this.removeActionListener(this);
					} catch (NullPointerException ex) {
						// Do nothing
					}
				}
			} else {
				TicTacToeGUI.gameOver();
			}
			
		}
	}

	
	// It checks if the GUI board is the identical to the gameBoard 2d-array.
	// It fixes an "Undo" operation bug.
	private boolean gameBoardMatchesWithGUIBoard() {
		for (HumanVsAiButton button: TicTacToeGUI.humanVsAiButtons) {
			List<Integer> cell = TicTacToeGUI.getBoardCellById(button.id);
			if (button.getIcon() == null
				&& TicTacToeGUI.board.getGameBoard()[cell.get(0)][cell.get(1)] != Constants.EMPTY) {
				return false;
			}
		}
		return true;
	}
	
}
