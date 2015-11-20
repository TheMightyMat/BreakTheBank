package themightymat.game.level.tiles;

import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public class BasicTile extends Tile {
	
	protected int tileId;
	protected int tileColor;
	
	public BasicTile(int id, int x, int y, int tileColor, int levelColor, String className) {
		super(id, false, false, levelColor, className);
		this.tileId = x + y;
		this.tileColor = tileColor;
		this.solid = false;
	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor);
	}
	
}
