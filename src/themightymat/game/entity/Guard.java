package themightymat.game.entity;

import themightymat.game.InputHandler;
import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public class Guard extends Player {
	
	int ticks = 0;
	int movingDir = 0;
	int numSteps = 0;
	
	private int color = Colors.get(-1, 115, 443, 000); // colors to use for this entity
	
	Camera guardCamera;
	
	public Guard(Level level, int x, int y, int movingDir, InputHandler input, Player player) {
		super(level, x, y, input);
		if (movingDir > 0 || movingDir <= 4) {
			this.guardCamera = new Camera(level, this.x, this.y, (this.movingDir*90), 90, player, false, false);
			level.addEntity(guardCamera);
			
			this.movingDir = movingDir;
		} else { // Bad movingDir parameter
			throw new IllegalArgumentException("movingDir paremeter must be 0 < x <= 4");
		}
	}
	
	@Override
	public void tick() {
		ticks++;
		numSteps++;
		
		int xa = 0; int ya = 0;
		
		if (movingDir == 0) { // Moving Up
			ya--;
			this.guardCamera.setAngle(0);
		}
		if (movingDir == 1) { // Moving Down
			ya++;
			this.guardCamera.setAngle(180);
		}
		if (movingDir == 2) { // Right
			xa--;
			this.guardCamera.setAngle(90);
		}
		if (movingDir == 3) { // Left
			xa++; 
			this.guardCamera.setAngle(270);
		}
		
		if (hasCollided(xa, ya)) {
			if (movingDir == 0) { movingDir = 1; }
			else if (movingDir == 1) {movingDir = 0; }
			else if (movingDir == 2) {movingDir = 3; }
			else if (movingDir == 3) {movingDir = 2; }
		} else {
			move (xa, ya);
		}
		
		this.guardCamera.x = this.x + 5;
		this.guardCamera.y = this.y;
	}
	
	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 25;
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
		
		screen.render(xOffset + (modifier * flipBottom), yOffset + modifier, xTile + yTile * 30, color, flipBottom, scale);
		screen.render(xOffset + modifier - (modifier * flipBottom), yOffset + modifier, (xTile + 1) + yTile * 30, color, flipBottom, scale);
		screen.render(xOffset + (modifier * flipTop), yOffset, xTile + (yTile - 1) * 30, color, flipTop, scale);
		screen.render(xOffset + modifier - (modifier * flipTop), yOffset, (xTile + 1) + (yTile - 1) * 30, color, flipTop, scale);
	}


}
