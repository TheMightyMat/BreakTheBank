package themightymat.game.entity;

import java.awt.Point;

import themightymat.game.Game;
import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public class Camera extends Entity {

	public int x, y;
	private int angle;
	private int fov;
	private int color = Colors.get(500, 222, 000, 333);
	private Player player;
	
	public boolean canSeePlayer = false;
	
	boolean shouldRender;
	public boolean canBeDisabled;
	
	public Camera(Level level, int x, int y, int angle, int fov, Player player, boolean shouldRender, boolean canBeDisabled) {
		super(level);
		if (angle >= 0 && angle <= 360) {
			this.x = x;
			this.y = y;
			this.angle = angle;
			this.fov = fov;
			this.player = player;
			this.shouldRender = shouldRender;
			this.canBeDisabled = canBeDisabled;
		} else {
			throw new IllegalArgumentException("Angle must be within 0 and 360");
		}
	}
	
	@Override
	public void tick() {
		if (!canBeDisabled || !Game.camerasDisabled) {
			double xDiff = this.x - player.getX();
			double yDiff = this.y - player.getY();
			
			double playerAngle = Math.toDegrees(Math.atan2(xDiff, yDiff)); // Degrees the player is from the camera
			if (playerAngle < 0) {
				playerAngle = 360 + playerAngle;
			}
			double playerDist = Math.sqrt((this.x - player.x)*(this.x - player.x) + (this.y - player.y)*(this.y - player.y)); // Distance the player is from the camera
			
			this.maxViewLeft = new Point((int) (50 * Math.sin(Math.toRadians(angle + fov/2 + 180))) + this.x, (int) (50 * Math.cos(Math.toRadians(angle + fov/2 + 180))) + this.y);
			this.maxViewRight = new Point((int) (50 * Math.sin(Math.toRadians(angle - fov/2 + 180))) + this.x, (int) (50 * Math.cos(Math.toRadians(angle - fov/2 + 180))) + this.y);
			
			double minAngle = angle + (fov/2);
			double maxAngle = angle - (fov/2);
			
			boolean isLessThanZero = false;
			boolean isGreaterThan360 = false;
			
			if (maxAngle < 0) {
				maxAngle = 360 + maxAngle;
				isLessThanZero = true;
			} else if (minAngle > 360) {
				minAngle = (minAngle%360);
				isGreaterThan360 = true;
			}
			
//			System.out.println(minAngle + ", " + maxAngle + ", " + playerAngle + ", " + isLessThanZero + ", " + isGreaterThan360 + ", " + playerDist);
			
			if (playerDist < 50) {
				if (playerAngle < minAngle && playerAngle > maxAngle && !isLessThanZero) {
					player.detected = true;
					canSeePlayer = true;
				} else if (playerAngle < minAngle && isLessThanZero || playerAngle > maxAngle && isLessThanZero) {
					player.detected = true;
					canSeePlayer = true;
				} else if ((playerAngle < minAngle && isGreaterThan360) || (playerAngle > maxAngle && isGreaterThan360)) {
					player.detected = true;
					canSeePlayer = true;
				} else {
					player.detected = false;
					canSeePlayer = false;
				}
			}
			if (playerDist > 50) {
				canSeePlayer = false;
			}
		}
	}

	@Override
	public void render(Screen screen) {
		if (shouldRender) {
			int xTile = 6;
			int yTile = 0;
			screen.render(this.x, this.y, xTile + yTile * 30, color);
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getAngle() {
		return this.angle;
	}
	
	public void setAngle(int angle) {
		this.angle = angle;
	}

	@Override
	public boolean getCanBeDisabledValue() {
		return canBeDisabled;
	}

	@Override
	public void setDisabled(boolean value) {
		// TODO Auto-generated method stub
	}

}
