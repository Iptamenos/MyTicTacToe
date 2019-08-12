package gui;

import javax.swing.*;

import ai.Constants;
import ai.GameParameters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PreferencesWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4435307947189338751L;
	
	private JLabel gameModeLabel;
	private JLabel maxDepthLabel;
	private JLabel player1ColorLabel;
	private JLabel player2ColorLabel;
	
	private JComboBox<String> game_mode_drop_down;
	private JComboBox<Integer> max_depth_drop_down;
	private JComboBox<String> player1_color_drop_down;
	private JComboBox<String> player2_color_drop_down;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
	
	private GameParameters game_params; 
	
	
	public PreferencesWindow(GameParameters gp) {
		super("Preferences");
		
		// copy passed argument object to class object
		this.game_params = gp; 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();
		
		gameModeLabel = new JLabel("Game mode: ");
		maxDepthLabel = new JLabel("Minimax AI search depth: ");
		player1ColorLabel = new JLabel("Player 1 \"X\" symbol color: ");
		player2ColorLabel = new JLabel("Player 2 \"O\" symbol color: ");
		
		add(gameModeLabel);
		add(maxDepthLabel);
		add(player1ColorLabel);
		add(player2ColorLabel);
		
		gameModeLabel.setBounds(20, 25, 175, 20);
		maxDepthLabel.setBounds(20, 75, 175, 20);
		player1ColorLabel.setBounds(20, 125, 175, 20);
		player2ColorLabel.setBounds(20, 175, 175, 20);
		
		game_mode_drop_down = new JComboBox<String>();
		game_mode_drop_down.addItem("Human Vs AI");
		game_mode_drop_down.addItem("Human Vs Human");
		game_mode_drop_down.addItem("AI Vs AI");

		int selectedMode = game_params.getGameMode();
		if (selectedMode == Constants.HumanVsAi)
			game_mode_drop_down.setSelectedIndex(Constants.HumanVsAi - 1);
		else if (selectedMode == Constants.HumanVsHuman)
			game_mode_drop_down.setSelectedIndex(Constants.HumanVsHuman - 1);
		else if (selectedMode == Constants.AiVsAi)
			game_mode_drop_down.setSelectedIndex(Constants.AiVsAi - 1);
		
		max_depth_drop_down = new JComboBox<Integer>();
		max_depth_drop_down.addItem(1);
		max_depth_drop_down.addItem(2);
		max_depth_drop_down.addItem(3);
		max_depth_drop_down.addItem(4);
		max_depth_drop_down.addItem(5);
		
		int index = game_params.getMaxDepth() - 1;
		max_depth_drop_down.setSelectedIndex(index);
		
		player1_color_drop_down = new JComboBox<String>();
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.BLUE));
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.RED));
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.BLACK));
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.GREEN));
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.ORANGE));
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.PURPLE));
		player1_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.YELLOW));
		
		int selectedPlayer1Color = game_params.getPlayer1Color();
		if (selectedPlayer1Color == Constants.BLUE)
			player1_color_drop_down.setSelectedIndex(Constants.BLUE-1);
		else if (selectedPlayer1Color == Constants.RED)
			player1_color_drop_down.setSelectedIndex(Constants.RED-1);
		else if (selectedPlayer1Color == Constants.BLACK)
			player1_color_drop_down.setSelectedIndex(Constants.BLACK-1);
		else if (selectedPlayer1Color == Constants.GREEN)
			player1_color_drop_down.setSelectedIndex(Constants.GREEN-1);
		else if (selectedPlayer1Color == Constants.ORANGE)
			player1_color_drop_down.setSelectedIndex(Constants.ORANGE-1);
		else if (selectedPlayer1Color == Constants.PURPLE)
			player1_color_drop_down.setSelectedIndex(Constants.PURPLE-1);
		else if (selectedPlayer1Color == Constants.YELLOW)
			player1_color_drop_down.setSelectedIndex(Constants.YELLOW-1);
		
		player2_color_drop_down = new JComboBox<String>();
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.BLUE));
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.RED));
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.BLACK));
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.GREEN));
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.ORANGE));
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.PURPLE));
		player2_color_drop_down.addItem(Constants.getColorNameByNumber(Constants.YELLOW));
		
		int selectedPlayer2Color = game_params.getPlayer2Color();
		if (selectedPlayer2Color == Constants.BLUE)
			player2_color_drop_down.setSelectedIndex(Constants.BLUE-1);
		else if (selectedPlayer2Color == Constants.RED)
			player2_color_drop_down.setSelectedIndex(Constants.RED-1);
		else if (selectedPlayer2Color == Constants.BLACK)
			player2_color_drop_down.setSelectedIndex(Constants.BLACK-1);
		else if (selectedPlayer2Color == Constants.GREEN)
			player2_color_drop_down.setSelectedIndex(Constants.GREEN-1);
		else if (selectedPlayer2Color == Constants.ORANGE)
			player2_color_drop_down.setSelectedIndex(Constants.ORANGE-1);
		else if (selectedPlayer2Color == Constants.ORANGE)
			player2_color_drop_down.setSelectedIndex(Constants.PURPLE-1);
		else if (selectedPlayer2Color == Constants.YELLOW)
			player2_color_drop_down.setSelectedIndex(Constants.YELLOW-1);
		
		add(game_mode_drop_down);
		add(max_depth_drop_down);
		add(player1_color_drop_down);
		add(player2_color_drop_down);
		game_mode_drop_down.setBounds(220, 25, 160, 20);
		max_depth_drop_down.setBounds(220, 75, 160, 20);
		player1_color_drop_down.setBounds(220, 125, 160, 20);
		player2_color_drop_down.setBounds(220, 175, 160, 20);
		
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		apply.setBounds(80, 225, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds(220, 225, 100, 30);
		cancel.addActionListener(handler);
	}


	private class EventHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			
			if (ev.getSource() == cancel) {
				dispose();
			}
			
			else if (ev.getSource() == apply) {
				try {
					
					int game_mode = game_mode_drop_down.getSelectedIndex() + 1;
					int depth = (int) max_depth_drop_down.getSelectedItem();
					int player1_color = player1_color_drop_down.getSelectedIndex() + 1;
					int player2_color = player2_color_drop_down.getSelectedIndex() + 1;
					
					if (player1_color == player2_color) {
						JOptionPane.showMessageDialog(null,
								"Player 1 and Player 2 cannot have the same color for their checkers!!",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Change game parameters based on settings.
					game_params.setGameMode(game_mode);
					game_params.setMaxDepth(depth);
					game_params.setPlayer1Color(player1_color);
					game_params.setPlayer2Color(player2_color);
					
					JOptionPane.showMessageDialog(null,
							"Game settings have been changed.\nThe changes will be applied in the next game.",
							"", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			} // else if.
			
		} // action performed.
		
	} // inner class.
	
} // class end.
