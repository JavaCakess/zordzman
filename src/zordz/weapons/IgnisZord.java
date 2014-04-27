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
			player.attackTicks = (int) ((int)Options.TICK_RATE / 6f);
			player.attackDelay = (int) ((int)Options.TICK_RATE / 3f);
			SoundPlayer.play(Sound.melee_swing);
			float yspeed = 0, xspeed = 0, xspawn = x, yspawn = y;
			if (player.direction == Mob.UP) { yspeed = -1.15f; xspawn = x + 20; yspawn = y - 12; }
			if (player.direction == Mob.DOWN) { yspeed = 1.15f; xspawn = x + 20; yspawn = y + 24;}
			if (player.direction == Mob.LEFT) xspeed = -1.15f;
			if (player.direction == Mob.RIGHT) xspeed = 1.15f;
			level.add(new Fireball(level, xspawn, yspawn, xspeed, yspeed, player.direction, player));
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
