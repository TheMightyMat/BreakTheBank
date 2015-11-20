package themightymat.game.entity;

import java.awt.Point;

import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public abstract class Entity {
	
	public int x, y;
	protected Level level;
	
	public Point maxViewRight;
	public Point maxViewLeft;
	
	public Entity(Level level) {
		init(level);
	}
	
	public final void init(Level level) {
		this.level = level;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen);
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract boolean getCanBeDisabledValue();
	
	public abstract void setDisabled(boolean value);
}
