package zordz.state;

import java.awt.Color;

import zordz.Zordz;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class MutliplayerState extends State {

	Level titleLevel = new Level(640 / 32, 480 / 32);
	Zordz zordz;
	Button backToTitle = new Button("Go Back", Color.yellow, Zordz.WIDTH / 2 - (Button.WIDTH / 2), Zordz.HEIGHT - Button.HEIGHT - 3, 26, 16);
	ScrollPane servers = new ScrollPane("Servers", 80, 60, 480, (480 / 12 * 9) - 100, 0, 0, null);
	public MutliplayerState(Zordz zordz) {
		this.zordz = zordz;
		for (int x = 0; x < titleLevel.getWidth(); x++) {
			for (int y = 0; y < titleLevel.getHeight(); y++) {
				if (x == 0 || y == 0 || x == titleLevel.getWidth() - 1 || y == titleLevel.getHeight() - 1) {
					titleLevel.setTileIDAt(x, y, Tile.FLOWER.getID());
				}
			}
		}
		servers.fullBarSize = 187;
		servers.add("cakessjaf.zapto.org");
		servers.add("26.16.195.10");
		servers.add("mzordz.com");
	}

	public void render() {
		titleLevel.render();
		servers.render();
		backToTitle.draw();
	}

	public void tick() {
		if (backToTitle.clicked()) {
			zordz.switchState(zordz.titlestate);
			SoundPlayer.play(Sound.button_clicked);
		}
	}

	public void onSwitchAway(State to) {

	}

	public void onSwitchTo(State awayFrom) {

	}

	public int getID() {
		return 5;
	}
}
