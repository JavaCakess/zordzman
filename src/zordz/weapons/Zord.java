package zordz.weapons;

import zordz.Options;
import zordz.entity.Entity;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Zord extends Weapon {

	public Zord(int id) {
		super(WeaponType.ZORD, "Zord", 0, 7, id);
		damage = 15;
		handGFX_r_x_1 = 8;
		handGFX_r_x_2 = 9;
		handGFX_r_y_1 = 4;
		handGFX_r_y_2 = 4;
		handGFX_u_x_1 = 8;
		handGFX_u_x_2 = 9;
		handGFX_u_y_1 = 5;
		handGFX_u_y_2 = 5;
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
			for (Entity ent : level.getEntities()) {
				if (!(ent instanceof Mob)) continue;
				Mob mob = (Mob) ent;
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage, player);
				}
			}
		}
	}
}
