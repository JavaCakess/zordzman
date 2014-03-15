package zordz.weapons;

import zordz.entity.Player;
import zordz.level.Level;

public abstract class Weapon {

	
	public static Zord zord = new Zord();
	public static Minizord minizord = new Minizord();
	
	public static enum WeaponType {
		ZORD(0x00);
		
		private int slot;
		WeaponType(int slot) {
			this.slot = slot;
		}
		
		public boolean isCombat() {
			return slot == 0x00;
		}
		
		public boolean isSpecial() {
			return slot == 0x01;
		}
	}
	
	String name;
	WeaponType type;
	int damage;
	
	public Weapon(WeaponType type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public Weapon setDamage(int damage) {
		if (type == WeaponType.ZORD) {
			this.damage = damage;
		}
		return this;
	}
	
	public int getDamage() {
		if (type == WeaponType.ZORD) {
			return damage;
		}
		return 0;
	}
	
	public String getName() {
		return name;
	}

	public void equip(Player player) {
		
	}
	
	public abstract void use(Player player, Level level, float x, float y);

	public abstract void function(Player player, Level level, float x, float y);
}
