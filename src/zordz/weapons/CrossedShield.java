package zordz.weapons;

import zordz.entity.Player;
import zordz.level.Level;

public class CrossedShield extends Weapon {

	public CrossedShield() {
		super(WeaponType.SHIELD, "Crossed Shield");
	}
	
	public void equip(Player player) {
		player.burnPercentage -= 0.4f;
		player.healthAdditive += 35;
	}

	public void use(Player player, Level level, float x, float y) {
		
	}

	public void function(Player player, Level level, float x, float y) {

	}

}
