package utility;

public class Constants {
	
	private Constants() { }  // Prevents instantiation.

	public static final double VERSION = 1.2;

    // Variables for the board values
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
	
	public static final int AI_MOVE_MILLISECONDS = 250;
	
	// AI BestResponse mode
	public static final int BEST_RESPONSE = -1;
	
	public static String getIconPath(int player, String color) {
		if (player == X) {
			return "img/X/" + color + ".png";
		} else {
			return "img/O/" + color + ".png";
		}
	}
	
}
