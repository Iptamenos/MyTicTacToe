package ai;

public class Constants {
	
	private Constants() { }  // Prevents instantiation.

	public static final double VERSION = 1.2;

    // Variables for the board values
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;

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
	
	// AI BestResponse mode
	public static final int BEST_RESPONSE = -1;

	public static final String getColorNameByNumber(int number) {
		switch (number) {
			case 1:
				return "Blue";
			case 2:
				return "Red";
			case 3:
				return "Black";
			case 4:
				return "Green";
			case 5:
				return "Orange";
			case 6:
				return "Purple";
			case 7:
				return "Yellow";
			default:
				return "Blue";
		}
	}
	
	public static String getIconPath(int player, String color) {
		if (player == X) {
			return "img/X/" + color + ".png";
		} else {
			return "img/O/" + color + ".png";
		}
	}
	
}
