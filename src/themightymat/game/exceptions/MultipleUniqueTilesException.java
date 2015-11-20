package themightymat.game.exceptions;

public class MultipleUniqueTilesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MultipleUniqueTilesException() { super(); }
	public MultipleUniqueTilesException(String message) { super(message); }
}
