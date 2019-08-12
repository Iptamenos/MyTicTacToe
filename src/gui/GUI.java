package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import ai.Board;
import ai.Constants;
import ai.GameParameters;
import ai.MiniMaxAi;
import ai.Move;


public class GUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1666862630121809768L;
	
	public static JPanel panel;
	public static GridLayout layout;
	public static GameParameters game_params;

	public static HumanVsHumanButton[] humanVsHumanButtons;
	public static HumanVsAiButton[] humanVsAiButtons;
	public static AiVsAiButton[] aiVsAiButtons;

	public static Board board;
	
	// Menu bars and items
	public static JMenuBar menuBar;
	public static JMenu fileMenu;
	public static JMenuItem newGameItem;
	public static JMenuItem undoItem;
	public static JMenuItem preferencesItem;
	public static JMenuItem exitItem;
	
	public static JMenu helpMenu;
	public static JMenuItem howToPlayItem;
	public static JMenuItem aboutItem;
	
	// for Undo operation
	public static int humanPlayerUndoRow;
	public static int humanPlayerUndoCol;
	public static int humanPlayerUndoSymbol;
	
	public GUI() {
		super();
		
		try {
			// Option 1
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		

			// Option 2 (Default)
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		
			// Option 3
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("My TicTacToe");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() - getWidth()) / 2, (int) (screenSize.getHeight() - getHeight()) / 2);
		
		if (menuBar == null)
			addMenus(this);
	}

	private void addMenus(GUI gui) {
		// Adding the menu bar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		preferencesItem = new JMenuItem("Preferences");
		exitItem = new JMenuItem("Exit");
		
		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(preferencesItem);
		fileMenu.add(exitItem);
		
		undoItem.setEnabled(false);
		
		helpMenu = new JMenu("Help");
		howToPlayItem = new JMenuItem("How to Play");
		aboutItem = new JMenuItem("About");
		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		newGameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (game_params.getGameMode() == Constants.HumanVsHuman)
					gui.createHumanVsHumanNewGame();
				else if (game_params.getGameMode() == Constants.HumanVsAi)
					gui.createHumanVsAiNewGame();
				else if (game_params.getGameMode() == Constants.AiVsAi)
					gui.createAiVsAiNewGame();
			}
		});
		
		undoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});

		preferencesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreferencesWindow prefs = new PreferencesWindow(game_params);
				prefs.setVisible(true);
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		howToPlayItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Click on the buttons insert a new symbol.\nTo win you must place 3 symbols in an row, horizontally, vertically or diagonally.",
						"How to Play", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"© Created by: Christos Kormaris",
						"About", JOptionPane.INFORMATION_MESSAGE);			
				}
		});
		
		setJMenuBar(menuBar);
	}
	
	// TODO
	private static void undo() {
		System.out.println("undo");
		// This is the undo implementation for Human VS Human mode.
		if (game_params.getGameMode() == Constants.HumanVsHuman) {
			try {
				int row = GUI.board.getLastMove().getRow();
				int col = GUI.board.getLastMove().getCol(); 
				GUI.board.undoMove(row, col, GUI.board.getLastMove().getValue());
				int buttonId = getIdByBoardCell(row, col);
				if (game_params.getGameMode() == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
						}
					}
				} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}
		}
		
		// This is the undo implementation for Human VS AI mode.
		else if (game_params.getGameMode() == Constants.HumanVsAi) {
			
			try {
			
				GUI.board.undoMove(humanPlayerUndoRow, humanPlayerUndoCol, humanPlayerUndoSymbol);
				int buttonId = getIdByBoardCell(humanPlayerUndoRow, humanPlayerUndoCol);
				if (game_params.getGameMode() == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
							button.addActionListener(button);
						}
					}
				} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
							button.addActionListener(button);
						}
					}
				}

				int aiPlayerSymbol = Constants.EMPTY;
				if (humanPlayerUndoSymbol == Constants.X)
					aiPlayerSymbol = Constants.O;
				if (humanPlayerUndoSymbol == Constants.O)
					aiPlayerSymbol = Constants.X;
				
				int row = GUI.board.getLastMove().getRow();
				int col = GUI.board.getLastMove().getCol();
				GUI.board.undoMove(row, col, aiPlayerSymbol);
				buttonId = getIdByBoardCell(row, col);
				if (game_params.getGameMode() == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
							button.addActionListener(button);
						}
					}
				} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
							button.addActionListener(button);
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.println("No move has been made yet!");
				System.err.flush();
			}
		}
		undoItem.setEnabled(false);
	}

	public static void saveUndoMove() {
		humanPlayerUndoRow = board.getLastMove().getRow();
		humanPlayerUndoCol = board.getLastMove().getCol();
		humanPlayerUndoSymbol = board.getLastMove().getValue();
//		humanPlayerUndoSymbol = board.getTurn();
	}
	
	/* This method returns a pair of 2 integers from 0-2, given an id from 0-8.
	 * We need something like a function, namely f, that contains the following points:
	 * f(0, 0) = 0 = 0*3 + 0
	 * f(0, 1) = 0 = 0*3 + 1
	 * f(0, 2) = 0 = 0*3 + 2
	 * f(1, 0) = 0 = 1*3 + 0
	 * f(1, 1) = 0 = 1*3 + 1
	 * f(1, 2) = 0 = 1*3 + 2
	 * f(2, 0) = 0 = 2*3 + 0
	 * f(2, 1) = 0 = 2*3 + 1
	 * f(2, 2) = 0 = 2*3 + 2
	*/ 
	public static List<Integer> getBoardCellById(int id) {
		List<Integer> cell = new ArrayList<Integer>();
		int i = 0, j = 0;
		if (id / 3 == 0) {  // we know that i=0
			i = 0;
			j = id % 3;
		} else if (id / 3 == 1) {  // we know that i=1
			i = 1;
			j = id % 3;
		}  else if (id / 3 == 2) {  // we know that i=2
			i = 2;
			j = id % 3; 
		}
		cell.add(i);
		cell.add(j);
		
		return cell;
	}
	
	public static int getIdByBoardCell(int row, int col) {
		return row * 3 + col;
	}
    
	public void createHumanVsHumanNewGame() {
		if (panel != null) {
			remove(panel);
			revalidate();
			repaint();
		}
		
		panel = new JPanel();
		add(panel);

		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();

		humanVsHumanButtons = new HumanVsHumanButton[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int i=0; i<9; i++) {
			humanVsHumanButtons[i] = new HumanVsHumanButton(i, this);
			panel.add(humanVsHumanButtons[i]);
		}

		setVisible(true);
	}
	
	public void createHumanVsAiNewGame() {
		MiniMaxAi aiPlayer = new MiniMaxAi(game_params.getMaxDepth(), Constants.O);
		
		if (panel != null) {
			remove(panel);
			revalidate();
			repaint();
		}
		
		panel = new JPanel();
		add(panel);

		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();

		humanVsAiButtons = new HumanVsAiButton[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int i=0; i<9; i++) {
			humanVsAiButtons[i] = new HumanVsAiButton(i, this, aiPlayer);
			panel.add(humanVsAiButtons[i]);
		}

		setVisible(true);
	}
	
	public void createAiVsAiNewGame() {
		MiniMaxAi ai1Player = new MiniMaxAi(game_params.getMaxDepth(), Constants.X);
		MiniMaxAi ai2Player = new MiniMaxAi(game_params.getMaxDepth(), Constants.O);

		if (panel != null) {
			remove(panel);
			revalidate();
			repaint();
		}
		
		panel = new JPanel();
		add(panel);

		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();

		aiVsAiButtons = new AiVsAiButton[9];

		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int i=0; i<9; i++) {
			aiVsAiButtons[i] = new AiVsAiButton(i, ai1Player, ai2Player);
			panel.add(aiVsAiButtons[i]);
		}
		
		setVisible(true);
		
		playAiVsAi(ai1Player, ai2Player);
	}
	
	
	private void playAiVsAi(MiniMaxAi ai1Player, MiniMaxAi ai2Player) {
		
		while (!GUI.board.isTerminal()) {

			// AI 1 Move
			Move ai1Move = ai1Player.miniMax(GUI.board);
			GUI.board.makeMove(ai1Move.getRow(), ai1Move.getCol(), Constants.X);
			
			int ai1MoveButtonId = GUI.getIdByBoardCell(ai1Move.getRow(), ai1Move.getCol());
			for (AiVsAiButton button: aiVsAiButtons) {
				if (button.id == ai1MoveButtonId) {
					button.setIcon(button.X);
					button.removeActionListener(button);
				}
			}
	
			board.printBoard();
			
			// change turn
			if (board.getTurn() == Constants.X) {
				board.setTurn(Constants.O);
			} else if (board.getTurn() == Constants.O) {
				board.setTurn(Constants.X);
			}
						
			if (!GUI.board.isTerminal()) {
				
				// AI 2 Move
				Move ai2Move = ai2Player.miniMax(GUI.board);
				GUI.board.makeMove(ai2Move.getRow(), ai2Move.getCol(), Constants.O);
				
				int ai2MoveButtonId = GUI.getIdByBoardCell(ai2Move.getRow(), ai2Move.getCol());
				for (AiVsAiButton button: aiVsAiButtons) {
					if (button.id == ai2MoveButtonId) {
						button.setIcon(button.O);
						button.removeActionListener(button);
					}
				}
	
				board.printBoard();
				
				// change turn
				if (board.getTurn() == Constants.X) {
					board.setTurn(Constants.O);
				} else if (board.getTurn() == Constants.O) {
					board.setTurn(Constants.X);
				}
				
			}
			
		}
		
		checkGameOver(null);
		
	}


	public boolean checkGameOver(XOButton XOButton) {

		// check if the game is over
		if (GUI.board.isTerminal()) {
			if (board.getWinner() == Constants.X) {
				System.out.println("Player 1 with \"X\" wins!");
				int input = JOptionPane.showConfirmDialog(null,
						"Player 1 with \"X\" wins!\nPlay again?",
						"Game Over", JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.OK_OPTION) {
					if (game_params.getGameMode() == Constants.HumanVsAi) {
						createHumanVsAiNewGame();
					} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
						createHumanVsHumanNewGame();
					} else if (game_params.getGameMode() == Constants.AiVsAi) {
						createAiVsAiNewGame();
					}
				} else if (input == JOptionPane.NO_OPTION 
						|| input == JOptionPane.CLOSED_OPTION) {
					if (game_params.getGameMode() == Constants.HumanVsAi) {
						for (HumanVsAiButton button: humanVsAiButtons) {
							button.removeActionListener(button);
						}
					} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
						for (HumanVsHumanButton button: humanVsHumanButtons) {
							button.removeActionListener(button);
						}
					}
				}
			} else if (board.getWinner() == Constants.O) {
				System.out.println("Player 2 with \"O\" wins!");
				int input = JOptionPane.showConfirmDialog(null,
						"Player 2 with \"O\" wins!\nPlay again?",
						"Game Over", JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.OK_OPTION) {
					if (game_params.getGameMode() == Constants.HumanVsAi) {
						createHumanVsAiNewGame();
					} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
						createHumanVsHumanNewGame();
					} else if (game_params.getGameMode() == Constants.AiVsAi) {
						createAiVsAiNewGame();
					}
				} else if (input == JOptionPane.NO_OPTION 
						|| input == JOptionPane.CLOSED_OPTION) {
					if (game_params.getGameMode() == Constants.HumanVsAi) {
						for (HumanVsAiButton button: humanVsAiButtons) {
							button.removeActionListener(button);
						}
					} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
						for (HumanVsHumanButton button: humanVsHumanButtons) {
							button.removeActionListener(button);
						}
					}
				}
			} else if (board.getWinner() == Constants.EMPTY) {
				System.out.println("It is a draw!");
				int input = JOptionPane.showConfirmDialog(null,
						"It is a draw!\nPlay again?",
						"Game Over", JOptionPane.YES_NO_OPTION);
				if (input == JOptionPane.OK_OPTION) {
					if (game_params.getGameMode() == Constants.HumanVsAi) {
						createHumanVsAiNewGame();
					} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
						createHumanVsHumanNewGame();
					} else if (game_params.getGameMode() == Constants.AiVsAi) {
						createAiVsAiNewGame();
					}
				} else if (input == JOptionPane.NO_OPTION 
						|| input == JOptionPane.CLOSED_OPTION) {
					if (game_params.getGameMode() == Constants.HumanVsAi) {
						for (HumanVsAiButton button: humanVsAiButtons) {
							button.removeActionListener(button);
						}
					} else if (game_params.getGameMode() == Constants.HumanVsHuman) {
						for (HumanVsHumanButton button: humanVsHumanButtons) {
							button.removeActionListener(button);
						}
					}
				}
			}
			return true;
		}
		else {
			try {
				XOButton.removeActionListener(XOButton);
			} catch (NullPointerException e) {
				// Do nothing
			}
			return false;
		}
		
	}
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		
		game_params = new GameParameters(3, Constants.BLUE, Constants.RED, Constants.HumanVsAi);
		
		if (game_params.getGameMode() == Constants.HumanVsAi)
			gui.createHumanVsAiNewGame();
		else if (game_params.getGameMode() == Constants.HumanVsHuman)
			gui.createHumanVsHumanNewGame();
		else if (game_params.getGameMode() == Constants.AiVsAi)
			gui.createAiVsAiNewGame();
	}
	
}
