package zordz.weapons;

import zordz.Options;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Minizord extends Weapon {

	public Minizord(int id) {
		super(WeaponType.ZORD, "Minizord", 0, 0, id);
		damage = 8;
	}
	
	public void equip(Player player) {
		player.setSpeed(1 * 1.25f); //+25% speed boost
	}

	public void use(Player player, Level level, float x, float y) {
		if (player.attackDelay < 1) {
			player.attackTicks = (int) ((int)6 * (Options.TICK_RATE / Options.MAX_TICK_RATE));
			player.attackDelay = (int) ((int)12 * (Options.TICK_RATE / Options.MAX_TICK_RATE));
			SoundPlayer.play(Sound.melee_swing);
		}
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.attackTicks == 3) {
			for (Mob mob : level.getMobs()) {
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage);
				}
			}
		}
		
	}

}
