package zordz.weapons;

import zordz.entity.Player;
import zordz.level.Level;

public class CrossedShield extends Weapon {

	public CrossedShield(int id) {
		super(WeaponType.SHIELD, "Crossed Shield", 1, 7, id);
	}
	
	public void equip(Player player) {
		player.stats.burnPercentage -= 0.4f;
		player.stats.healthAdditive += 35;
	}

	public void use(Player player, Level level, float x, float y) {
		
	}

	public void function(Player player, Level level, float x, float y) {

	}

}
