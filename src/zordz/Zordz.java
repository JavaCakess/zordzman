package zordz;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import zordz.entity.PlayerMP;
import zordz.gfx.NewGLHandler;
import zordz.level.Level;
import zordz.net.GameClient;
import zordz.net.TCPClient;
import zordz.state.Button;
import zordz.state.ChooseLevelState;
import zordz.state.GameState;
import zordz.state.HelpState;
import zordz.state.IntroState;
import zordz.state.LoadoutState;
import zordz.state.MultiplayerState;
import zordz.state.OptionsState;
import zordz.state.State;
import zordz.state.TitleState;
import zordz.util.IOTools;
import zordz.util.LuaExec;
import zordz.util.SoundPlayer;

public class Zordz {

	public static final int WIDTH = 640, HEIGHT = 480;
	public static final DisplayMode DISPLAY_MODE = new DisplayMode(WIDTH, HEIGHT);
	public Screen screen;
	public Level level;
	public State state;
	public TitleState titlestate;
	public GameState gamestate;
	public ChooseLevelState chooselevelstate;
	public OptionsState optionsstate;
	public HelpState helpstate;
	public MultiplayerState mutliplayerstate;
	public LoadoutState loadoutstate;
	public InputHandler inputhandler;
	public GameClient gameclient;
	public IntroState introstate;
	public static Zordz zordz;
	public static String version = "v0.1.0";
	public Map<String, String> englishMap = new HashMap<String, String>();
	public Map<String, String> germanMap = new HashMap<String, String>();
	public Map<String, Level> levels = new HashMap<String, Level>();
	public Console console;
	public String hex = "8B96315";
	public PlayerMP player = null;
	public TCPClient tcpclient;
	

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
		mutliplayerstate = new MultiplayerState(this);
		loadoutstate = new LoadoutState(this);
		inputhandler = new InputHandler(this);
		introstate = new IntroState(this);
		state = introstate;
		SoundPlayer.init();
		LuaExec.init();

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
			} else if (option.equals("name")) {
				Options.USERNAME = v[1];
			}
		}
		console.write("Hello " + Options.USERNAME + "!");
		if (Options.console) {
			console.setVisible(true);
			optionsstate.console.checked = Options.console;
		}
		zordz = this;
		Zordz.zordz.console.write("Loading levels... ");
		File levelsDir = new File("res/levels/");
		for (File x : levelsDir.listFiles()) {
			levels.put(x.getName().substring(0, x.getName().length()-4), Level.loadMap("res/levels/" + x.getName(), x.getName()));
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
			data.add("name:" + Options.USERNAME);
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
	
	public Level getLevel(String selected) {
		return levels.get(selected);
	}
}
