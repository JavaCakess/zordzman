package zordz.state;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class TitleState extends State {
	Zordz zordz;
	Level titleLevel = new Level(640 / 32, 480 / 32);
	Button sp = new Button("Singleplayer", Color.red, 232, 180, 12, 12);
	Button options = new Button("Options", Color.white, 232, 240, 28, 16);
	Button exit = new Button("Quit", Color.orange, 232, 300, 52, 16);
	public TitleState(Zordz zordz) {
		this.zordz = zordz;
		for (int x = 0; x < titleLevel.getWidth(); x++) {
			for (int y = 0; y < titleLevel.getHeight(); y++) {
				titleLevel.setTileIDAt(x, y, Tile.GRASS.getID());
			}
		}
	}

	public void render() {
		titleLevel.render();
		int titleX = 180;
		Drawer.setCol(new Color(255, 204, 102));
		Text.render("Z", titleX, 40, 32, 32);
		Drawer.setCol(Color.red);
		Text.render("ordzman", titleX + 32, 56, 32, 16);
		Drawer.setCol(Color.white);
		sp.draw();
		options.draw();
		exit.draw();
	}
	public void tick() {
		zordz.screen.setOff(0, 0);
		if (sp.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.switchState(zordz.chooselevelstate);
		} else if (exit.clicked()) {
			System.out.println("[INFO] Stopping Game");
			zordz.stop();
		} else if (options.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.switchState(zordz.optionsstate);
			zordz.optionsstate.backToState = getID();
		}
	}

	public int getID() {
		return 0;
	}
}
