package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import ai.MiniMaxAi;
import ai.Move;


public class HumanVsAiButton extends JButton implements ActionListener {

	// empty: 0, X: 1, O: 0
	int id;
	GUI gui;
	ImageIcon X;
	ImageIcon O;
	MiniMaxAi aiPlayer;
	Board board;
	GameParameters game_params;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1970641473917018979L;

	public HumanVsAiButton(int id, GUI gui, MiniMaxAi aiPlayer) {
		this.id = id;
		this.gui = gui;
		this.game_params = GUI.game_params;
		X = new ImageIcon(this.getClass().getResource("/img/X/" + this.game_params.getPlayer1Color() + ".png"));
		O = new ImageIcon(this.getClass().getResource("/img/O/" + this.game_params.getPlayer2Color() + ".png"));
		this.addActionListener(this);
		setIcon(null);
		this.aiPlayer = aiPlayer;
		board = gui.getBoard();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setIcon(X);
		
		// get cell coordinates by id
		List<Integer> cell = gui.getBoardCellById(id);
		System.out.println("player row: " + cell.get(0));
		System.out.println("player col: " + cell.get(1));
		
		if (cell != null) {
			gui.getBoard().getGameBoard()[cell.get(0)][cell.get(1)] = Constants.X;
			this.board.copyBoard(gui.getBoard());
			this.board.makeMove(cell.get(0), cell.get(1), Constants.X);
//			this.board.printBoard();

			if (!isGameOver()) {

				if (!this.board.isGameBoardFull()) {
					// AI Move
					Move aiMove = aiPlayer.miniMax(this.board);
					System.out.println("ai row: " + aiMove.getRow());
					System.out.println("ai col: " + aiMove.getCol());
					this.board.makeMove(aiMove.getRow(), aiMove.getCol(), Constants.O);
					this.gui.getBoard().copyBoard(this.board);
					
					int aiMoveButtonId = GUI.getIdByBoardCell(aiMove.getRow(), aiMove.getCol());
					for (HumanVsAiButton button: gui.getHumanVsAiButtons()) {
						button.aiPlayer = this.aiPlayer;
						if (button.id == aiMoveButtonId) {
							button.setIcon(O);
							button.removeActionListener(button);
						}
					}
				}

				gui.getBoard().printBoard();
				
				// change turn
				if (gui.getTurn() == Constants.X) {
					gui.setTurn(Constants.O);
				} else if (gui.getTurn() == Constants.O) {
					gui.setTurn(Constants.X);
				}
				
				isGameOver();
			}
			
		}
		
	}

	private boolean isGameOver() {

		// check if the game is over
		if (gui.getBoard().isTerminal()) {
			gui.setWinner(gui.getBoard().getWinner());
			if (gui.getWinner() == Constants.X) {
				System.out.println("Player 1 with \"X\" wins!");
				int input = JOptionPane.showOptionDialog(null, "Player 1 with \"X\" wins!\nPlay again?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					gui.createHumanVsAiNewGame();
				} else if (input == JOptionPane.CANCEL_OPTION) {
					for (HumanVsAiButton button: gui.getHumanVsAiButtons()) {
						button.removeActionListener(button);
					}
				}
			} else if (gui.getWinner() == Constants.O) {
				System.out.println("Player 2 with \"O\" wins!");
				int input = JOptionPane.showOptionDialog(null, "Player 2 with \"O\" wins!\nPlay again?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					gui.createHumanVsAiNewGame();
				} else if (input == JOptionPane.CANCEL_OPTION) {
					for (HumanVsAiButton button: gui.getHumanVsAiButtons()) {
						button.removeActionListener(button);
					}
				}	
			} else if (gui.getWinner() == Constants.EMPTY) {
				System.out.println("It is a draw!");
				int input = JOptionPane.showOptionDialog(null, "It is a draw!\nPlay again?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					gui.createHumanVsAiNewGame();
				} else if (input == JOptionPane.CANCEL_OPTION) {
					for (HumanVsAiButton button: gui.getHumanVsAiButtons()) {
						button.removeActionListener(button);
					}
				}
			}
			return true;
		}
		else {
			this.removeActionListener(this);
			return false;
		}
		
	}
	
}
