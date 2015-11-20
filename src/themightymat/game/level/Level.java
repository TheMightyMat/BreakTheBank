package themightymat.game.level;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import themightymat.game.Game;
import themightymat.game.entity.Entity;
import themightymat.game.gfx.Screen;
import themightymat.game.level.tiles.Tile;

public class Level {
	
	private byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Point> cameras = new ArrayList<Point>();
	public static ArrayList<Point> guards = new ArrayList<Point>();
	public static ArrayList<Point> objectives = new ArrayList<Point>();
	public static ArrayList<Point> camRooms = new ArrayList<Point>();
	public static ArrayList<Point> doors = new ArrayList<Point>();
	public static ArrayList<Point> doorReleases = new ArrayList<Point>();
	public static ArrayList<Point> spawns = new ArrayList<Point>();
	private String imagePath;
	private BufferedImage image;
	private int[] levelPixels;
	
	public Level(String imagePath) {
		this.imagePath = imagePath;
		if (imagePath != null) {
			this.loadLevelFromFile(imagePath);
		} else {
			this.width = 64;
			this.height = 64;
			tiles = new byte[width * height];
			this.generateLevel();
		}
	}
	
	private void loadLevelFromFile(String imagePath) {
		try {
			this.image = ImageIO.read(this.getClass().getResource(this.imagePath));
			
			this.width = image.getWidth();
			this.height = image.getHeight();
			
			tiles = new byte[width * height];
			
			levelPixels = image.getRGB(0, 0, width, height, null, 0, width);
			for (int y = 0; y < height; y++) { // Adds points where for each tile that spawns an entity
				for (int x = 0; x < width; x++) {
					tileCheck: for (Tile t : Tile.tiles) {
						if (t != null && t.getLevelColor() == levelPixels[x + y * width]) {
							this.tiles[x + y * width] = t.getId();
							if (t.getClassName() != null) {
								if (t.getClassName() == "Camera") {
									cameras.add(new Point(x * 8, y * 8));
								} else if (t.getClassName() == "Guard") {
									guards.add(new Point(x * 8, y * 8));
								} else if (t.getClassName() == "Objective") {
									objectives.add(new Point(x * 8, y * 8));
								} else if (t.getClassName() == "Camera_Room") {
									camRooms.add(new Point(x * 8, y * 8));
								} else if (t.getClassName() == "Door") {
									doors.add(new Point(x * 8, y * 8));
								} else if (t.getClassName() == "Door_Release") {
									doorReleases.add(new Point(x * 8, y * 8));
								} else if (t.getClassName() == "Spawn") {
									spawns.add(new Point(x * 8, y * 8));
								}
							}
							break tileCheck;
						}
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void saveLevelToFile() {
		try {
			ImageIO.write(image,  "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x + y * width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
	}

	public void generateLevel() {
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				if (x * y%10 < 7) {
					tiles[x + y * width] = Tile.GRASS.getId();
				} else {
					tiles[x + y * width] = Tile.STONE.getId();
				}
			}
		}
	}
	
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0) xOffset = 0;
		if (xOffset > ((width << 3) - screen.width)) xOffset = ((width<<3) - screen.width);
		if (yOffset < 0) yOffset = 0;
		if (yOffset > ((height << 3) - screen.height)) yOffset = ((height<<3) - screen.height);
		
		screen.xOffset = xOffset;
		screen.yOffset = yOffset;
		
		for (int y = (screen.yOffset >> 3); y < (screen.yOffset + screen.height >> 3) + 1; y++) {
			for (int x = (screen.xOffset >> 3); x < (screen.xOffset + screen.width >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x<<3, y<<3);
			}
		}
	}

	public void renderEntities(Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}
	
	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height) return Tile.VOID; // Return void if not inside the world
		return Tile.tiles[tiles[x+y*width]];
	}
	
	public void tick() {
		for (Entity e : entities) {
			e.tick();
		}
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
		
	}
}
