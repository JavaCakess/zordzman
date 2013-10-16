package zordz;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import zordz.level.Level;
import cjaf.tools.NewGLHandler;

public class Zordz {

	public static final int WIDTH = 640, HEIGHT = 480;
	public static final DisplayMode DISPLAY_MODE = new DisplayMode(WIDTH, HEIGHT);
	public Screen screen;
	public Level level;
	public int hp = 100;
	public static Zordz zordz;
	
	public static void main(String[] args) {
		new Zordz().start();
	}

	public Zordz() {
		try {
			Display.setDisplayMode(DISPLAY_MODE);
			Display.setTitle("The Zordzman");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void start() {
		init(); //Initialize variables, etc.
		run();  //Run the game!
		stop(); //Stop running the game, and close the window.
	}
	
	public void init() {
		NewGLHandler.init2D(WIDTH, HEIGHT);
		screen = new Screen(this);
		level = new Level();
		zordz = this;
	}
	
	public void run() {
		while (!Display.isCloseRequested()) {
			screen.render();
			screen.update();
		}
	}
	
	public void stop() {
		Display.destroy();
		System.exit(0);
	}
}
