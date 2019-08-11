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


public class GUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1666862630121809768L;
	
	public static JPanel panel;
	public static GridLayout layout;
	public static GameParameters game_params = new GameParameters();

	private HumanVsHumanButton[] humanVsHumanButtons;
	private HumanVsAiButton[] humanVsAiButtons;
	private Board board;
	private int turn;
	private int winner;
	
	// Menu bars and items
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newGameItem;
	private JMenuItem preferencesItem;
	private JMenuItem exitItem;
	
	private JMenu helpMenu;
	private JMenuItem howToPlayItem;
	private JMenuItem aboutItem;

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
			addMenus();
	}

	public HumanVsHumanButton[] getHumanVsHumanButtons() {
		return humanVsHumanButtons;
	}

	public void setHumanVsHumanButtons(HumanVsHumanButton[] humanVsHumanButtons) {
		this.humanVsHumanButtons = humanVsHumanButtons;
	}
	
	public HumanVsAiButton[] getHumanVsAiButtons() {
		return humanVsAiButtons;
	}

	public void setHumanVsAiButtons(HumanVsAiButton[] humanVsAiButtons) {
		this.humanVsAiButtons = humanVsAiButtons;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getTurn() {
		return turn;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	private void addMenus() {
		// Adding the menu bar
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		newGameItem = new JMenuItem("New Game");
		preferencesItem = new JMenuItem("Preferences");
		exitItem = new JMenuItem("Exit");
		fileMenu.add(newGameItem);
		fileMenu.add(preferencesItem);
		fileMenu.add(exitItem);
		
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
					createHumanVsHumanNewGame();
				else if (game_params.getGameMode() == Constants.HumanVsAi)
					createHumanVsAiNewGame();
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
	public List<Integer> getBoardCellById(int id) {
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
		turn = Constants.X;
		winner = Constants.EMPTY;

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
		turn = Constants.X;
		winner = Constants.EMPTY;

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

	public static void main(String[] args) {
		GUI gui = new GUI();
		if (game_params.getGameMode() == Constants.HumanVsAi)
			gui.createHumanVsAiNewGame();
		else if (game_params.getGameMode() == Constants.HumanVsHuman)
			gui.createHumanVsHumanNewGame();
	}
	
}
