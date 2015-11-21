import static org.junit.Assert.*;

import org.junit.Test;

import themightymat.game.ui.UI;

public class UiGradientTest {

	@Test
	public void uiGradientTest() {
		UI ui = new UI();
		
		double numerator = ui.getMixedFraction(0.5, false);
		double denominator = ui.getMixedFraction(0.5, true);
		
		assertEquals(1.0, numerator, 0.01); // Is the numerator correct? (allow accuracy of 0.01)
		assertEquals(2.0, denominator, 0.01); // Is denominator correct?
		
	}

}
