package ai;

import java.io.Serializable;

import enumerations.Color;
import enumerations.GameMode;
import enumerations.GuiStyle;

public class GameParameters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -302611744838922290L;

	private GameParameters() { }  // Prevents instantiation.
	
	/* Default values */
	public static GuiStyle guiStyle = GuiStyle.SYSTEM_STYLE;
	public static GameMode gameMode = GameMode.HUMAN_VS_MINIMAX_AI;
	public static int maxDepth1 = Constants.BEST_RESPONSE;
	public static int maxDepth2 = Constants.BEST_RESPONSE;
	public static Color player1Color = Color.BLUE;
	public static Color player2Color = Color.RED;
	public static int clientServerSymbol = Constants.X;
	public static int serverPort = 4000;
	public static String clientIP = "127.0.0.1";
	public static int clientPort = 4001;

}
