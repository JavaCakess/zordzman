package zordz.weapons;

import zordz.Options;
import zordz.entity.Entity;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class IgnisZord extends Weapon {

	public IgnisZord(int id) {
		super(WeaponType.ZORD, "Ignis Zord", 5, 7, id);
		damage = 10;
		handGFX_r_x_1 = 10;
		handGFX_r_x_2 = 11;
		handGFX_r_y_1 = 4;
		handGFX_r_y_2 = 4;
		handGFX_u_x_1 = 10;
		handGFX_u_x_2 = 11;
		handGFX_u_y_1 = 5;
		handGFX_u_y_2 = 5;
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
			for (int i = 0; i < level.getEntities().size(); i++) {
				Entity ent = level.getEntities().get(i);
				if (!(ent instanceof Mob)) continue;
				Mob mob = (Mob) ent;
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage, player);
					mob.setBurnTicks((int) ((int)Options.TICK_RATE * 1.5f));
				}
			}
		}
	}
}
