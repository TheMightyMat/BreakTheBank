package themightymat.game.level.tiles;

public class BasicSolidTile extends BasicTile {

	public BasicSolidTile(int id, int x, int y, int tileColor, int levelColor, String className) {
		super(id, x, y, tileColor, levelColor, className);
		this.solid = true;
	}

}
