package themightymat.game.entity;

import themightymat.game.Game;
import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public class Objective extends Entity {
	
	private int x;
	private int y;
	private int pickupDist;
	private Player player;
	private int color = Colors.get(332, 020, 000, 000);
	
	public boolean destroyed = false;
	
	public Objective(Level level, int x, int y, int pickupDist, Player player) {
		super(level);
		this.x = x;
		this.y = y;
		this.pickupDist = pickupDist;
		this.player = player;
	}

	@Override
	public void tick() {
		if (!destroyed) {
			double playerDist = Math.sqrt((this.x - player.x)*(this.x - player.x) + (this.y - player.y)*(this.y - player.y));
			if (playerDist < pickupDist) {
				Game.money += 100;
				this.destroy();
			}
		}
		
	}

	@Override
	public void render(Screen screen) {
		if (!destroyed) {
			int xTile = 7;
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
	
	public void destroy() {
		destroyed = true;
	}

	@Override
	public boolean getCanBeDisabledValue() {
		return false;
	}

	@Override
	public void setDisabled(boolean value) {
		// TODO Auto-generated method stub
		
	}

}
