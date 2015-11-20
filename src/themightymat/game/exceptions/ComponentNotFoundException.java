package themightymat.game.exceptions;

public class ComponentNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ComponentNotFoundException() { super(); }
	public ComponentNotFoundException(String message) { super(message); }
}
