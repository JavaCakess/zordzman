package zordz.weapons;

import zordz.Options;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Chicken extends Weapon {

	public Chicken(int id) {
		super(WeaponType.FOOD, "Chicken", 2, 7, id);
	}

	public void use(Player player, Level level, float x, float y) {
		int delay = 5, ticks = 3;
		if (player.getHealth() >= player.getMaxHealth()) return;
		if (!player.eat(delay, ticks)) return;
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.foodEatTicks > 0) {
			player.canDoSwitch = false;
			if (player.foodEatTicks % (int) ((int)Options.TICK_RATE * 0.5f) == 0) {
				player.heal(player.getMaxHealth() * 0.05);
			}
		} else {
			player.canDoSwitch = true;
			if (player.foodCounter == 0) {
				player.setSpecialAvailable(false);
				player.setCurrentWeapon(player.getCombatWeapon());
			}
		}
	}
}
