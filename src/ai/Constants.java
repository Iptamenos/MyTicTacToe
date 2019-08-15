package ai;

public class Constants {
	
    // Variables for the board values
	public static final int EMPTY = 0;
	public static final int X = 1;
	public static final int O = 2;

	// GUI styles
	public static final int SystemStyle = 1;
	public static final int CrossPlatformStyle = 2;
	public static final int NimbusStyle = 3;
	
	// Colors
	public static final int BLUE = 1;
	public static final int RED = 2;
	public static final int BLACK = 3;
	public static final int GREEN = 4;
	public static final int ORANGE = 5;
	public static final int PURPLE = 6;
	public static final int YELLOW = 7;
	
	// Game mode
	public static final int HumanVsAi = 1;
	public static final int HumanVsHuman = 2;
	public static final int AiVsAi = 3;
	public static final int ClientServer = 4;

	public static final String getColorNameByNumber(int number) {
		switch (number) {
			case 1:
				return "BLUE";
			case 2:
				return "RED";
			case 3:
				return "BLACK";
			case 4:
				return "GREEN";
			case 5:
				return "ORANGE";
			case 6:
				return "PURPLE";
			case 7:
				return "YELLOW";
			default:
				return "BLUE";
		}
	}
	
}
