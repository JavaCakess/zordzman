package zordz;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import zordz.level.Level;
import zordz.state.Button;
import zordz.state.ChooseLevelState;
import zordz.state.GameState;
import zordz.state.State;
import zordz.state.TitleState;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class Zordz {

	public static final int WIDTH = 640, HEIGHT = 480;
	public static final DisplayMode DISPLAY_MODE = new DisplayMode(WIDTH, HEIGHT);
	public Screen screen;
	public Level level;
	public int hp = 100;
	public State state;
	public TitleState titlestate;
	public GameState gamestate;
	public ChooseLevelState chooselevelstate;
	public InputHandler inputhandler;
	public static Zordz zordz;
	
	public static void main(String[] args) {
		new Zordz().start();
	}

	public Zordz() {
		try {
			Display.setDisplayMode(DISPLAY_MODE);
			Display.setTitle("The Zordzman");
			Display.create();
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void start() {
		init();
		run();
		stop();
	}
	
	/** 
	 * Initialize variables, etc
	 */
	public void init() {
		NewGLHandler.init2D(WIDTH, HEIGHT);
		screen = new Screen(this);
		titlestate = new TitleState(this);
		gamestate = new GameState(this);
		chooselevelstate = new ChooseLevelState(this);
		state = titlestate;
		inputhandler = new InputHandler(this);
		SoundPlayer.init();
		zordz = this;
	}
	
	/**
	 * Run the game!
	 */
	public void run() {
		while (!Display.isCloseRequested()) {
			NewGLHandler.wipeScreen();
			screen.render();
			screen.update();
		}
	}
	
	/**
	 * Clean up (close the window, etc.). Used before quitting
	 */
	public void stop() {
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}
	
	public void switchState(State state) {
		this.state = state;
		Button.between_state_cd = 15;
	}
}
