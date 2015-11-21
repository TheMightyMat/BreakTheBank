package themightymat.game.ui;

import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;

public class UI {
	
	private Screen screen;
	
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
}
