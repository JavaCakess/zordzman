package zordz.entity;

import java.awt.Color;
import java.awt.Rectangle;

import zordz.Options;
import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.weapons.Weapon;
import cjaf.tools.NewGLHandler;

/**
 * ID: 0
 * @author JavaCakess
 *
 */
public class Player extends Mob {
	//STATISTICS
	public static final int DEFAULT_BURN_RATE = 15;
	public float burnPercentage = 1f;
	public static final int DEFAULT_MAX_HEALTH = 100;
	public int healthAdditive;
	public static final float DEFAULT_SPEED = 1f;
	public float speedPercentage = 1f;
	//END STATISTICS
	float move_ticks = 0;

	public int foodCounter = 1;
	public int foodEatTicks, foodEatDelay;
	public int ticks;
	public int attackTicks = 0;
	public int attackDelay = 0;
	public int displayLifeTicks = 0;
	public Rectangle swordHitbox = new Rectangle();
	String username = "Player";
	Weapon combat = Weapon.zord;
	Weapon special = Weapon.cyanboys_plate;
	int currentWeapon = 0;
	public boolean canDoSwitch = true;
	public Player(Level lvl, float x, float y, String username) {
		super(lvl, x, y);
		id = 0;
		max_health = DEFAULT_MAX_HEALTH;
		health = max_health;
		this.username = username;
		speed = 1f; //Speed: 60
		rect = new Rectangle((int)x, (int)y, 32, 32);
		equip(Weapon.splinter_zord, Weapon.cyanboys_plate);
	}

	public boolean canDoSwitch() {
		return canDoSwitch;
	}
	
	public void equip(Weapon combat, Weapon special) {
		//Set default stats
		max_health = DEFAULT_MAX_HEALTH;
		healthAdditive = 0;
		speed = DEFAULT_SPEED;
		speedPercentage = 1f;
		burnRate = DEFAULT_BURN_RATE;
		burnPercentage = 1f;
		this.combat = combat;
		this.special = special;
		combat.equip(this);
		special.equip(this);
		max_health += healthAdditive;
		health = max_health;
		speed *= speedPercentage;
		burnRate = (int)(burnRate * burnPercentage);
	}

	public float healthPerc() {
		return (float)health / max_health;
	}

