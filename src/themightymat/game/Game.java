package themightymat.game;



/* ~ ~ ~ ~ ~ GAME VERSION = 00.03 ~ ~ ~ ~ ~*/



import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import themightymat.game.entity.Camera;
import themightymat.game.entity.CameraRoom;
import themightymat.game.entity.Door;
import themightymat.game.entity.DoorRelease;
import themightymat.game.entity.Guard;
import themightymat.game.entity.Objective;
import themightymat.game.entity.Player;
import themightymat.game.gfx.Colors;
import themightymat.game.gfx.Font;
import themightymat.game.gfx.Screen;
import themightymat.game.gfx.SpriteSheet;
import themightymat.game.level.Level;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH;
	public static final int SCALE = 4; // No higher than 6
	public static final String NAME = "Game";
	
	public static final int DETECTION_RATE = 2;
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0; // Total ticks since program start
	
	public static int detection = 0; // Player detection
	public static int money = 0; // Player money and score
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colors = new int[6*6*6]; // 6 different shades used for R, G and B
	
	private Screen screen;
	public InputHandler input;
	public static Level level;
	public static Player player;
	public Camera camera;
	public Guard guard;
	
	public List<Player> players = new ArrayList<Player>();
	public static List<Camera> cameras = new ArrayList<Camera>();
	
	public static boolean camerasDisabled = false;
	
	private int playerStartingX = 30;
	private int playerStartingY = 30;
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	} 
	
	public void init() {
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5); // Make the "red", which only has a value of 6 here, into an actual red
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					
					colors[index++] = rr << 16 | gg << 8 | bb; // Shifts the numbers along by 8 bits, giving 1 long int.
				}
			}
		}
		
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);
		level = new Level("/level2.png");
		
		spawnLoop : for (Point newSpawn : Level.spawns) { // Iterates through spawn blocks (should only be 1, if none it will not execute)
			playerStartingX = (int) newSpawn.getX(); // Set player starting co-ordinates
			playerStartingY = (int) newSpawn.getY();
			break spawnLoop; // Only find the first (top left) spawn
		}
		
		player = new Player(level, playerStartingX, playerStartingY, input);
//		camera = new Camera(level, 80, 80, 0, 90, player, true, true);	// Developer Testing
		level.addEntity(player);
//		level.addEntity(camera);										// Developer Testing
//		cameras.add(camera);											// Developer Testing
//		guard = new Guard(level, 120, 120, 0, input, player);			// Developer Testing
//		level.addEntity(guard);											// Developer Testing
		
		Random randomGenerator = new Random();
		
		for (Point newCamera : Level.cameras) { // For every camera in the level
			double newCameraX = newCamera.getX();
			double newCameraY = newCamera.getY();
			Camera tempCam = new Camera(level, (int) newCameraX,  (int) newCameraY, randomGenerator.nextInt(360), 90, player, true, true);
			level.addEntity(tempCam); // Add the camera
			cameras.add(tempCam);
		} for (Point newGuard : Level.guards) { // For every guard in the level
			double newGuardX = newGuard.getX();
			double newGuardY = newGuard.getY();
			level.addEntity(new Guard(level, (int) newGuardX, (int) newGuardY, randomGenerator.nextInt(4), input, player));
		} for (Point newObjective : Level.objectives) { // For every objective in the level
			double newObjectiveX = newObjective.getX();
			double newObjectiveY = newObjective.getY();
			level.addEntity(new Objective(level, (int) newObjectiveX, (int) newObjectiveY, 10, player));
		} for (Point newCameraRoom : Level.camRooms) { // For every objective in the level
			double newCameraRoomX = newCameraRoom.getX();
			double newCameraRoomY = newCameraRoom.getY();
			level.addEntity(new CameraRoom(level, (int) newCameraRoomX, (int) newCameraRoomY, 10, player));
		} for (Point newDoor : Level.doors) {
			double newDoorX = newDoor.getX();
			double newDoorY = newDoor.getY();
			level.addEntity(new Door(level, (int) newDoorX, (int) newDoorY, false));
		} for (Point newDoorRelease : Level.doorReleases) {
			double newDoorReleaseX = newDoorRelease.getX();
			double newDoorReleaseY = newDoorRelease.getY();
			level.addEntity(new DoorRelease(level, (int) newDoorReleaseX, (int) newDoorReleaseY, 10, player));
		}
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public void run() {
		long lastTime = System.nanoTime(); // Starting time
		double nsPerTick = 1000000000D/60D; // Number of nanoseconds 1 tick should last
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis(); // Used for fps calculation
		double delta = 0; // Number of nanoseconds passed by so far
		
		init();
		
		while (running) {
			long timeNow = System.nanoTime();
			delta += (timeNow - lastTime)/nsPerTick; // Should be greater than 1 if time passed is greater than the time for one tick to last
			lastTime = timeNow;
			
			boolean renderFrames = false; // false for capped fps, true for unlimited
			while(delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				renderFrames = true;
			}
			
			if (renderFrames) {
				frames++;
				render();
			}
			
			if (System.currentTimeMillis() - lastTimer >= 1000) { // If one second has passed
				lastTimer += 1000; // Add one second to lastTimer, so this if works on next iteration
				System.out.println(ticks + " ticks," + frames + " frames"); // Debugging, display frames and ticks in that second.
				frames = 0;
				ticks = 0; // Reset frames and ticks
			}
			
			
		}
		
	}
	
	
	public void tick() {
		tickCount++;
		
		if (player.detected) {
			System.out.println("Hello");
			detection += DETECTION_RATE;
			if (detection > 100) { detection = 100; }
		}
		
		level.tick();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // Triple Buffering
			return;
		}
		
		int xOffset = player.x - (screen.width/2);
		int yOffset = player.y - (screen.height/2);
		
		level.renderTiles(screen, xOffset, yOffset);
		level.renderEntities(screen);
		
		Font.render(Integer.toString(detection), screen, screen.xOffset + 5, screen.yOffset + 5, Colors.get(000, 000, 555, 555), 1);
		Font.render("$" + Integer.toString(money), screen, screen.xOffset + 5, screen.yOffset + 15, Colors.get(000, 000, 555, 555), 1);
		
		for (int y=0; y < screen.height; y++) {
			for (int x=0; x < screen.width; x++) {
				int colorCode = screen.pixels[x+y * screen.width];
				if (colorCode < 255) pixels[x+y * WIDTH] = colors[colorCode];
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		for (Camera entity : cameras) {
			g2.setStroke(new BasicStroke(SCALE));
			g2.setColor(Color.RED.brighter());
			
			if (!entity.getCanBeDisabledValue() || !camerasDisabled) {
				g2.drawLine((entity.getX() - screen.xOffset) * SCALE, (int) (entity.getY() - screen.yOffset) * SCALE, (int) (entity.maxViewLeft.getX() - screen.xOffset) * SCALE, (int) (entity.maxViewLeft.getY() - screen.yOffset) * SCALE);
				g2.drawLine((entity.getX() - screen.xOffset) * SCALE, (int) (entity.getY() - screen.yOffset) * SCALE, (int) (entity.maxViewRight.getX() - screen.xOffset) * SCALE, (int) (entity.maxViewRight.getY() - screen.yOffset) * SCALE);
			}
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

}
