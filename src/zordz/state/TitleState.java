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
	Texture titlescreen = NewGLHandler.loadTexture("res/titlescreen.png");
	Level titleLevel = new Level(640 / 32, 480 / 32);
	Button sp = new Button("Singleplayer", Color.red, 232, 200, 12, 12);
	Button exit = new Button("Quit", Color.orange, 232, 260, 52, 16);
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
		}
	}

	public int getID() {
		return 0;
	}
}
