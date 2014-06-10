package zordz.weapons;

import java.util.Random;

import zordz.Options;
import zordz.entity.Entity;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class SplinterZord extends Weapon {
	Random r = new Random();
	public SplinterZord(int id) {
		super(WeaponType.ZORD, "Splinter Zord", 3, 7, id);
		damage = 5;
	}

	public void use(Player player, Level level, float x, float y) {
		if (player.attackDelay < 1) {
			player.attackTicks = (int) ((int)Options.TICK_RATE / 6f);
			player.attackDelay = (int) ((int)Options.TICK_RATE / 3f);
			SoundPlayer.play(Sound.melee_swing, 0.6f);
		}
	}
	
	public void equip(Player p) {
		p.stats.speedPercentage += 0.1;
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.attackTicks == 3) {
			for (Entity ent : level.getEntities()) {
				if (!(ent instanceof Mob)) continue;
				Mob mob = (Mob) ent;
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage, player);
					if (r.nextInt(5) == 0) {
						mob.setBleedTicks((int) ((int)Options.TICK_RATE * 10f), player);
					}
				}
			}
		}
	}
	
}
