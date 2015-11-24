package themightymat.game.ui;

import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;

public class UI {
	
	private Screen screen;
	
	public UI(Screen screen) {
		this.screen = screen;
	}
	
	public void drawLine(int xStart, int yStart, int xFinish, int yFinish) {
		
//		if (xFinish < xStart) { // Line going "backwards"
//			int xFinishTemp = xFinish; int yFinishTemp = yFinish;
//			xFinish = xStart;
//			xStart = xFinishTemp;
//			yFinish = yStart;
//			yStart = yFinishTemp;
//		}
		
		double gradient = ((double) yFinish - (double) yStart) / ((double) xFinish - (double) xStart); // decimal gradient of line
		int octant = 0;
		
		if (xFinish >= xStart && gradient < 1 && gradient >= 0) {				//  \2|1/
			octant = 0;															//  3\|/0
		} else if (xFinish >= xStart && gradient > 1) {							//  --+--
			octant = 1;															//  4/|\7
		} else if (xFinish < xStart && gradient > 1) {							//  /5|6\
			octant = 2;
		} else if (xFinish < xStart && gradient < 1 && gradient > 0) {
			octant = 3;
		} else if (xFinish < xStart && gradient < 0 && gradient >= -1) {	
			octant = 4;
		} else if (xFinish < xStart && gradient < -1) {
			octant = 5;
		} else if (xFinish >= xStart && gradient < -1) {
			octant = 6;
		} else if (xFinish >= xStart && gradient <= 0 && gradient >= -1) {
			octant = 7;
		} else {
			octant = 8;
		}
		
		if (xFinish >= xStart) {
			for (int xPos = xStart; xPos <= xFinish; xPos++) {
				int yPos = (int) Math.round(gradient*(xPos - xStart) + yStart);
				
				if (!(yPos >= screen.height) && !(yPos <= 0)) {
					if (!(xPos >= screen.width) && !(xPos <= 0)) {
						screen.pixels[xPos + yPos * screen.width] = Colors.get(500);
					}
				}
			}
		} else {
			for (int xPos = xStart; xPos >= xFinish; xPos--) {
				int yPos = (int) Math.round(gradient*(xPos - xStart) + yStart);
				
				if (!(yPos >= screen.height) && !(yPos <= 0)) {
					if (!(xPos >= screen.width) && !(xPos <= 0)) {
						screen.pixels[xPos + yPos * screen.width] = Colors.get(500);
					}
				}
			}
		}
		
	}
	
	public void deathScreen(Screen screen) {
		for (int xPixel = 0; xPixel < screen.width; xPixel++) {
			for (int yPixel = 0; yPixel < screen.height; yPixel++) {
				int colorTint = 200;
				
				screen.pixels[xPixel + yPixel * screen.width] = (screen.pixels[xPixel + yPixel * screen.width] + Colors.get(colorTint) > 215) ? Colors.get(colorTint)*2 : (screen.pixels[xPixel + yPixel * screen.width] + Colors.get(colorTint));
			}
		}
	}
}
