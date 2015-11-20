package themightymat.game.level.tiles;

import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Screen;
import themightymat.game.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 30, 28, Colors.get(000, -1, -1, -1), 0xFF000000, null); // Id, sprite sheet x, sprite sheet y, color, level color
	public static final Tile STONE = new BasicSolidTile(1, 2, 0, Colors.get(-1, 333, -1, -1), 0xFF555555, null);
	public static final Tile GRASS = new BasicTile(2, 3, 0, Colors.get(-1,  131,  141,  -1), 0xFF00FF00, null);
	public static final Tile FLOOR = new BasicTile(3, 5, 0, Colors.get(-1, -1, 222, 444), 0xFF000011, null);
	public static final Tile CAMERA = new BasicTile(4, 5, 0, Colors.get(-1, -1, -1, -1), 0xFFFFFFFF, "Camera");
	public static final Tile OBJECTIVE = new BasicTile(5, 5, 0, Colors.get(-1, -1, 222, 444), 0xFF00FFFF, "Objective");
	public static final Tile GUARD = new BasicTile(6, 5, 0, Colors.get(-1, -1, 222, 444), 0xFFFF0000, "Guard");
	public static final Tile CAMERA_ROOM = new BasicTile(7, 5, 0, Colors.get(-1, -1, 222, 444), 0xFFFF00FF, "Camera_Room");
	public static final Tile DOOR = new BasicTile(8, 5, 0, Colors.get(-1, -1, 222, 444), 0xFFFF9900, "Door");
	public static final Tile DOOR_RELEASE = new BasicTile(9, 5, 0, Colors.get(-1, -1, 222, 444), 0xFFFF99FF, "Door_Release");
	public static final Tile SPAWN = new BasicTile(10, 11, 0, Colors.get(522, 555, -1, -1), 0xFFFFD800, "Spawn");
	
	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	protected String className;
	private int levelColor;
	
	public Tile (int id, boolean isSolid, boolean isEmmiter, int color, String className) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate Tile ID on " + id);
		this.solid = isSolid;
		this.emitter = isEmmiter;
		this.levelColor = color;
		this.className = className;
		this.className = className;
		tiles[id] = this; // Add this tile into the tiles array
	}
	
	public byte getId() {
		return id;
	}
	
	public boolean isEmitter() {
		return emitter;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);

	public int getLevelColor() {
		return levelColor;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setSolid(boolean value) {
		this.solid = value;
	}

}