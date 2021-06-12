package utility;

import enumeration.Color;
import enumeration.GameMode;
import enumeration.GuiStyle;

public class GameParameters {
	
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
