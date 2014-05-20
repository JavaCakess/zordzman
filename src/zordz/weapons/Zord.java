package zordz.weapons;

import zordz.Options;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Zord extends Weapon {

	public Zord(int id) {
		super(WeaponType.ZORD, "Zord", 0, 7, id);
		damage = 15;
	}

	public void use(Player player, Level level, float x, float y) {
		if (player.attackDelay < 1) {
			player.attackTicks = (int) ((int)Options.TICK_RATE / 6f);
			player.attackDelay = (int) ((int)Options.TICK_RATE / 3f);
			SoundPlayer.play(Sound.melee_swing);
		}
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.attackTicks == 3) {
			for (Mob mob : level.getMobs()) {
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage, player);
				}
			}
		}
	}
}
