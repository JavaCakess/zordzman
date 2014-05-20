package zordz.weapons;

import zordz.Options;
import zordz.entity.Fireball;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class IgnisZord extends Weapon {

	public IgnisZord(int id) {
		super(WeaponType.ZORD, "Ignis Zord", 5, 7, id);
		damage = 7;
	}

	public void use(Player player, Level level, float x, float y) {
		if (player.attackDelay < 1) {
			player.attackTicks = (int) ((int)Options.TICK_RATE / 6f); //30 ticks instead !!!
			player.attackDelay = (int) ((int)Options.TICK_RATE / 2f);
			SoundPlayer.play(Sound.melee_swing);
		}
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.attackTicks == 3) {
			for (Mob mob : level.getMobs()) {
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage, player);
					mob.setBurnTicks((int) ((int)Options.TICK_RATE * 1.5f));
				}
			}
		}
	}
}
