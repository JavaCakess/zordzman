package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import cjaf.tools.NewGLHandler;

import zordz.Options;
import zordz.Zordz;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.state.Meter.TextType;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class OptionsState extends State {

	Zordz zordz;
	Level titleLevel = new Level(640 / 32, 480 / 32);
	Button backToTitle = new Button("Go Back", new Color(0, 100, 0), Zordz.WIDTH / 2 - (Button.width / 2), 
			Zordz.HEIGHT - 86, 26, 16);
	Meter volume = new Meter(100f, 100f, 400f, 50f, 400f, 400f, new float[]{0.4f, 0.5f, 0.4f});
	Meter tickRate = new Meter(100, 200f, 400f, 50f, 400f, 400f, new float[]{0.4f, 0.5f, 0.4f}).setTextType(TextType.NORMAL).setCustomText(true);
	Checkbox console = new Checkbox(Color.green, false, 300, 260, 32, 32);
	int backToState = 0;
	public OptionsState(Zordz zordz) {
		this.zordz = zordz;
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
		titleLevel.render();
		backToTitle.draw();
		//Draw the volume bar!
		NewGLHandler.setCurrentColor(new float[]{0.7f, 0.7f, 0.7f}, false);
		Text.render(zordz.getString("#ZM_Volume") + ":", 40, 60, 16, 16);
		Text.render(zordz.getString("#ZM_Performance") + ":", 40, 160, 16, 16);
		Text.render("Enable Console: ", 40, 260, 16, 16);
		NewGLHandler.resetColors();
		volume.draw();
		tickRate.draw();
		console.draw();
	}

	public void tick() {
		String performance = "";
		zordz.screen.setOff(0, 0);
		volume.setValue(Options.SOUND_LEVEL * 4);
		if (backToTitle.clicked()) {
			if (backToState == 0) {
				zordz.switchState(zordz.titlestate);
			} else if (backToState == 1) {
				zordz.switchState(zordz.gamestate);
			}
			SoundPlayer.play(Sound.button_clicked);
		} else if (volume.clicked()) {
			volume.setValue(Mouse.getX() - 100);
			Options.SOUND_LEVEL = Math.round(volume.value / 4);
		}  else if (tickRate.clicked()) {
			tickRate.setValue(Mouse.getX() - 100);
			Options.TICK_RATE = 30 + Math.round(tickRate.value / 14);
			if (Options.TICK_RATE % 2 == 1) {
				Options.TICK_RATE++;
			}
		}
		if (console.wasClicked) {
			zordz.console.setVisible(console.checked);
		}
		
		if (Options.TICK_RATE <= 40) {
			performance = zordz.getString("#ZM_Performance_Low");
		}
		if (Options.TICK_RATE > 40 && Options.TICK_RATE <= 50) {
			performance = zordz.getString("#ZM_Performance_Middle");
		}
		if (Options.TICK_RATE > 50) {
			performance = zordz.getString("#ZM_Performance_Good");
		}
		tickRate.setMeterText(performance);
	}

	public int getID() {
		return 3;
	}

	public void onSwitchAway(State to) {
		
	}

	public void onSwitchTo(State awayFrom) {
		
	}
	
}
