package zordz.weapons;

import zordz.entity.Mob;
import zordz.entity.Player;
import zordz.level.Level;

public abstract class Weapon {

	
	public static Zord zord = new Zord(0);
	public static Minizord minizord = new Minizord(1);
	public static CrossedShield crossed_shield = new CrossedShield(2);
	public static TestSpecial test_special = new TestSpecial(3);
	public static Chicken chicken = new Chicken(4);
	public static SplinterZord splinter_zord = new SplinterZord(5);
	public static CyanboysPlate cyanboys_plate = new CyanboysPlate(6);
	public static IgnisZord ignis_zord = new IgnisZord(7);
	
	int id;
	Weapon[] weapons = new Weapon[1024];
	
	public static enum WeaponType {
		ZORD(0x00, true, "Sword"), SHIELD(0x01, false, "Shield"),
		TEST(0x01, false, ""), FOOD(0x01, true, "Food");
		
		private int slot;
		private boolean switchable;
		private String name;
		WeaponType(int slot, boolean switchable, String name) {
			this.slot = slot;
			this.switchable = switchable;
			this.name = name;
		}
		
		public String getName() {
			return name;
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
	
	public Weapon(WeaponType type, String name, int texX, int texY, int id) {
		this.type = type;
		this.name = name;
		this.texX = texX;
		this.texY = texY;
		this.id = id;
		weapons[id] = this;
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

	public void equip(Player player) {}
	
	public WeaponType getType() {
		return type;
	}
	
	public abstract void use(Player player, Level level, float x, float y);

	public abstract void function(Player player, Level level, float x, float y);

	public void onDoDamage(double damage2, Mob mob, Player player, Level lvl,
			float x, float y) {}
	
	public int getID() {
		return id;
	}

	public static Weapon getByID(int id2) {
		return null;
	}
}
