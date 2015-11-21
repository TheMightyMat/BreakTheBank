package themightymat.game.entity;

import themightymat.game.Game;
import themightymat.game.InputHandler;
import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public class Player extends Mob {
	
	private InputHandler input;
	private int color = Colors.get(-1, 000, 111, 555); // colors to use for this entity
	private int ticks = 0;
	
	public boolean detected = false;
	
	public Player(Level level, int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1);
		this.input = input;
	}

	@Override
	public void tick() {
		ticks++;
		
		int xa = 0;
		int ya = 0;
		
		if (input.up.isPressed()) {ya--;}
		if (input.down.isPressed()) {ya++;}
		if (input.left.isPressed()) {xa--;}
		if (input.right.isPressed()) {xa++;}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else {
			isMoving = false;
		}
		
		boolean detectedThisTick = false; // Stores if the player was detected this tick
		for (Camera camera : Game.cameras) {
			if (camera.canSeePlayer) {
				detectedThisTick = true;
				detected = true;
				break;
			}
		}
		if (!detectedThisTick) {
			detected = false;
		}
	}
	
	
	@Override
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0; // How many pixels in to start the hitbox from the middle
		int xMax = 7; // Where to end the hitbox from the middle
		int yMin = -2; // Start the hitbox from the middle
		int yMax = 7; // Finish the hitbox from the middle
		
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 26;
		int walkingSpeed = 4;
		int flipTop = (numSteps >> walkingSpeed) & 1; // Gives a number between 0 and 1
		int flipBottom = (numSteps >> walkingSpeed) & 1;
		
		if (movingDir == 0) { // Moving up
			xTile += 2;
		}
		if (movingDir > 1) { // Moving right
			xTile += 4 + ((numSteps >> walkingSpeed) & 1) * 2;
			flipTop = (movingDir - 1) % 2; // Should return 0 or 1
		}
		
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2-4;
		
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + yTile * 30, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + yTile * 30, color, flipTop, scale);
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + (yTile + 1) * 30, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + (yTile + 1) * 30, color, flipBottom, scale);
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
		return false;
	}

	@Override
	public void setDisabled(boolean value) {
		// TODO Auto-generated method stub
		
	}
	
	public int getPixelWidth() {
		return 8;
	}
	
	public int getPixelHeight() {
		return 8;
	}

	
}
