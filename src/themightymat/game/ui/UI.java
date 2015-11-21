package themightymat.game.ui;

public class UI {
	public UI() {
		// TODO - Improve UI coding
	}
	
	public void drawLine(int xStart, int yStart, int xFinish, int yFinish) {
		double gradient = (yFinish - yStart) / (xFinish - xStart); // decimal gradient of line
		
		double pixelsUp = getMixedFraction(gradient, false);
		double pixelsAcross = getMixedFraction(gradient, true);
		
	}
	
	public double getMixedFraction(double value, boolean giveDenominator) {
		int whole = (int) Math.floor(value);
		double denominator = Math.pow(10, String.valueOf(whole).length());
		double numerator = (value - whole) * denominator;
		
		double denomTemp = denominator;
		double HCF = numerator; // Highest Common Factor
		
		while (denomTemp > 0)
	    {
	        double temp = denomTemp;
	        denomTemp = HCF % denomTemp; // % is remainder
	        HCF = temp;
	    }
		
		denominator /= HCF;
		numerator /= HCF;
		
		if (giveDenominator) {
			return denominator;
		} else {
			return numerator;
		}
	}
}
