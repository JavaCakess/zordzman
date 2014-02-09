package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import cjaf.tools.NewGLHandler;

import zordz.Options;
import zordz.Zordz;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class OptionsState extends State {

	Zordz zordz;
	Level titleLevel = new Level(640 / 32, 480 / 32);
	Button backToTitle = new Button("Go Back", new Color(0, 100, 0), Zordz.WIDTH / 2 - (Button.width / 2), 
			Zordz.HEIGHT - 86, 26, 16);
	Meter volume = new Meter(100f, 100f, 400f, 50f, 400f, 400f, new float[]{0.4f, 0.5f, 0.4f});
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
		Text.render("Volume:", 40, 60, 16, 16);
		NewGLHandler.resetColors();
		volume.draw();
	}

	public void tick() {
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
		}
	}

	public int getID() {
		return 3;
	}
	
}
