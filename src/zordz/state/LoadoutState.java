package zordz.state;

import java.awt.Color;

import zordz.Options;
import zordz.Zordz;
import zordz.entity.Player;
import zordz.entity.Statistics;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import zordz.weapons.Weapon;

public class LoadoutState extends State {

	Zordz zordz;
	Level level;
	WeaponSelector combatSelector = new WeaponSelector(Options.combatWeap, new Weapon[]{
			Weapon.ignis_zord, Weapon.splinter_zord, Weapon.zord
	}, 120, 200);
	Player player = new Player(level, 0, 0, ""); // Invisible player.
	Button done = new Button("Done", new Color(150, 130, 0), (Zordz.WIDTH / 2 - (Button.WIDTH / 2)), Zordz.HEIGHT - 56, 50, 16);
	public LoadoutState(Zordz zordz) {
		this.zordz = zordz;
		level = new Level(20, 15);

		for (int x = 0; x < level.width; x++) {
			for (int y = 0; y < level.height; y++) {
				if (x == 0 || y == 0 || y == level.height-1 || x == level.width-1)
					level.setTileIDAt(x, y, Tile.FLOWER.getID());
				else level.setTileIDAt(0, 0, Tile.GRASS.getID());
			}
		}
	}

	public void render() {
		// Background, so it isn't just black space.
		level.render();
		int combatBox_x = 120;
		int combatBox_y = 200;
		//		int combatBox_w = 64;
		//		int combatBox_h = 64;
		//		int combatWeap_x = 128;
		//		int combatWeap_y = 208;
		//		int combatWeap_w = 48;
		//		int combatWeap_h = 48;

		// And tell the player the item inside is their combat weapon.
		Text.render("Combat", combatBox_x - 16, combatBox_y - 16, 16, 16);

		combatSelector.render();

		done.draw();

		if (done.clicked()) {
			saveOptions();
			zordz.switchState(zordz.titlestate);
			SoundPlayer.play(Sound.button_clicked);	
		}
		Drawer.setCol(Color.yellow);
		
		Text.render("Max HP:   " + (Statistics.DEFAULT_MAX_HEALTH + player.stats.healthAdditive),
								100, 340, 16, 16);
		Text.render("Speed:    " + (int)((Statistics.DEFAULT_SPEED * player.stats.speedPercentage) * 60),
				100, 356, 16, 16);
		Drawer.setCol(Color.white);
	}

	private void saveOptions() {
		Options.combatWeap = combatSelector.weap;
	}

	public void tick() {
		player.equip(combatSelector.weap, Options.specialWeap);
	}

	public void onSwitchAway(State to) {

	}

	public void onSwitchTo(State awayFrom) {

	}

	@Override
	public int getID() {
		return 6;
	}
}
