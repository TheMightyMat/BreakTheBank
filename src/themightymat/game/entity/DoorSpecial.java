package themightymat.game.entity;

import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;
import themightymat.game.level.tiles.Tile;

public class DoorSpecial extends Entity {
	
	private int color = Colors.get(500, 222, -1, -1);
	
	private boolean disabled;
	private int x;
	private int y;
	
	public DoorSpecial(Level level, int x, int y, boolean disabled) {
		super(level);
		this.x = x;
		this.y = y;
		this.disabled = disabled;
	}

	@Override
	public void tick() {
		Tile occupyingTile = level.getTile((this.x) >> 3, (this.y) >> 3);
		if (!disabled) {
			occupyingTile.setSolid(true); // Makes the door solid if closed
		} else {
			occupyingTile.setSolid(false); // Makes the door not solid if open
		}
	}

	@Override
	public void render(Screen screen) {
		if (!disabled) { // Don't render this tile if destroyed
			int xTile = 9;
			int yTile = 0;
			screen.render(this.x, this.y, xTile + yTile * 30, color);
		}
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public boolean getCanBeDisabledValue() {
		return true;
	}
	
	public boolean isDisabled() {
		return this.disabled;
	}
	
	public void setDisabled(boolean value) {
		this.disabled = value;
	}


}