	public void render() {

		int x = Math.round(this.x);
		int y = Math.round(this.y);
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f, 0.3f}, true);
		NewGLHandler.draw2DRect((x + 12) - (username.length() * 4), y - 20, (8 * username.length()) + 4, 9, true);
		
		if (displayLifeTicks > 0) {
			NewGLHandler.draw2DRect((x + 18) - (36), y - 11, 64, 8, true);
			if (healthPerc() > 0.66) {
				Drawer.setCol(Color.green);
			} else if (healthPerc() > 0.33) {
				Drawer.setCol(Color.orange);
			} else if (healthPerc() > 0) {
				Drawer.setCol(Color.red);
			}
			NewGLHandler.draw2DRect((x + 14) - (27), y - 10, 50 * ((float)health / max_health), 4, true);
			Drawer.setCol(Color.black);
			NewGLHandler.draw2DRect((x + 14) - (27), y - 9, 50, 4, false);
		}
		Drawer.setCol(Color.white);
		Text.render(username, (x + 12) - (username.length() * 4), y - 20, 8, 8);

		int scale = 2;
		int down_legs_modifier = 0;
		int side_pos_modifier = 0;
		int up_flip_arms = 0b00;
		int up_rarm_pos = 8;
		int up_larm_pos = 0;
		float bottom_left_pos = x + 8 * scale;
		float bottom_right_pos = x;
		if (damageTicks > 0) {
			Drawer.setCol(Color.red);
		}
		// Down
		if (direction == Mob.DOWN) {
			Drawer.draw(SpriteSheet.sheet, 0, 4, x, y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 1, 4, x + 8 * scale, y, 8 * scale, 8 * scale);

			if (move_ticks > Options.TICK_RATE / 2) {

				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = x;
				bottom_right_pos = x + 8 * scale;
			} else {
				down_legs_modifier = 0b00;
				bottom_left_pos = x + 8 * scale;
				bottom_right_pos = x;
			}
			Drawer.draw(SpriteSheet.sheet, 1, 5, bottom_left_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(SpriteSheet.sheet, 0, 5, bottom_right_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 5, x + 20, y + 28, 12, 12, Drawer.FLIP_Y);
				Drawer.draw(SpriteSheet.sheet, 9, 5, x + 20 , y + 16, 12, 12, Drawer.FLIP_Y);
				swordHitbox = new Rectangle(Math.round(x + 20), Math.round(y + 16), 12, 24);
			}
		} else if (direction == Mob.LEFT) {
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 4, x + 8 * scale, y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 4, x, y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 5, x + 8 * scale, y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 5, x, y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);

			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 4, x - 7, y + 11, 12, 12, Drawer.FLIP_X);
				Drawer.draw(SpriteSheet.sheet, 9, 4, x - 19, y + 11, 12, 12, Drawer.FLIP_X);
				swordHitbox = new Rectangle(Math.round(x - 19), Math.round(y + 11), 24, 12);
			}
		} else if (direction == Mob.RIGHT) {
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 4, x, y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 4, x + 8 * scale, y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 5, x, y + 8 * scale, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 5, x + 8 * scale, y + 8 * scale, 8 * scale, 8 * scale);

			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 4, x + 26, y + 11, 12, 12);
				Drawer.draw(SpriteSheet.sheet, 9, 4, x + 34, y + 11, 12, 12);
				swordHitbox = new Rectangle(Math.round(x + 26), Math.round(y + 11), 24, 12);
			}
		} else if (direction == Mob.UP) {
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 5, x + 20, y - 14, 12, 12);
				Drawer.draw(SpriteSheet.sheet, 9, 5, x + 20, y - 2, 12, 12);
				swordHitbox = new Rectangle(Math.round(x + 20), Math.round(y - 14), 12, 24);
			}
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				up_flip_arms = 0b00;
				up_rarm_pos = (int)x + 8 * scale;
				up_larm_pos = (int)x + 0 * scale;
			} else {
				up_flip_arms = Drawer.FLIP_X;
				up_larm_pos = (int)x + 8 * scale;
				up_rarm_pos = (int)x + 0 * scale;

			}
			Drawer.draw(SpriteSheet.sheet, 6, 4, up_larm_pos, y, 8 * scale, 8 * scale, up_flip_arms);
			Drawer.draw(SpriteSheet.sheet, 7, 4, up_rarm_pos, y, 8 * scale, 8 * scale, up_flip_arms);

			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {

				down_legs_modifier = 0b00;
				bottom_left_pos = x + 8 * scale;
				bottom_right_pos = x;
			} else {
				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = x;
				bottom_right_pos = x + 8 * scale;
			}
			Drawer.draw(SpriteSheet.sheet, 6, 5, bottom_right_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(SpriteSheet.sheet, 7, 5, bottom_left_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
		}

		Drawer.setCol(Color.white);
	}

	public void tick() {
		rect = new Rectangle((int)x, (int)y, 32, 32);
		if (attackTicks > 0) {
			attackTicks--;
			combat.function(this, lvl, x, y);
		}
		if (foodEatTicks > 0) {
			foodEatTicks--;
			special.function(this, lvl, x, y);
		}
		if (foodEatDelay > 0) foodEatDelay --;
		if (attackDelay > 0) attackDelay--;
		if (damageTicks > 0) damageTicks--;
		if (displayLifeTicks > 0) displayLifeTicks--;
		doEffects();
	}

	public void attack() {
		getCurrentWeapon().use(this, lvl, x, y);
	}

	public boolean move(float xa, float ya) {
		super.move(xa, ya);
		float d = Options.TICK_RATE;
		if (xa > 0) {
			xa = 1;
		} else if (xa < 0) {
			xa = -1;
		}

		if (ya > 0) {
			ya = 1;
		} else if (ya < 0) {
			ya = -1;
		}


		move_ticks+=Options.MAX_TICK_RATE / d;
		if (move_ticks >= Options.MAX_TICK_RATE) {
			move_ticks = 0;
		}

		for (Mob mob : lvl.getMobs()) {
			if (mob instanceof Player && !mob.equals(this)) {
				Player player = (Player) mob;
				if (Options.PLAYERS_MIMIC && !Zordz.zordz.player.equals(player)) {
					player.move(xa, ya);
				}
			}
		}
		return true;
	}

	public void softDamage(double d) {
		super.softDamage(d);
		displayLifeTicks = Math.round(Options.TICK_RATE * 0.5f);
	}

	public void damage(double d) {
		super.damage(d);
		displayLifeTicks = Math.round(Options.TICK_RATE * 1.5f);
	}

	public boolean isDead() {
		return health <= 0;
	}

	public String getUsername() {
		return username;
	}

	public Weapon getCombatWeapon() {
		return combat;
	}

	public void setMaxHealth(int i) {
		this.max_health = i;
	}

	public int getMaxHealth() {
		return max_health;
	}

	public void setCurrentWeapon(Weapon combatWeapon) {
		if (!combatWeapon.getType().canSwitch() || !canDoSwitch) return;
		this.currentWeapon = combatWeapon.getType().getSlot();
	}

	public Weapon getCurrentWeapon() {
		if (currentWeapon == 0x00) return combat; else return special;
	}

	public Weapon getSpecialWeapon() {
		return special;
	}

	public void onDoDamage(Mob mob, double damage) {
		combat.onDoDamage(damage, mob, this, lvl, x, y);
		special.onDoDamage(damage, mob, this, lvl, x, y);
	}
}
