package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import ai.Constants;
import ai.GameParameters;


public class HumanVsHumanButton extends JButton implements ActionListener {

	// empty: 0, X: 1, O: 0
	int id;
	GUI gui;
	ImageIcon X;
	ImageIcon O;
	GameParameters game_params;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1970641473917018979L;

	public HumanVsHumanButton(int id, GUI gui) {
		this.id = id;
		this.gui = gui;
		this.game_params = GUI.game_params;
		X = new ImageIcon(this.getClass().getResource("/img/X/" + this.game_params.getPlayer1Color() + ".png"));
		O = new ImageIcon(this.getClass().getResource("/img/O/" + this.game_params.getPlayer2Color() + ".png"));
		this.addActionListener(this);
		setIcon(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Touched Human vs Human button");

		// add X or O on the board GUI
		if (gui.getTurn() == Constants.EMPTY) {
			setIcon(null);
		} else if (gui.getTurn() == Constants.X) {
			setIcon(X);
		} else if (gui.getTurn() == Constants.O) {
			setIcon(O);
		}
		
		// get cell coordinates by id
		List<Integer> cell = gui.getBoardCellById(id);
		if (cell != null)
			gui.getBoard().getGameBoard()[cell.get(0)][cell.get(1)] = gui.getTurn();
		gui.getBoard().printBoard();
		
		// change turn
		if (gui.getTurn() == Constants.X) {
			gui.setTurn(Constants.O);
		} else if (gui.getTurn() == Constants.O) {
			gui.setTurn(Constants.X);
		}
		
		// check if the game is over
		isGameOver();
		
	}
	
	private boolean isGameOver() {

		// check if the game is over
		if (gui.getBoard().isTerminal()) {
			gui.setWinner(gui.getBoard().getWinner());
			if (gui.getWinner() == Constants.X) {
				System.out.println("Player 1 with \"X\" wins!");
				int input = JOptionPane.showOptionDialog(null, "Player 1 with \"X\" wins!\nPlay again?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					gui.createHumanVsHumanNewGame();
				} else if (input == JOptionPane.CANCEL_OPTION) {
					for (HumanVsHumanButton button: gui.getHumanVsHumanButtons()) {
						button.removeActionListener(button);
					}
				}
			} else if (gui.getWinner() == Constants.O) {
				System.out.println("Player 2 with \"O\" wins!");
				int input = JOptionPane.showOptionDialog(null, "Player 2 with \"O\" wins!\nPlay again?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					gui.createHumanVsHumanNewGame();
				} else if (input == JOptionPane.CANCEL_OPTION) {
					for (HumanVsHumanButton button: gui.getHumanVsHumanButtons()) {
						button.removeActionListener(button);
					}
				}	
			} else if (gui.getWinner() == Constants.EMPTY) {
				System.out.println("It is a draw!");
				int input = JOptionPane.showOptionDialog(null, "It is a draw!\nPlay again?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					gui.createHumanVsHumanNewGame();
				} else if (input == JOptionPane.CANCEL_OPTION) {
					for (HumanVsHumanButton button: gui.getHumanVsHumanButtons()) {
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
