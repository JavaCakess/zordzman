package zordz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import zordz.entity.Player;
import zordz.level.Level;
import zordz.state.Button;
import zordz.state.ChooseLevelState;
import zordz.state.GameState;
import zordz.state.HelpState;
import zordz.state.OptionsState;
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
	public int coins = 10230;
	public State state;
	public TitleState titlestate;
	public GameState gamestate;
	public ChooseLevelState chooselevelstate;
	public OptionsState optionsstate;
	public HelpState helpstate;
	public InputHandler inputhandler;
	public static Zordz zordz;
	public static String version = "v0.1.0";
	public Map<String, String> englishMap = new HashMap<String, String>();
	public Map<String, String> germanMap = new HashMap<String, String>();
	public Map<String, Level> levels = new HashMap<String, Level>();
	public Console console;
	public String hex = "8B96315";
	public Player player = null;
	
	public static void main(String[] args) {
		new Zordz().start();
	}

	public Zordz() {
		console = new Console(this);
		try {
			Display.setDisplayMode(DISPLAY_MODE);
			Display.setTitle("The Zordzman " + version);
			console.write("Creating window...");
			Display.create();
			console.write("Initializing AudioLibrary...");
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
		optionsstate = new OptionsState(this);
		helpstate = new HelpState(this);
		state = titlestate;
		inputhandler = new InputHandler(this);
		SoundPlayer.init();
		
		
		
		console.write("Loading languages...");
		try {
			Scanner scan = new Scanner(new File("res/languages/english.txt"));
			String line = "";
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				englishMap.put(line.substring(0, line.indexOf(" ")), line.substring(line.indexOf(" ") + 2, line.length() - 1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		zordz = this;
		Zordz.zordz.console.write("Loading levels... ");
		File levelsDir = new File("res/levels/");
		for (File x : levelsDir.listFiles()) {
			levels.put(x.getName().substring(0, x.getName().length()-4), Level.loadLevel(x.getPath()));
		}
		Zordz.zordz.console.write("Done loading levels.");
		console.write("Done! And have fun.");
	}
	
	/**
	 * Run the game!
	 */
	public void run() {
		screen.startTPSThread();
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
		this.state.onSwitchAway(state);
		state.onSwitchTo(this.state);
		this.state = state;
		Button.between_state_cd = 15;
	}
	
	public String getString(String key) {
		if (Options.LANGUAGE == "ENGLISH") {
			if (englishMap.get(key) == null) {
				System.err.println("The key " + key + " does not exist!");
				return "Unknown";
			}
			return englishMap.get(key);
		}
		return englishMap.get(key);
	}

	public Level getLevel(String selected) {
		return levels.get(selected);
	}
}
