package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import enumerations.Color;
import enumerations.GameMode;
import enumerations.GuiStyle;
import utilities.Constants;
import utilities.GameParameters;


public class SettingsWindow extends JFrame {
	
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
	private JComboBox<String> max_depth1_drop_down;
	private JComboBox<String> max_depth2_drop_down;
	private JComboBox<String> player1_color_drop_down;
	private JComboBox<String> player2_color_drop_down;
	private JComboBox<String> client_server_symbol_drop_down;
	private JTextField server_port_text_field;
	private JTextField client_ip_text_field;
	private JTextField client_port_text_field;
	
	private JButton apply;
	private JButton cancel;
	
	private EventHandler handler;
	
	public static int width = 460;
	public static int height = 480;
	
	
	public SettingsWindow() {
		super("Settings");
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		
		handler = new EventHandler();

		GuiStyle selectedGuiStyle = GameParameters.guiStyle;
		GameMode selectedMode = GameParameters.gameMode;
		int maxDepth1 = GameParameters.maxDepth1;
		int maxDepth2 = GameParameters.maxDepth2;
		Color selectedPlayer1Color = GameParameters.player1Color;
		Color selectedPlayer2Color = GameParameters.player2Color;
		int selectedClientServerSymbol = GameParameters.clientServerSymbol;
		int serverPort = GameParameters.serverPort;
		String clientIP = GameParameters.clientIP;
		int clientPort = GameParameters.clientPort;

		guiStyleLabel = new JLabel("GUI style");
		gameModeLabel = new JLabel("Game mode");
		maxDepth1Label = new JLabel("AI 1 depth");
		maxDepth2Label = new JLabel("AI 2 depth (AiVsAi)");
		player1ColorLabel = new JLabel("Player 1 \"X\" color");
		player2ColorLabel = new JLabel("Player 2 \"O\" color");
		clientServerSymbolLabel = new JLabel("Client-Server symbol");
		serverPortLabel = new JLabel("Server port");
		clientIpLabel = new JLabel("Client IP");
		clientPortLabel = new JLabel("Client port");
		
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
		
		gui_style_drop_down = new JComboBox<String>();
		gui_style_drop_down.addItem("System style");
		gui_style_drop_down.addItem("Cross-Platform style");
		gui_style_drop_down.addItem("Nimbus style");
		
		if (selectedGuiStyle == GuiStyle.SYSTEM_STYLE)
			gui_style_drop_down.setSelectedIndex(0);
		else if (selectedGuiStyle == GuiStyle.CROSS_PLATFORM_STYLE)
			gui_style_drop_down.setSelectedIndex(1);
		else if (selectedGuiStyle == GuiStyle.NIMBUS_STYLE)
			gui_style_drop_down.setSelectedIndex(2);
			
		game_mode_drop_down = new JComboBox<String>();
		game_mode_drop_down.addItem("Human Vs Minimax AI");
		game_mode_drop_down.addItem("Human Vs Human");
		game_mode_drop_down.addItem("Minimax AI Vs Minimax AI");
		game_mode_drop_down.addItem("Client-Server");

		if (selectedMode == GameMode.HUMAN_VS_MINIMAX_AI)
			game_mode_drop_down.setSelectedIndex(0);
		else if (selectedMode == GameMode.HUMAN_VS_HUMAN)
			game_mode_drop_down.setSelectedIndex(1);
		else if (selectedMode == GameMode.MINIMAX_AI_VS_MINIMAX_AI)
			game_mode_drop_down.setSelectedIndex(2);
		else if (selectedMode == GameMode.CLIENT_SERVER)
			game_mode_drop_down.setSelectedIndex(3);
		
		max_depth1_drop_down = new JComboBox<String>();
		max_depth1_drop_down.addItem("1");
		max_depth1_drop_down.addItem("2");
		max_depth1_drop_down.addItem("3");
		max_depth1_drop_down.addItem("4");
		max_depth1_drop_down.addItem("Best Response");

		maxDepth1 = (maxDepth1 == Constants.BEST_RESPONSE) ? 4 : maxDepth1-1; 
		max_depth1_drop_down.setSelectedIndex(maxDepth1);
		
		max_depth2_drop_down = new JComboBox<String>();
		max_depth2_drop_down.addItem("1");
		max_depth2_drop_down.addItem("2");
		max_depth2_drop_down.addItem("3");
		max_depth2_drop_down.addItem("4");
		max_depth2_drop_down.addItem("Best Response");
		
		maxDepth2 = (maxDepth2 == Constants.BEST_RESPONSE) ? 4 : maxDepth2-1; 
		max_depth2_drop_down.setSelectedIndex(maxDepth2);
		
		player1_color_drop_down = new JComboBox<String>();
		player1_color_drop_down.addItem(String.valueOf(Color.BLUE));
		player1_color_drop_down.addItem(String.valueOf(Color.RED));
		player1_color_drop_down.addItem(String.valueOf(Color.BLACK));
		player1_color_drop_down.addItem(String.valueOf(Color.GREEN));
		player1_color_drop_down.addItem(String.valueOf(Color.ORANGE));
		player1_color_drop_down.addItem(String.valueOf(Color.PURPLE));
		player1_color_drop_down.addItem(String.valueOf(Color.YELLOW));
		
		if (selectedPlayer1Color == Color.BLUE)
			player1_color_drop_down.setSelectedIndex(0);
		else if (selectedPlayer1Color == Color.RED)
			player1_color_drop_down.setSelectedIndex(1);
		else if (selectedPlayer1Color == Color.BLACK)
			player1_color_drop_down.setSelectedIndex(2);
		else if (selectedPlayer1Color == Color.GREEN)
			player1_color_drop_down.setSelectedIndex(3);
		else if (selectedPlayer1Color == Color.ORANGE)
			player1_color_drop_down.setSelectedIndex(4);
		else if (selectedPlayer1Color == Color.ORANGE)
			player1_color_drop_down.setSelectedIndex(5);
		else if (selectedPlayer1Color == Color.YELLOW)
			player1_color_drop_down.setSelectedIndex(6);
		
		player2_color_drop_down = new JComboBox<String>();
		player2_color_drop_down.addItem(String.valueOf(Color.BLUE));
		player2_color_drop_down.addItem(String.valueOf(Color.RED));
		player2_color_drop_down.addItem(String.valueOf(Color.BLACK));
		player2_color_drop_down.addItem(String.valueOf(Color.GREEN));
		player2_color_drop_down.addItem(String.valueOf(Color.ORANGE));
		player2_color_drop_down.addItem(String.valueOf(Color.PURPLE));
		player2_color_drop_down.addItem(String.valueOf(Color.YELLOW));
		
		if (selectedPlayer2Color == Color.BLUE)
			player2_color_drop_down.setSelectedIndex(0);
		else if (selectedPlayer2Color == Color.RED)
			player2_color_drop_down.setSelectedIndex(1);
		else if (selectedPlayer2Color == Color.BLACK)
			player2_color_drop_down.setSelectedIndex(2);
		else if (selectedPlayer2Color == Color.GREEN)
			player2_color_drop_down.setSelectedIndex(3);
		else if (selectedPlayer2Color == Color.ORANGE)
			player2_color_drop_down.setSelectedIndex(4);
		else if (selectedPlayer2Color == Color.ORANGE)
			player2_color_drop_down.setSelectedIndex(5);
		else if (selectedPlayer2Color == Color.YELLOW)
			player2_color_drop_down.setSelectedIndex(6);
		
		client_server_symbol_drop_down = new JComboBox<String>();
		client_server_symbol_drop_down.addItem("X");
		client_server_symbol_drop_down.addItem("O");
		
		if (selectedClientServerSymbol == Constants.X)
			client_server_symbol_drop_down.setSelectedIndex(Constants.X - 1);
		else if (selectedClientServerSymbol == Constants.O)
			client_server_symbol_drop_down.setSelectedIndex(Constants.O - 1);
		
		server_port_text_field = new JTextField();
		server_port_text_field.setText(serverPort + "");
		
		client_ip_text_field = new JTextField(GameParameters.clientIP);
		client_ip_text_field.setText(clientIP);
		
		client_port_text_field = new JTextField(GameParameters.clientPort);
		client_port_text_field.setText(clientPort + "");
		
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

		guiStyleLabel.setBounds(20, 25, 250, 25);
		gameModeLabel.setBounds(20, 60, 250, 25);
		maxDepth1Label.setBounds(20, 95, 250, 25);
		maxDepth2Label.setBounds(20, 130, 250, 25);
		player1ColorLabel.setBounds(20, 165, 250, 25);
		player2ColorLabel.setBounds(20, 200, 250, 25);
		clientServerSymbolLabel.setBounds(20, 235, 250, 25);
		serverPortLabel.setBounds(20, 270, 250, 25);
		clientIpLabel.setBounds(20, 305, 250, 25);
		clientPortLabel.setBounds(20, 340, 250, 25);
		
		gui_style_drop_down.setBounds(260, 25, 160, 25);
		game_mode_drop_down.setBounds(260, 60, 160, 25);
		max_depth1_drop_down.setBounds(260, 95, 160, 25);
		max_depth2_drop_down.setBounds(260, 130, 160, 25);
		player1_color_drop_down.setBounds(260, 165, 160, 25);
		player2_color_drop_down.setBounds(260, 200, 160, 25);
		client_server_symbol_drop_down.setBounds(260, 235, 160, 25);
		server_port_text_field.setBounds(260, 270, 160, 25);
		client_ip_text_field.setBounds(260, 305, 160, 25);
		client_port_text_field.setBounds(260, 340, 160, 25);
				
		apply = new JButton("Apply");
		cancel = new JButton("Cancel");
		add(apply);
		add(cancel);
		
		int distance = 10;
		apply.setBounds((int) (width / 2) - 110 - (int) (distance / 2), 380, 100, 30);
		apply.addActionListener(handler);
		cancel.setBounds((int) (width / 2) - 10 + (int) (distance / 2), 380, 100, 30);
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
					
					GuiStyle guiStyle = 
						GuiStyle.valueOf(gui_style_drop_down.getSelectedItem().toString().toUpperCase().replace("-", "_").replace(" ", "_"));
					GameMode gameMode = 
						GameMode.valueOf(game_mode_drop_down.getSelectedItem().toString().toUpperCase().replace("-", "_").replace(" ", "_"));
					String maxDepth1String = (String) max_depth1_drop_down.getSelectedItem();
					String maxDepth2String = (String) max_depth2_drop_down.getSelectedItem();
					Color player1Color = 
						Color.valueOf(player1_color_drop_down.getSelectedItem().toString());
					Color player2Color =
							Color.valueOf(player2_color_drop_down.getSelectedItem().toString());
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
					
					int maxDepth1;
					try {
						maxDepth1 = Integer.parseInt(maxDepth1String);
					} catch (NumberFormatException e) {
						// e.printStackTrace();
						maxDepth1 = Constants.BEST_RESPONSE;
					}
					
					int maxDepth2;
					try {
						maxDepth2 = Integer.parseInt(maxDepth2String);
					} catch (NumberFormatException e) {
						// e.printStackTrace();
						maxDepth2 = Constants.BEST_RESPONSE;
					}
					
					// Change game parameters based on settings.
					GameParameters.guiStyle = guiStyle;
					GameParameters.gameMode = gameMode;
					GameParameters.maxDepth1 = maxDepth1;
					GameParameters.maxDepth2 = maxDepth2;
					GameParameters.player1Color = player1Color;
					GameParameters.player2Color = player2Color;
					GameParameters.clientServerSymbol = clientServerSymbol;
					GameParameters.serverPort = serverPort;
					GameParameters.clientIP = clientIP;
					GameParameters.clientPort = clientPort;
					
					JOptionPane.showMessageDialog(null,
							"Game settings have been changed.\nThe changes will be applied in the next new game.",
							"", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				
				catch(Exception e) {
					System.err.println("ERROR : " + e.getMessage());
				}
				
			}  // else if.
			
		}  // action performed.
		
	}  // inner class.
	
	
}  // class end.
