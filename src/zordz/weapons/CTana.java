package zordz.weapons;


import zordz.entity.Entity;
import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

/**
 * Sneaku-sensei update!
 * @author user
 *
 */
public class CTana extends Weapon {

	public CTana(int id) {
		super(WeaponType.KATANA, "C++Tana", 6, 7, id);
		damage = 9;
		handGFX_r_x_1 = 12;
		handGFX_r_x_2 = 13;
		handGFX_r_y_1 = 4;
		handGFX_r_y_2 = 4;
		handGFX_u_x_1 = 12;
		handGFX_u_x_2 = 13;
		handGFX_u_y_1 = 5;
		handGFX_u_y_2 = 5;
		handGFX_width = 16;
		handGFX_height = 16;
	}
	
	public void equip(Player player) {
		player.stats.speedPercentage += 0.15f;
	}

	public void use(Player player, Level level, float x, float y) {
		if (player.attackDelay < 1) {
			player.attackTicks = 13;
			player.attackDelay = 18;
			SoundPlayer.play(Sound.katana_swing, 0.5f);
		}
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.attackTicks == 9) {
			for (Entity entity : level.getEntities()) {
				if (!(entity instanceof Mob)) continue;
				Mob mob = (Mob) entity;
				if (mob.intersects(player.swordHitbox) && mob != player) {
					mob.damage(damage, player);
				}
			}
		}
	}
}
