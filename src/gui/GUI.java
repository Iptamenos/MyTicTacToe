package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import ai.BestResponse;
import ai.Board;
import ai.Constants;
import ai.GameParameters;
import ai.MiniMaxAi;
import ai.Move;
import client_server.Server;


public class GUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1666862630121809768L;
	
	public static JPanel panel;
	public static GridLayout layout;

	public static HumanVsHumanButton[] humanVsHumanButtons;
	public static HumanVsAiButton[] humanVsAiButtons;
	public static AiVsAiButton[] aiVsAiButtons;
	public static ClientServerButton[] clientServerButtons;

	public static Board board;
	
	// Menu bars and items
	public static JMenuBar menuBar;
	public static JMenu fileMenu;
	public static JMenuItem newGameItem;
	public static JMenuItem undoItem;
	public static JMenuItem settingsItem;
	public static JMenuItem exitItem;
	
	public static JMenu helpMenu;
	public static JMenuItem howToPlayItem;
	public static JMenuItem aboutItem;
	
	// for Undo operation
	public static int humanPlayerUndoRow;
	public static int humanPlayerUndoCol;
	public static int humanPlayerUndoSymbol;
	
	public GUI(String title) {
		super();
		
		configureGuiStyle();
		
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(title);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (screenSize.getWidth() - getWidth()) / 2, (int) (screenSize.getHeight() - getHeight()) / 2);
	}

	private void addMenus(GUI gui) {
		// Adding the menu bar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		settingsItem = new JMenuItem("Settings");
		exitItem = new JMenuItem("Exit");
		
		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(settingsItem);
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
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					gui.createHumanVsHumanNewGame();
				else if (GameParameters.gameMode == Constants.HumanVsAi)
					gui.createHumanVsAiNewGame();
				else if (GameParameters.gameMode == Constants.AiVsAi)
					gui.createAiVsAiNewGame();
				else if (GameParameters.gameMode == Constants.ClientServer)
					gui.createClientServerNewGame();
			}
		});
		
		undoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});

		settingsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsWindow settings = new SettingsWindow();
				settings.setVisible(true);
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
						"Click on the buttons or press 1-9 on your keyboard to insert a new symbol."
						+ "\nTo win you must place 3 symbols in an row, horizontally, vertically or diagonally.",
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
	

	private static void undo() {

		// System.out.println("undo");
		// This is the undo implementation for Human VS Human mode.
		if (GameParameters.gameMode == Constants.HumanVsHuman) {
			try {
				int row = GUI.board.getLastMove().getRow();
				int col = GUI.board.getLastMove().getCol(); 
				GUI.board.undoMove(row, col, GUI.board.getLastMove().getValue());
				int buttonId = getIdByBoardCell(row, col);
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
						}
					}
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
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
		else if (GameParameters.gameMode == Constants.HumanVsAi) {
			
			try {
			
				GUI.board.undoMove(humanPlayerUndoRow, humanPlayerUndoCol, humanPlayerUndoSymbol);
				int buttonId = getIdByBoardCell(humanPlayerUndoRow, humanPlayerUndoCol);
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
							button.addActionListener(button);
						}
					}
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
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
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						if (button.id == buttonId) {
							button.setIcon(null);
							button.addActionListener(button);
						}
					}
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
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

	public void createClientServerNewGame() {
		
		configureGuiStyle();

		if (menuBar == null)
			addMenus(this);
		
		Server server = new Server(this, GameParameters.serverPort);
		server.start();

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

		clientServerButtons = new ClientServerButton[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int i=0; i<9; i++) {
			clientServerButtons[i] = new ClientServerButton(i, this, GameParameters.clientIP, 
					GameParameters.clientPort, GameParameters.clientServerSymbol);
			panel.add(clientServerButtons[i]);
		}
		
		addKeyListener(gameKeyListener);
		setFocusable(true);
		
		setVisible(true);
	}
	
	public void createHumanVsHumanNewGame() {

		configureGuiStyle();

		if (menuBar == null)
			addMenus(this);
		
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

		addKeyListener(gameKeyListener);
		setFocusable(true);
		
		setVisible(true);
	}
	
	public void createHumanVsAiNewGame() {

		configureGuiStyle();

		if (menuBar == null)
			addMenus(this);
		
		MiniMaxAi aiPlayer = new MiniMaxAi(GameParameters.maxDepth1, Constants.O);
		
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

		addKeyListener(gameKeyListener);
		setFocusable(true);
		
		setVisible(true);
	}
	
	public void createAiVsAiNewGame() {

		configureGuiStyle();
		
		if (menuBar == null)
			addMenus(this);
		
		MiniMaxAi ai1Player = new MiniMaxAi(GameParameters.maxDepth1, Constants.X);
		MiniMaxAi ai2Player = new MiniMaxAi(GameParameters.maxDepth2, Constants.O);

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
			aiVsAiButtons[i] = new AiVsAiButton(i);
			panel.add(aiVsAiButtons[i]);
		}
		
		setVisible(true);
		
		playAiVsAi(ai1Player, ai2Player);
	}
	
	
	private void playAiVsAi(MiniMaxAi ai1Player, MiniMaxAi ai2Player) {
		
		while (!GUI.board.isTerminal()) {

			// AI 1 Move
			Move ai1Move;
			if (ai1Player.getMaxDepth() == Constants.BestResponse) {
				// Best Response Move
				BestResponse bestResponse = new BestResponse(GUI.board.getGameBoard());
				ai1Move = bestResponse.findBestResponse();
			} else {
				// MiniMax AI Move
				GUI.board.setLastSymbolPlayed(Constants.O);
				ai1Move = ai1Player.miniMax(GUI.board);
			}
			
			int ai1_button_id = getIdByBoardCell(ai1Move.getRow(), ai1Move.getCol());
			for (AiVsAiButton button: aiVsAiButtons) {
				if (button.id == ai1_button_id) {
					button.player = Constants.X;
					button.move = ai1Move;
					button.doClick();
				}
			}
			
			if (!GUI.board.isTerminal()) {
				
				// AI 2 Move
				Move ai2Move;
				if (ai2Player.getMaxDepth() == Constants.BestResponse) {
					// Best Response Move
					BestResponse bestResponse = new BestResponse(GUI.board.getGameBoard());
					ai2Move = bestResponse.findBestResponse();
				} else {
					// MiniMax AI Move
					GUI.board.setLastSymbolPlayed(Constants.O);
					ai2Move = ai1Player.miniMax(GUI.board);
				}
				makeMove(ai2Move.getRow(), ai2Move.getCol(), Constants.O);
				
				int ai2_button_id = getIdByBoardCell(ai2Move.getRow(), ai2Move.getCol());
				for (AiVsAiButton button: aiVsAiButtons) {
					if (button.id == ai2_button_id) {
						button.player = Constants.O;
						button.move = ai2Move;
						button.doClick();
					}
				}
				
			}
			
		}
		
		gameOver();
		
	}

	
	private static void configureGuiStyle() {
		try {
			if (GameParameters.guiStyle == Constants.SystemStyle) {
				// Option 1
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else if (GameParameters.guiStyle == Constants.CrossPlatformStyle) {
				// Option 2
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else if (GameParameters.guiStyle == Constants.NimbusStyle) {
				// Option 3
			    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            UIManager.setLookAndFeel(info.getClassName());
			            break;
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void gameOver() {
		// System.out.println("Game Over function called!");

		if (board.getWinner() == Constants.X) {
			System.out.println("Player 1 with \"X\" wins!");
			int input = JOptionPane.showConfirmDialog(null,
					"Player 1 with \"X\" wins!\nPlay again?",
					"Game Over", JOptionPane.YES_NO_OPTION);
			if (input == JOptionPane.OK_OPTION) {
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					createHumanVsAiNewGame();
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
					createHumanVsHumanNewGame();
				} else if (GameParameters.gameMode == Constants.AiVsAi) {
					createAiVsAiNewGame();
				} else if (GameParameters.gameMode == Constants.ClientServer) {
					createClientServerNewGame();
				}
			} else if (input == JOptionPane.NO_OPTION 
					|| input == JOptionPane.CLOSED_OPTION) {
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == Constants.ClientServer) {
					for (ClientServerButton button: clientServerButtons) {
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
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					createHumanVsAiNewGame();
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
					createHumanVsHumanNewGame();
				} else if (GameParameters.gameMode == Constants.AiVsAi) {
					createAiVsAiNewGame();
				} else if (GameParameters.gameMode == Constants.ClientServer) {
					createClientServerNewGame();
				}
			} else if (input == JOptionPane.NO_OPTION 
					|| input == JOptionPane.CLOSED_OPTION) {
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == Constants.ClientServer) {
					for (ClientServerButton button: clientServerButtons) {
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
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					createHumanVsAiNewGame();
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
					createHumanVsHumanNewGame();
				} else if (GameParameters.gameMode == Constants.AiVsAi) {
					createAiVsAiNewGame();
				} else if (GameParameters.gameMode == Constants.ClientServer) {
					createClientServerNewGame();
				}
			} else if (input == JOptionPane.NO_OPTION 
					|| input == JOptionPane.CLOSED_OPTION) {
				if (GameParameters.gameMode == Constants.HumanVsAi) {
					for (HumanVsAiButton button: humanVsAiButtons) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == Constants.HumanVsHuman) {
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						button.removeActionListener(button);
					}
				} else if (GameParameters.gameMode == Constants.ClientServer) {
					for (ClientServerButton button: clientServerButtons) {
						button.removeActionListener(button);
					}
				}
			}
		}
	}
	
	public static KeyListener gameKeyListener = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
			String button = KeyEvent.getKeyText(e.getKeyCode());
			
			if (button.equals("1")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[0].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[0].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[0].doClick();
			} else if (button.equals("2")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[1].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[1].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[1].doClick();
			} else if (button.equals("3")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[2].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[2].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[2].doClick();
			} else if (button.equals("4")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[3].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[3].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[3].doClick();
			} else if (button.equals("5")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[4].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[4].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[4].doClick();
			} else if (button.equals("6")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[5].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[5].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[5].doClick();
			} else if (button.equals("7")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[6].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[6].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[6].doClick();
			} else if (button.equals("8")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[7].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[7].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[7].doClick();
			} else if (button.equals("9")) {
				if (GameParameters.gameMode == Constants.HumanVsAi)
					humanVsAiButtons[8].doClick();
				if (GameParameters.gameMode == Constants.HumanVsHuman)
					humanVsHumanButtons[8].doClick();
				if (GameParameters.gameMode == Constants.ClientServer)
					clientServerButtons[8].doClick();
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
		}
	};
	
	
    // Make a move; it places a symbol on the board
	public static void makeMove(int row, int col, int symbol) {
		board.getGameBoard()[row][col] = symbol;
		// System.out.println("previous move: " + board.getLastMove());
		board.setLastMove(new Move(row, col));
		// System.out.println("this move: " + board.getLastMove());
		board.setLastSymbolPlayed(symbol);
	}
	
	
	public static void main(String[] args) {
		
		// These are the default values.
		// Feel free to change them, before running.
		// You can also change them later, from the GUI window.
		GameParameters.guiStyle = Constants.SystemStyle;
		GameParameters.gameMode = Constants.HumanVsAi;
		// GameParameters.gameMode = Constants.AiVsAi;
		GameParameters.maxDepth1 = Constants.BestResponse;
		// GameParameters.maxDepth1 = 3;
		GameParameters.maxDepth2 = Constants.BestResponse;
		// GameParameters.maxDepth2 = 1;
		GameParameters.player1Color = Constants.BLUE;
		GameParameters.player2Color = Constants.RED;
		GameParameters.clientServerSymbol = Constants.X;
		GameParameters.serverPort = 4000;
		GameParameters.clientIP = "127.0.0.1";
		GameParameters.clientPort = 4001;
		
		GUI gui = new GUI("My TicTacToe");
		
		if (GameParameters.gameMode == Constants.HumanVsAi)
			gui.createHumanVsAiNewGame();
		else if (GameParameters.gameMode == Constants.HumanVsHuman)
			gui.createHumanVsHumanNewGame();
		else if (GameParameters.gameMode == Constants.AiVsAi)
			gui.createAiVsAiNewGame();
		else if (GameParameters.gameMode == Constants.ClientServer)
			gui.createClientServerNewGame();
	}
	
}
