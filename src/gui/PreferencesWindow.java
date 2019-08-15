package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ai.Constants;
import ai.GameParameters;


public class PreferencesWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4435307947189338751L;
	
	private JLabel guiStyleLabel;
	private JLabel gameModeLabel;
	private JLabel maxDepth1Label;
	private JLabel maxDepth2Label;
	private JLabel player1ColorLabel;
	private JLabel player2ColorLabel;
	private JLabel clientServerSymbolLabel;
	private JLabel serverPortLabel;
	private JLabel clientIpLabel;
	private JLabel clientPortLabel;
	
	private JComboBox<String> gui_style_drop_down;
	private JComboBox<String> game_mode_drop_down;
	private JComboBox<Integer> max_depth1_drop_down;
	private JComboBox<Integer> max_depth2_drop_down;
	private JComboBox<String> player1_color_drop_down;
	private JComboBox<String> player2_color_drop_down;
	private JComboBox<String> client_server_symbol_drop_down;
	private JTextField server_port_text_field;
	private JTextField client_ip_text_field;
	private JTextField client_port_text_field;
	
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
		setSize(450, 650);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();
		
		guiStyleLabel = new JLabel("GUI style: ");
		gameModeLabel = new JLabel("Game mode: ");
		maxDepth1Label = new JLabel("Minimax AI 1 search depth: ");
		maxDepth2Label = new JLabel("Minimax AI 2 search depth: ");
		player1ColorLabel = new JLabel("Player 1 \"X\" symbol color: ");
		player2ColorLabel = new JLabel("Player 2 \"O\" symbol color: ");
		clientServerSymbolLabel = new JLabel("Client-Server symbol: ");
		serverPortLabel = new JLabel("Server port: ");
		clientIpLabel = new JLabel("Client IP: ");
		clientPortLabel = new JLabel("Client port: ");
		
		add(guiStyleLabel);
		add(gameModeLabel);
		add(maxDepth1Label);
		add(maxDepth2Label);
		add(player1ColorLabel);
		add(player2ColorLabel);
		add(clientServerSymbolLabel);
		add(serverPortLabel);
		add(clientIpLabel);
		add(clientPortLabel);
		
		guiStyleLabel.setBounds(20, 25, 175, 20);
		gameModeLabel.setBounds(20, 75, 175, 20);
		maxDepth1Label.setBounds(20, 125, 175, 20);
		maxDepth2Label.setBounds(20, 175, 175, 20);
		player1ColorLabel.setBounds(20, 225, 175, 20);
		player2ColorLabel.setBounds(20, 275, 175, 20);
		clientServerSymbolLabel.setBounds(20, 325, 175, 20);
		serverPortLabel.setBounds(20, 375, 175, 20);
		clientIpLabel.setBounds(20, 425, 175, 20);
		clientPortLabel.setBounds(20, 475, 175, 20);
		
		gui_style_drop_down = new JComboBox<String>();
		gui_style_drop_down.addItem("System style");
		gui_style_drop_down.addItem("Cross-Platform style");
		gui_style_drop_down.addItem("Nimbus style");
		
		int selectedGuiStyle = game_params.getGuiStyle();
		if (selectedGuiStyle == Constants.SystemStyle)
			gui_style_drop_down.setSelectedIndex(Constants.SystemStyle - 1);
		else if (selectedGuiStyle == Constants.CrossPlatformStyle)
			gui_style_drop_down.setSelectedIndex(Constants.CrossPlatformStyle - 1);
		else if (selectedGuiStyle == Constants.NimbusStyle)
			gui_style_drop_down.setSelectedIndex(Constants.NimbusStyle - 1);
			
		game_mode_drop_down = new JComboBox<String>();
		game_mode_drop_down.addItem("Human Vs AI");
		game_mode_drop_down.addItem("Human Vs Human");
		game_mode_drop_down.addItem("AI Vs AI");
		game_mode_drop_down.addItem("Client-Server");

		int selectedMode = game_params.getGameMode();
		if (selectedMode == Constants.HumanVsAi)
			game_mode_drop_down.setSelectedIndex(Constants.HumanVsAi - 1);
		else if (selectedMode == Constants.HumanVsHuman)
			game_mode_drop_down.setSelectedIndex(Constants.HumanVsHuman - 1);
		else if (selectedMode == Constants.AiVsAi)
			game_mode_drop_down.setSelectedIndex(Constants.AiVsAi - 1);
		else if (selectedMode == Constants.ClientServer)
			game_mode_drop_down.setSelectedIndex(Constants.ClientServer - 1);
		
		max_depth1_drop_down = new JComboBox<Integer>();
		max_depth1_drop_down.addItem(1);
		max_depth1_drop_down.addItem(2);
		max_depth1_drop_down.addItem(3);
		max_depth1_drop_down.addItem(4);
		max_depth1_drop_down.addItem(5);

		int index = game_params.getMaxDepth1() - 1;
		max_depth1_drop_down.setSelectedIndex(index);
		
		max_depth2_drop_down = new JComboBox<Integer>();
		max_depth2_drop_down.addItem(1);
		max_depth2_drop_down.addItem(2);
		max_depth2_drop_down.addItem(3);
		max_depth2_drop_down.addItem(4);
		max_depth2_drop_down.addItem(5);

		index = game_params.getMaxDepth2() - 1;
		max_depth2_drop_down.setSelectedIndex(index);
		
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
		
		client_server_symbol_drop_down = new JComboBox<String>();
		client_server_symbol_drop_down.addItem("X");
		client_server_symbol_drop_down.addItem("O");
		
		int selectedClientServerSymbol = game_params.getClientServerSymbol();
		if (selectedClientServerSymbol == Constants.X)
			client_server_symbol_drop_down.setSelectedIndex(Constants.X - 1);
		else if (selectedClientServerSymbol == Constants.O)
			client_server_symbol_drop_down.setSelectedIndex(Constants.O - 1);
		
		server_port_text_field = new JTextField();
		server_port_text_field.setText(game_params.getServerPort() + "");
		
		client_ip_text_field = new JTextField(game_params.getClientIP());
		client_ip_text_field.setText(game_params.getClientIP());
		
		client_port_text_field = new JTextField(game_params.getClientPort());
		client_port_text_field.setText(game_params.getClientPort() + "");
		
		add(gui_style_drop_down);
		add(game_mode_drop_down);
		add(max_depth1_drop_down);
		add(max_depth2_drop_down);
		add(player1_color_drop_down);
		add(player2_color_drop_down);
		add(client_server_symbol_drop_down);
		add(server_port_text_field);
		add(client_ip_text_field);
		add(client_port_text_field);
		
		gui_style_drop_down.setBounds(220, 25, 160, 20);
		game_mode_drop_down.setBounds(220, 75, 160, 20);
		max_depth1_drop_down.setBounds(220, 125, 160, 20);
		max_depth2_drop_down.setBounds(220, 175, 160, 20);
		player1_color_drop_down.setBounds(220, 225, 160, 20);
		player2_color_drop_down.setBounds(220, 275, 160, 20);
		client_server_symbol_drop_down.setBounds(220, 325, 160, 25);
		server_port_text_field.setBounds(220, 375, 160, 25);
		client_ip_text_field.setBounds(220, 425, 160, 25);
		client_port_text_field.setBounds(220, 475, 160, 25);
		
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		apply.setBounds(80, 550, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds(220, 550, 100, 30);
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
					
					int guiStyle = gui_style_drop_down.getSelectedIndex() + 1;
					int gameMode = game_mode_drop_down.getSelectedIndex() + 1;
					int maxDepth1 = (int) max_depth1_drop_down.getSelectedItem();
					int maxDepth2 = (int) max_depth2_drop_down.getSelectedItem();
					int player1Color = player1_color_drop_down.getSelectedIndex() + 1;
					int player2Color = player2_color_drop_down.getSelectedIndex() + 1;
					int clientServerSymbol = client_server_symbol_drop_down.getSelectedIndex() + 1;
					int serverPort = Integer.parseInt(server_port_text_field.getText());
					String clientIP = client_ip_text_field.getText();
					int clientPort = Integer.parseInt(client_port_text_field.getText());
					
					if (player1Color == player2Color) {
						JOptionPane.showMessageDialog(null,
								"Player 1 and Player 2 cannot have the same color of symbols!",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Change game parameters based on settings.
					game_params.setGuiStyle(guiStyle);
					game_params.setGameMode(gameMode);
					game_params.setMaxDepth1(maxDepth1);
					game_params.setMaxDepth2(maxDepth2);
					game_params.setPlayer1Color(player1Color);
					game_params.setPlayer2Color(player2Color);
					game_params.setClientServerSymbol(clientServerSymbol);
					game_params.setServerPort(serverPort);
					game_params.setClientIP(clientIP);
					game_params.setClientPort(clientPort);
					
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
