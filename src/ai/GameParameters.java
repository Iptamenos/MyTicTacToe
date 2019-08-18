package ai;

import java.io.Serializable;

public class GameParameters implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -302611744838922290L;
	
	/* Default values */
	public static int guiStyle = Constants.SystemStyle;
	public static int gameMode = Constants.HumanVsAi;
	public static int maxDepth1 = Constants.BestResponse;
	public static int maxDepth2 = Constants.BestResponse;
	public static int player1Color = Constants.BLUE;
	public static int player2Color = Constants.RED;
	public static int clientServerSymbol = Constants.X;
	public static int serverPort = 4000;
	public static String clientIP = "127.0.0.1";
	public static int clientPort = 4001;

}
