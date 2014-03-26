package zordz.weapons;

import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;

public abstract class Weapon {

	
	public static Zord zord = new Zord();
	public static Minizord minizord = new Minizord();
	public static CrossedShield crossed_shield = new CrossedShield();
	public static TestSpecial test_special = new TestSpecial();
	public static Chicken chicken = new Chicken();
	public static SplinterZord splinter_zord = new SplinterZord();
	public static CyanboysPlate cyanboys_plate = new CyanboysPlate();
	
	public static enum WeaponType {
		ZORD(0x00, true), SHIELD(0x01, false), TEST(0x01, true), FOOD(0x01, true);
		
		private int slot;
		private boolean switchable;
		WeaponType(int slot, boolean switchable) {
			this.slot = slot;
			this.switchable = switchable;
		}
		
		public int getSlot() {
			return slot;
		}
		
		public boolean canSwitch() {
			return switchable;
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
	public int texX;
	public int texY;
	
	public Weapon(WeaponType type, String name, int texX, int texY) {
		this.type = type;
		this.name = name;
		this.texX = texX;
		this.texY = texY;
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
	
	public WeaponType getType() {
		return type;
	}
	
	public abstract void use(Player player, Level level, float x, float y);

	public abstract void function(Player player, Level level, float x, float y);

	public void onDoDamage(double damage2, Mob mob, Player player, Level lvl,
			float x, float y) {
		
	}
}
