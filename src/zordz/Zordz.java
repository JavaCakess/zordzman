package zordz;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import zordz.entity.Player;
import zordz.entity.PlayerMP;
import zordz.level.Level;
import zordz.net.GameClient;
import zordz.net.GameServer;
import zordz.net.Packet00Login;
import zordz.state.Button;
import zordz.state.ChooseLevelState;
import zordz.state.GameState;
import zordz.state.HelpState;
import zordz.state.OptionsState;
import zordz.state.State;
import zordz.state.TitleState;
import zordz.util.IOTools;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class Zordz {

	public static final int WIDTH = 640, HEIGHT = 480;
	public static final DisplayMode DISPLAY_MODE = new DisplayMode(WIDTH, HEIGHT);
	public Screen screen;
	public Level level;
	public int hp = 100;
	public int coins = 10230;
	public String myName;
	public State state;
	public TitleState titlestate;
	public GameState gamestate;
	public ChooseLevelState chooselevelstate;
	public OptionsState optionsstate;
	public HelpState helpstate;
	public InputHandler inputhandler;
	public GameClient gameclient;
	public GameServer gameserver;
	public static Zordz zordz;
	public static String version = "v0.1.0";
	public Map<String, String> englishMap = new HashMap<String, String>();
	public Map<String, String> germanMap = new HashMap<String, String>();
	public Map<String, Level> levels = new HashMap<String, Level>();
	public Console console;
	public String hex = "8B96315";
	public PlayerMP player = null;

	public static void main(String[] args) {
		new Zordz().start();
	}

	public Zordz() {
		myName = JOptionPane.showInputDialog("Input username!");
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

		if (JOptionPane.showConfirmDialog(null, "Run server?") == 0) {
			gameserver = new GameServer();
			gameserver.start();
		}
		gameclient = new GameClient("localhost");
		gameclient.start();
		
		console.write("Loading options");
		ArrayList<String> options = IOTools.readFile(new File("res/settings/options.txt"));
		for (String s : options) {
			String option = s.split(":")[0];
			String[] v = s.split(":");
			if (option.equals("soundlevel")) {
				Options.SOUND_LEVEL = Integer.parseInt(v[1]);
			} else if (option.equals("tickratemeter")) {
				Options.TICK_RATE_METER = Integer.parseInt(v[1]);
			} else if (option.equals("console")) {
				Options.console = Boolean.parseBoolean(v[1]);
			} else if (option.equals("damage_feedback")) {
				Options.DAMAGE_FEEDBACK = Boolean.parseBoolean(v[1]);
			}
		}
		if (Options.console) {
			console.setVisible(true);
			optionsstate.console.checked = Options.console;
		}
		optionsstate.tickRate.setValue(Options.TICK_RATE_METER);
		optionsstate.meterToTickRate();
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
		console.write("Saving options");
		ArrayList<String> data = new ArrayList<String>();
		{
			data.add("soundlevel:" + Options.SOUND_LEVEL);
			data.add("tickratemeter:" + Options.TICK_RATE_METER);
			data.add("console:" + Options.console);
			data.add("damage_feedback:" + Options.DAMAGE_FEEDBACK);
		}
		IOTools.writeToFile(new File("res/settings/options.txt"), data);
		console.write("Destroying window...");
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
