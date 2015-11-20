package themightymat.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener{
	
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public List<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	@Override
	public void keyPressed(KeyEvent e) { // When key is pressed
		toggleKey(e.getKeyCode(), true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) { // When key is released
		toggleKey(e.getKeyCode(), false);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {up.toggle(isPressed);} // Key bindings
		if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {down.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {left.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {right.toggle(isPressed);}
	}

}
