package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import zordz.Options;
import zordz.Zordz;
import zordz.gfx.NewGLHandler;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.state.Meter.TextType;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class OptionsState extends State {

	Zordz zordz;
	Level titleLevel = new Level(640 / 32, 480 / 32);
	Button cancel = new Button("Back", new Color(0, 100, 0), Zordz.WIDTH / 2 - (Button.WIDTH / 2) + 100, 
			Zordz.HEIGHT - 86, 50, 16);
	Button done = new Button("Done", new Color(0, 100, 0), (Zordz.WIDTH / 2 - (Button.WIDTH / 2)) - 100, Zordz.HEIGHT - 86, 26, 16);
	Meter volume = new Meter(100f, 100f, 400f, 50f, 400f, 400f, new float[]{0.4f, 0.5f, 0.4f});
	public Meter tickRate = new Meter(100, 200f, 400f, 50f, 400f, 400f, new float[]{0.4f, 0.5f, 0.4f}).setTextType(TextType.NORMAL).setCustomText(true);
	public Checkbox console = new Checkbox(Color.green, false, 300, 260, 32, 32);
	public TextBox username;
	boolean init = true;
	int backToState = 0;
	public OptionsState(Zordz zordz) {
		this.zordz = zordz;
		tickRate.setValue(Options.TICK_RATE_METER);
		username = new TextBox(Options.USERNAME, 200f, 300, 332f, 32f, 24, 13, new char[]{':', ' ', '?', '\n', '\r'});
		console.checked = Options.console;
		for (int x = 0; x < titleLevel.getWidth(); x++) {
			for (int y = 0; y < titleLevel.getHeight(); y++) {
				if (x == 0 || x == titleLevel.getWidth()-1) {
					titleLevel.setTileIDAt(x, y, Tile.SAND_ROCK.getID());
					continue;
				}
				if (y == 0 || y == titleLevel.getHeight()-1) {
					titleLevel.setTileIDAt(x, y, Tile.SAND_ROCK.getID());
					continue;
				}
				titleLevel.setTileIDAt(x, y, Tile.SAND.getID());
			}
		}
	}
	
	public void render() {
		if (init) { init(); init = false; }
		titleLevel.render();
		cancel.draw();
		done.draw();
		//Draw the volume bar!
		NewGLHandler.setCurrentColor(new float[]{0.7f, 0.7f, 0.7f}, false);
		Text.render("Volume:", 40, 60, 16, 16);
		Text.render("Enable Console: ", 40, 260, 16, 16);
		Text.render("Username:", 40, 300, 16, 16);
		NewGLHandler.resetColors();
		volume.draw();
		console.draw();
		username.draw();
	}

	public void saveOptions() {
		//Username
		Options.USERNAME = username.text;
	}
	
	public void init() {
		System.out.println("four eyes");
		username.setText(Options.USERNAME);
		System.out.println(username.text);
	}
	
	public void tick() {
		
		zordz.screen.setOff(0, 0);
		volume.setValue(Options.SOUND_LEVEL * 4);
		if (cancel.clicked()) {
			saveOptions();
			if (backToState == 0) {
				zordz.switchState(zordz.titlestate);
			} else if (backToState == 1) {
				zordz.switchState(zordz.gamestate);
			}
			SoundPlayer.play(Sound.button_clicked);
		} else if (volume.clicked()) {
			volume.setValue(Mouse.getX() - 100);
			Options.SOUND_LEVEL = Math.round(volume.value / 4);
		} else if (done.clicked()) {
			saveOptions();
			if (backToState == 0) {
				zordz.switchState(zordz.titlestate);
			} else if (backToState == 1) {
				zordz.switchState(zordz.gamestate);
			}
			SoundPlayer.play(Sound.button_clicked);
		}
		if (console.wasClicked) {
			zordz.console.setVisible(console.checked);
		}
	}

	public void meterToTickRate() {
		Options.TICK_RATE = 30 + Math.round(tickRate.value / 14);
		Options.TICK_RATE_METER = (int)tickRate.value;
		if (Options.TICK_RATE % 2 == 1) {
			Options.TICK_RATE++;
		}
	}
	
	public int getID() {
		return 3;
	}

	public void onSwitchAway(State to) {
		
	}

	public void onSwitchTo(State awayFrom) {
		
	}
	
}
