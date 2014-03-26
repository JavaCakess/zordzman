package zordz.weapons;

import zordz.Options;
import zordz.entity.Player;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Chicken extends Weapon {

	public Chicken() {
		super(WeaponType.FOOD, "Chicken", 2, 7);
	}

	public void use(Player player, Level level, float x, float y) {
		if (player.foodEatDelay > 0) return;

		if (player.foodCounter > 0) {
			player.foodCounter--;
		}

		player.foodEatDelay = (int) ((int)Options.TICK_RATE * 5f);
		player.foodEatTicks = (int) ((int)Options.TICK_RATE * 3f);
	}

	public void function(Player player, Level level, float x, float y) {
		if (player.foodEatTicks > 0) {
			player.canDoSwitch = false;
			if (player.foodEatTicks % (int) ((int)Options.TICK_RATE * 0.5f) == 0) {
				player.heal(player.getMaxHealth() * 0.05);
			}
		} else {
			player.canDoSwitch = true;
		}
	}
}
