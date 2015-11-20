package themightymat.game.entity;

import themightymat.game.level.Level;
import themightymat.game.level.tiles.Tile;

public abstract class Mob extends Entity {
	
	protected String name;

	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;
	
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move(int xa, int ya) {
		numSteps++;
		if(!hasCollided(xa, ya)) {
			if (ya < 0) movingDir = 0; // Moving up
			if (ya > 0) movingDir = 1; // Moving down
			if (xa < 0) movingDir = 2; // Moving Left
			if (xa > 0) movingDir = 3; // Moving right
			
			x += xa * speed;
			y += ya * speed;
		} else if (!hasCollided(xa, 0)) {
			if (xa < 0) movingDir = 2; // Moving Left
			if (xa > 0) movingDir = 3; // Moving right
			
			x += xa * speed;
		} else if(!hasCollided(0, ya)) {
			if (ya < 0) movingDir = 0; // Moving up
			if (ya > 0) movingDir = 1; // Moving down
			
			y += ya * speed;
		}
	}
	
	public abstract boolean hasCollided(int xa, int ya);
	
	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null) { 
			return false; 
		}
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		if (!lastTile.equals(newTile) && newTile.isSolid()) {
			return true;
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
}
