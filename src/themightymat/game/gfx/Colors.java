package themightymat.game.gfx;

public class Colors {
	
	public static int get(int color1, int color2, int color3, int color4) {
		return (get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1); // Each number is shifted by 8 in the binary, to give one long int
	}

	public static int get(int color) {
		if (color < 0) return 255; // Format for color int is RGB, eg 505 would be 255, 0, 255
		int r = color/100%10; // First digit of number
		int g = color/10%10; // Second digit
		int b = color%10; // Third
		
		return r*36 + g * 6 + b;
	}
}
