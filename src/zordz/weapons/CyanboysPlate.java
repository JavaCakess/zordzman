package zordz.weapons;

import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;

public class CyanboysPlate extends Weapon {

	public CyanboysPlate(int id) {
		super(WeaponType.SHIELD, "Cyanboy's Plate", 4, 7, id);
	}

	public void equip(Player player) {
		player.stats.speedPercentage -= 0.1f;
	}
	
	public void use(Player player, Level level, float x, float y) {
		
	}

	public void function(Player player, Level level, float x, float y) {

	}

	public void onDoDamage(double damage, Mob mob, Player player, Level level, float x, float y) {
		player.heal(damage * 0.15);
	}
}
