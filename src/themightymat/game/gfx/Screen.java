package themightymat.game.gfx;

public class Screen {
	
	public static final int MAP_WIDTH = 64;
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;
	
	public int[] pixels;

	public int xOffset = 0;
	public int yOffset = 0;
	
	public int width;
	public int height;
	
	public SpriteSheet sheet;
	
	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		
		pixels = new int[width*height];
	}
	
	public void render(int xPos, int yPos, int tile, int color) {
		render(xPos, yPos, tile, color, 00, 1);
	}
	
	public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;
		
		boolean xMirror = ( mirrorDir & BIT_MIRROR_X) > 0;
		boolean yMirror = (mirrorDir & BIT_MIRROR_Y) > 0;
		
		int scaleMap = scale - 1;
		int xTile = tile%30; // Returns x and y position of the tile
		int yTile = tile/30; // 30 is the number of tiles wide/tall the sprite sheet is.
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width; // Shifts the number by 8, for 8 pixels per tile (or 3 places in binary)
		for (int y = 0; y < 8; y++) {
			int ySheet = y; // Used to flip/mirror the image
			if (yMirror) ySheet = 7-y;
			
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3));
			for (int x = 0; x < 8; x++) {
				int xSheet = x;
				if (xMirror)
					xSheet = 7-x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
				int col = (color >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
				if (col < 255) {
					for (int yScale = 0; yScale < scale; yScale++) {
						if (yPixel + yScale < 0 || yPixel + yScale >= height) // Don't draw pixel if it is off the screen
							continue;
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= width) // Don't draw pixel if it is off the screen
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}

					
				}
			}
		}
		
	}
}
