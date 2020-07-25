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
import java.util.Stack;

import javax.swing.ImageIcon;
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
import buttons.AiVsAiButton;
import buttons.ClientServerButton;
import buttons.HumanVsAiButton;
import buttons.HumanVsHumanButton;
import client_server.Server;


public class GUI {
	
	public static JFrame frame;
	
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
	public static JMenuItem redoItem;
	public static JMenuItem settingsItem;
	public static JMenuItem exitItem;
	
	public static JMenu helpMenu;
	public static JMenuItem howToPlayItem;
	public static JMenuItem aboutItem;
	
	// These Stack objects are used for the "Undo" and "Redo" functionalities.
	static Stack<Board> undoBoards = new Stack<Board>();
	static Stack<Board> redoBoards = new Stack<Board>();
	
	public GUI(String title) {
		
		configureGuiStyle();
		
		frame = new JFrame();
		
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.getWidth() - frame.getWidth()) / 2, (int) (screenSize.getHeight() - frame.getHeight()) / 2);
	}

	
	private static void addMenus() {
		// Adding the menu bar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		undoItem = new JMenuItem("Undo    Ctrl+Z");
		redoItem = new JMenuItem("Redo    Ctrl+Y");
		settingsItem = new JMenuItem("Settings");
		exitItem = new JMenuItem("Exit");
		
		undoItem.setEnabled(false);
		redoItem.setEnabled(false);
		
		helpMenu = new JMenu("Help");
		howToPlayItem = new JMenuItem("How to Play");
		aboutItem = new JMenuItem("About");
		helpMenu.add(howToPlayItem);
		helpMenu.add(aboutItem);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		newGameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (GameParameters.gameMode == Constants.HumanVsAi)
					createHumanVsAiNewGame();
				else if (GameParameters.gameMode == Constants.HumanVsHuman)
					createHumanVsHumanNewGame();
				else if (GameParameters.gameMode == Constants.AiVsAi)
					createAiVsAiNewGame();
				else if (GameParameters.gameMode == Constants.ClientServer)
					createClientServerNewGame();
				
				undoBoards.clear();
				redoBoards.clear();
				
				undoItem.setEnabled(false);
				redoItem.setEnabled(false);
				
				Board.printBoard(board.getGameBoard());
			}
		});
		
		undoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				undo();
			}
		});
		
		redoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redo();
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
						"© Created by: Christos Kormaris\nVersion " + Constants.version,
						"About", JOptionPane.INFORMATION_MESSAGE);			
				}
		});

		fileMenu.add(newGameItem);
		fileMenu.add(undoItem);
		fileMenu.add(redoItem);
		fileMenu.add(settingsItem);
		fileMenu.add(exitItem);
		
		frame.setJMenuBar(menuBar);
	}
	

	public static void undo() {
		if (!undoBoards.isEmpty()) {
			System.out.println("Undo is pressed!");
			
			// System.out.println("undo");
			// This is the undo implementation for Human VS Human mode.
			if (GameParameters.gameMode == Constants.HumanVsHuman) {
				try {
					redoBoards.push(new Board(board));
					board = undoBoards.pop(); 
					
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						List<Integer> cell = GUI.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							button.doClick();
						}
						else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							button.doClick();
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (undoBoards.isEmpty())
						undoItem.setEnabled(false);
					
					redoItem.setEnabled(true);
					
					Board.printBoard(board.getGameBoard());

				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
			
			// This is the undo implementation for Human VS AI mode.
			else if (GameParameters.gameMode == Constants.HumanVsAi) {
				
				try {
					
					redoBoards.push(new Board(board));
					board = undoBoards.pop();

					for (HumanVsAiButton button: humanVsAiButtons) {
						List<Integer> cell = GUI.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							button.doClick();
						}
						else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							button.doClick();
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (undoBoards.isEmpty())
						undoItem.setEnabled(false);
					
					redoItem.setEnabled(true);
					
					Board.printBoard(board.getGameBoard());

				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
		}
	}

	
	public static void redo() {
		if (!redoBoards.isEmpty()) {
			System.out.println("Redo is pressed!");

			// System.out.println("undo");
			// This is the undo implementation for Human VS Human mode.
			if (GameParameters.gameMode == Constants.HumanVsHuman) {
				try {
					
					undoBoards.push(new Board(board));
					board = new Board(redoBoards.pop());
					
					for (HumanVsHumanButton button: humanVsHumanButtons) {
						List<Integer> cell = GUI.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						}
						else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (redoBoards.isEmpty())
						redoItem.setEnabled(false);
					
					undoItem.setEnabled(true);

					Board.printBoard(board.getGameBoard());

					boolean isGameOver = board.isTerminal(); 
					if (isGameOver) {
						gameOver();
					}

				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
			
			else if (GameParameters.gameMode == Constants.HumanVsAi) {
				
				try {
					undoBoards.push(new Board(board));
					board = new Board(redoBoards.pop());

					for (HumanVsAiButton button: humanVsAiButtons) {
						List<Integer> cell = GUI.getBoardCellById(button.id);
						if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.X) {
							String player1Color = Constants.getColorNameByNumber(GameParameters.player1Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.X, player1Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						}
						else if (board.getGameBoard()[cell.get(0)][cell.get(1)] == Constants.O) {
							String player2Color = Constants.getColorNameByNumber(GameParameters.player2Color);
							button.setIcon(new ImageIcon(ResourceLoader.load(Constants.getIconPath(Constants.O, player2Color))));
							for (ActionListener actionListener: button.getActionListeners()) {
								button.removeActionListener(actionListener);
							}
						} else {
							button.setIcon(null);
							if (button.getActionListeners().length == 0)
								button.addActionListener(button);
						}
					}
					
					if (redoBoards.isEmpty())
						redoItem.setEnabled(false);
					
					undoItem.setEnabled(true);

					Board.printBoard(board.getGameBoard());

					boolean isGameOver = board.isTerminal(); 
					if (isGameOver) {
						gameOver();
					}
					
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.err.println("No move has been made yet!");
					System.err.flush();
				}
			}
		}
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

	public static void createHumanVsAiNewGame() {

		configureGuiStyle();
	
		if (menuBar == null)
			addMenus();
		
		MiniMaxAi aiPlayer = new MiniMaxAi(GameParameters.maxDepth1, Constants.O);
		
		if (panel != null) {
			frame.remove(panel);
			frame.revalidate();
			frame.repaint();
		}
		
		panel = new JPanel();
		frame.add(panel);
	
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();
	
		humanVsAiButtons = new HumanVsAiButton[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int i=0; i<9; i++) {
			humanVsAiButtons[i] = new HumanVsAiButton(i, aiPlayer);
			panel.add(humanVsAiButtons[i]);
		}
	
		if (frame.getKeyListeners().length == 0)
			frame.addKeyListener(gameKeyListener);
		frame.setFocusable(true);
		
		frame.setVisible(true);
		
	}


	public static void createHumanVsHumanNewGame() {

		configureGuiStyle();
	
		if (menuBar == null)
			addMenus();
		
		if (panel != null) {
			frame.remove(panel);
			frame.revalidate();
			frame.repaint();
		}
		
		panel = new JPanel();
		frame.add(panel);
	
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();
	
		humanVsHumanButtons = new HumanVsHumanButton[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int id=0; id<9; id++) {
			humanVsHumanButtons[id] = new HumanVsHumanButton(id);
			panel.add(humanVsHumanButtons[id]);
		}
	
		if (frame.getKeyListeners().length == 0)
			frame.addKeyListener(gameKeyListener);
		frame.setFocusable(true);
		
		frame.setVisible(true);
	}

	
	public static void createAiVsAiNewGame() {

		configureGuiStyle();
	
		if (menuBar == null)
			addMenus();
		
		MiniMaxAi ai1Player = new MiniMaxAi(GameParameters.maxDepth1, Constants.X);
		MiniMaxAi ai2Player = new MiniMaxAi(GameParameters.maxDepth2, Constants.O);
	
		if (panel != null) {
			frame.remove(panel);
			frame.revalidate();
			frame.repaint();
		}
		
		panel = new JPanel();
		frame.add(panel);
	
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
		
		frame.setVisible(true);
		
		playAiVsAi(ai1Player, ai2Player);
		
	}


	public static void createClientServerNewGame() {

		configureGuiStyle();
	
		if (menuBar == null)
			addMenus();
		
		Server server = new Server(GameParameters.serverPort);
		server.start();

		if (panel != null) {
			frame.remove(panel);
			frame.revalidate();
			frame.repaint();
		}
		
		panel = new JPanel();
		frame.add(panel);
		
		layout = new GridLayout(3, 3);
		panel.setLayout(layout);
		
		board = new Board();

		clientServerButtons = new ClientServerButton[9];
		
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		for (int i=0; i<9; i++) {
			clientServerButtons[i] = new ClientServerButton(i, GameParameters.clientIP, 
					GameParameters.clientPort, GameParameters.clientServerSymbol);
			panel.add(clientServerButtons[i]);
		}
		
		if (frame.getKeyListeners().length == 0)
			frame.addKeyListener(gameKeyListener);
		frame.setFocusable(true);
		
		frame.setVisible(true);
		
	}
	
	private static void aiMove(MiniMaxAi aiPlayer) {
		Move aiMove;
		if (aiPlayer.getMaxDepth() == Constants.BestResponse) {
			// Best Response Move
			BestResponse bestResponse = new BestResponse(board.getGameBoard());
			aiMove = bestResponse.findBestResponse();
		} else {
			// MiniMax AI Move
			aiMove = aiPlayer.miniMax(board);
		}
		
		makeMove(aiMove.getRow(), aiMove.getColumn(), aiPlayer.getPlayerSymbol());
		
		int ai_button_id = getIdByBoardCell(aiMove.getRow(), aiMove.getColumn());
		for (AiVsAiButton button: aiVsAiButtons) {
			if (button.id == ai_button_id) {
				button.player = aiPlayer.getPlayerSymbol();
				button.doClick();
			}
		}
	}
	
	
	private static void playAiVsAi(MiniMaxAi ai1Player, MiniMaxAi ai2Player) {
		
		while (!GUI.board.isTerminal()) {

			// AI 1 Move
			aiMove(ai1Player);
			
			// Sleep for 200 ms
			try {
				Thread.sleep(200);
				frame.paint(frame.getGraphics());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (!GUI.board.isTerminal()) {
				// AI 2 Move
				aiMove(ai2Player);
			}
			
			// Sleep for 200 ms
			try {
				Thread.sleep(200);
				frame.paint(frame.getGraphics());
			} catch (Exception e) {
				e.printStackTrace();
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
		} catch (Exception e1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e2) {
				e2.printStackTrace();	
			}
		}
	}
	
	
	public static void gameOver() {
		// System.out.println("Game Over function called!");

		if (board.getWinner() == Constants.X) {
			System.out.println("Player 1 \"X\" wins!");
			int input = JOptionPane.showConfirmDialog(null,
					"Player 1 \"X\" wins!\nPlay again?",
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
			System.out.println("Player 2 \"O\" wins!");
			int input = JOptionPane.showConfirmDialog(null,
					"Player 2 \"O\" wins!\nPlay again?",
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
		} else if (Board.isGameBoardFull(board.getGameBoard()) && board.getWinner() == Constants.EMPTY) {
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
			
			for (int i=0; i<9; i++) {
				if (button.equals(i+1+"")) {
					if (GameParameters.gameMode == Constants.HumanVsAi)
						humanVsAiButtons[i].doClick();
					if (GameParameters.gameMode == Constants.HumanVsHuman)
						humanVsHumanButtons[i].doClick();
					if (GameParameters.gameMode == Constants.ClientServer)
						clientServerButtons[i].doClick();
					break;
				} 
			}
			
			if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Z)) {
                undo();
            }
			else if (((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) &&
					(e.getKeyCode() == KeyEvent.VK_Y)) {
                redo();
            }
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
		}
	};
	
	
    // Make a move; it places a symbol on the board
	public static void makeMove(int row, int col, int player) {
		if ((player == Constants.X && GameParameters.gameMode == Constants.HumanVsAi)
			|| (GameParameters.gameMode == Constants.HumanVsHuman)) {
			undoBoards.push(new Board(board));
		}
		
		board.getGameBoard()[row][col] = player;
		// System.out.println("previous move: " + board.getLastMove());
		board.setLastMove(new Move(row, col));
		// System.out.println("this move: " + board.getLastMove());
		board.setLastPlayer(player);
		
		redoBoards.clear();
		redoItem.setEnabled(false);
	}
	
	
	public static void main(String[] args) {
		
		// These are the default values.
		// Feel free to change them, before running.
		// You can also change them later, from the GUI window.
		/*
		GameParameters.guiStyle = Constants.SystemStyle;
		GameParameters.gameMode = Constants.HumanVsAi;
		// GameParameters.gameMode = Constants.AiVsAi;
		// GameParameters.maxDepth1 = Constants.BestResponse;
		GameParameters.maxDepth1 = 4;
		// GameParameters.maxDepth2 = Constants.BestResponse;
		GameParameters.maxDepth2 = 1;
		GameParameters.player1Color = Constants.BLUE;
		GameParameters.player2Color = Constants.RED;
		GameParameters.clientServerSymbol = Constants.X;
		GameParameters.serverPort = 4000;
		GameParameters.clientIP = "127.0.0.1";
		GameParameters.clientPort = 4001;
		*/
		
		@SuppressWarnings("unused")
		GUI gui = new GUI("My TicTacToe");
		
		if (GameParameters.gameMode == Constants.HumanVsAi)
			GUI.createHumanVsAiNewGame();
		else if (GameParameters.gameMode == Constants.HumanVsHuman)
			GUI.createHumanVsHumanNewGame();
		else if (GameParameters.gameMode == Constants.AiVsAi)
			GUI.createAiVsAiNewGame();
		else if (GameParameters.gameMode == Constants.ClientServer)
			GUI.createClientServerNewGame();
	}
	
}
