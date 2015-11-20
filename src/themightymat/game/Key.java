package themightymat.game;

public class Key {
	private boolean pressed = false;
	
	public void toggle(boolean isPressed) {
		pressed = isPressed;
	}
	
	public boolean isPressed() {
		return pressed;
	}
}
