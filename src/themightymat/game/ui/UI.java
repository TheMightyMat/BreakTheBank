package themightymat.game.ui;

import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;

public class UI {
	
	private Screen screen;
	private int count;
	
	public UI(Screen screen) {
		this.screen = screen;
	}
	
	public void drawLine(int xStart, int yStart, int xFinish, int yFinish) {
		double gradient = 0;
		if (xFinish != xStart && yFinish != yStart) {
			gradient = (yFinish - yStart) / (xFinish - xStart); // decimal gradient of line
		}
		
		double pixelsAcross = xFinish - xStart;
		double pixelsUp = yFinish - yStart;
		
		double acrossTemp = pixelsAcross;
		double HCF = pixelsUp;
		
		if (acrossTemp > 0) {
			while (acrossTemp > 0) {
				double temp = acrossTemp;
				acrossTemp = HCF % acrossTemp;
				HCF = temp;
			}
			
			pixelsUp /= HCF;
			pixelsAcross /= HCF;
		}
		
		double repeats = (xFinish - xStart) / (pixelsAcross);
		if (xFinish - xStart == 0 || xFinish + xStart == 0) {
			repeats = (yFinish - yStart) / (pixelsUp);
		}
		
		int count = 0;
		
		int xPos = xStart;
		int yPos = yStart;
		
		while (count <= repeats) {
			screen.pixels[xPos + yPos * screen.width] = Colors.get(500);
			xPos += pixelsAcross;
			yPos += pixelsUp;
			count++;
		}
		
	}
	
	public void deathScreen(Screen screen) {
		for (int xPixel = 0; xPixel < screen.width; xPixel++) {
			for (int yPixel = 0; yPixel < screen.height; yPixel++) {
				if (screen.pixels[xPixel + yPixel * screen.width] + Colors.get(200) > 215) {
					screen.pixels[xPixel + yPixel * screen.width] = 215;
				} else {
					screen.pixels[xPixel + yPixel * screen.width] = screen.pixels[xPixel + yPixel * screen.width] + Colors.get(200);
				}
//				screen.pixels[xPixel + yPixel * screen.width] = ((screen.pixels[xPixel + yPixel * screen.width] + Colors.get(100)) > 215) ? 215 : screen.pixels[xPixel + yPixel * screen.width] + Colors.get(300);
			}
		}
	}
}
