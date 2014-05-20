package zordz.state;

import java.awt.Color;

import org.newdawn.slick.opengl.Texture;

import zordz.Options;
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
	Button sp = new Button("Singleplayer", Color.red, 232, 120, 12, 12);
	String sp_text = "Singleplayer mode.";
	Button mp = new Button("Multi-player", Color.blue, 232, 120 + Button.HEIGHT, 12, 12);
	String mp_text = "Multiplayer mode.";
	Button options = new Button("Options", Color.white, 232, 210, 28, 16);
	String options_text = "Configure the options.";
	Button help    = new Button("Help", Color.green, 232, 210 + Button.HEIGHT, 52, 16);
	String help_text = "How to play the game and info.";
	Button loadout = new Button("Loadout", Color.yellow.darker(), 232, 300, 28, 16);
	String loadout_text = "Select your weapons!";
	Button exit = new Button("Quit", Color.orange, 232, 300 + Button.HEIGHT, 52, 16);
	String exit_text = "Quit the game.";
	
	String buttonDesc = "";
	int ticks = 0;
	int cjafFlashNum = 0;
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
		mp.draw();
		options.draw();
		loadout.draw();
		help.draw();
		exit.draw();
		Drawer.setCol(Color.lightGray);
		Text.render(buttonDesc, 0, 480-41, 16, 16);
		Drawer.setCol(Color.white);
		Text.render(Zordz.version, 0, 480-24, 24, 24);
		if (ticks % Options.TICK_RATE == 0) cjafFlashNum++;
		String cjafString = "CJAF!";
		for (int i = 0; i < cjafString.length(); i++) {
			if (cjafFlashNum % cjafString.length() == i) {
				Drawer.setCol(Color.red);
			}
			Text.render(""+cjafString.charAt(i), 640 - (24 * -i+1) - (24 * 5), 480 - 24, 24, 24);
			Drawer.setCol(Color.white);
		}
		
		for (int i = 0; i < zordz.hex.length(); i++) {
			Text.render(""+zordz.hex.charAt(i), 640 - (16 * -i+1) - (16 * zordz.hex.length()), 480 - 40, 16, 16);
		}
		Drawer.setCol(Color.white);
	}
	public void tick() {
		zordz.screen.setOff(0, 0);
		if (sp.mouseOn()) {
			buttonDesc = sp_text;
		} else if (mp.mouseOn()) {
			buttonDesc = mp_text;
		} else if (options.mouseOn()) {
			buttonDesc = options_text;
		} else if (help.mouseOn()) {
			buttonDesc = help_text;
		} else if (loadout.mouseOn()) {
			buttonDesc = loadout_text;
		} else if (exit.mouseOn()) {
			buttonDesc = exit_text;
		} else {
			buttonDesc = "";
		}
		
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
		} else if (help.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.switchState(zordz.helpstate);
		} else if (loadout.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.switchState(zordz.loadoutstate);
		} else if (mp.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.switchState(zordz.mutliplayerstate);
		}
		ticks++;
		if (ticks == Options.TICK_RATE) {
			ticks = 0;
		}
	}

	public int getID() {
		return 0;
	}

	public void onSwitchAway(State to) {
		
	}

	public void onSwitchTo(State awayFrom) {
		
	}
}
