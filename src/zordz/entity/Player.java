package zordz.entity;

import java.awt.Color;
import java.awt.Rectangle;

import zordz.Options;
import zordz.Zordz;
import zordz.backpacks.Backpack;
import zordz.gfx.Drawer;
import zordz.gfx.NewGLHandler;
import zordz.gfx.SpriteSheet;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.util.Util;
import zordz.weapons.Weapon;
import zordz.weapons.Weapon.WeaponType;

/**
 * ID: 0
 * @author JavaCakess
 *
 */
public class Player extends Mob {
	public Statistics stats = new Statistics();
	public float move_ticks = 0;

	public boolean specialAvailable = true;
	public int foodCounter = 1;
	public static final int MAX_FOOD = 5;
	public int foodEatTicks, foodEatDelay;
	public int ticks;
	public int attackTicks = 0;
	public int attackDelay = 0;
	public int displayLifeTicks = 0;
	public Rectangle swordHitbox = new Rectangle();
	String username = "Player";
	Weapon combat = Weapon.zord;
	Weapon special = Weapon.crossed_shield;
	int currentWeapon = 0;
	public boolean canDoSwitch = true;
	public Backpack backpack = null;
	boolean render = true;
	boolean bot = false;

	public byte[] down_gfx = {
			0, 4,
			1, 4,
			1, 5,
			0, 5
	};

	public byte[] right_gfx = {
			2, 4,
			3, 4,
			2, 5,
			3, 5
	};

	public byte[] up_gfx = {
			6, 4,
			7, 4,
			6, 5,
			7, 5
	};

	public Player(Level lvl, float x, float y, String username) {
		super(lvl, x, y);
		id = 0;
		max_health = Statistics.DEFAULT_MAX_HEALTH;
		health = max_health;
		this.username = username;
		speed = 1f; //Speed: 60
		rect = new Rectangle((int)x, (int)y + 16, 32, 16);
		equip(Options.combatWeap, Options.specialWeap);
	}

	public boolean canDoSwitch() {
		return canDoSwitch;
	}

	public void equip(Weapon combat, Weapon special) {
		//Set default stats
		max_health = Statistics.DEFAULT_MAX_HEALTH;
		speed = Statistics.DEFAULT_SPEED;
		burnRate = Statistics.DEFAULT_BURN_RATE;
		stats.healthAdditive = 0;
		stats.burnPercentage = 1;
		stats.speedPercentage = 1;
		this.combat = combat;
		this.special = special;
		combat.equip(this);
		special.equip(this);
		max_health += stats.healthAdditive;
		health = max_health;
		speed *= stats.speedPercentage;
		burnRate = (int)(burnRate * stats.burnPercentage);
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

		if (!render) return;

		byte scale = 2;
		byte down_legs_modifier = 0;
		byte side_pos_modifier = 0;
		int up_flip_arms = 0b00;
		int up_rarm_pos = 8;
		int up_larm_pos = 0;
		float bottom_left_pos = x + 8 * scale;
		float bottom_right_pos = x;

		SpriteSheet player_gfx = SpriteSheet.sheet;

		byte down_gfx_0 = down_gfx[0];
		byte down_gfx_1 = down_gfx[1];
		byte down_gfx_2 = down_gfx[2];
		byte down_gfx_3 = down_gfx[3];
		byte down_gfx_4 = down_gfx[4];
		byte down_gfx_5 = down_gfx[5];
		byte down_gfx_6 = down_gfx[6];
		byte down_gfx_7 = down_gfx[7];

		byte right_gfx_0 = right_gfx[0];
		byte right_gfx_1 = right_gfx[1];
		byte right_gfx_2 = right_gfx[2];
		byte right_gfx_3 = right_gfx[3];
		byte right_gfx_4 = right_gfx[4];
		byte right_gfx_5 = right_gfx[5];
		byte right_gfx_6 = right_gfx[6];
		byte right_gfx_7 = right_gfx[7];

		byte up_gfx_0 = up_gfx[0];
		byte up_gfx_1 = up_gfx[1];
		byte up_gfx_2 = up_gfx[2];
		byte up_gfx_3 = up_gfx[3];
		byte up_gfx_4 = up_gfx[4];
		byte up_gfx_5 = up_gfx[5];
		byte up_gfx_6 = up_gfx[6];
		byte up_gfx_7 = up_gfx[7];

		if (damageTicks > 0) {
			if (burnTicks > 0) Drawer.setCol(new Color(255, 110, 0));
			else Drawer.setCol(Color.red);
		}

		if (backpack != null) {
			down_gfx_0 = backpack.down_gfx[0];
			down_gfx_1 = backpack.down_gfx[1];
			down_gfx_2 = backpack.down_gfx[2];
			down_gfx_3 = backpack.down_gfx[3];

			down_gfx_4 = backpack.down_gfx[4];
			down_gfx_5 = backpack.down_gfx[5];
			down_gfx_6 = backpack.down_gfx[6];
			down_gfx_7 = backpack.down_gfx[7];

			right_gfx_0 = backpack.right_gfx[0];
			right_gfx_1 = backpack.right_gfx[1];
			right_gfx_2 = backpack.right_gfx[2];
			right_gfx_3 = backpack.right_gfx[3];

			right_gfx_4 = backpack.right_gfx[4];
			right_gfx_5 = backpack.right_gfx[5];
			right_gfx_6 = backpack.right_gfx[6];
			right_gfx_7 = backpack.right_gfx[7];

			up_gfx_0 = backpack.up_gfx[0];
			up_gfx_1 = backpack.up_gfx[1];
			up_gfx_2 = backpack.up_gfx[2];
			up_gfx_3 = backpack.up_gfx[3];

			up_gfx_4 = backpack.up_gfx[4];
			up_gfx_5 = backpack.up_gfx[5];
			up_gfx_6 = backpack.up_gfx[6];
			up_gfx_7 = backpack.up_gfx[7];

			player_gfx = backpack.sheet;
		}

		Weapon currWeap = this.getCurrentWeapon();
		// Down
		if (direction == Mob.DOWN) {
			Drawer.draw(player_gfx, down_gfx_0, down_gfx_1, x, y, 8 * scale, 8 * scale);
			Drawer.draw(player_gfx, down_gfx_2, down_gfx_3, x + 8 * scale, y, 8 * scale, 8 * scale);

			if (move_ticks > Options.TICK_RATE / 2) {

				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = x;
				bottom_right_pos = x + 8 * scale;
			} else {
				down_legs_modifier = 0b00;
				bottom_left_pos = x + 8 * scale;
				bottom_right_pos = x;
			}
			Drawer.draw(player_gfx, down_gfx_4, down_gfx_5, bottom_left_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(player_gfx, down_gfx_6, down_gfx_7, bottom_right_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_u_x_1, currWeap.handGFX_u_y_1, x + 20, y + 28, currWeap.handGFX_width, currWeap.handGFX_height, Drawer.FLIP_Y);
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_u_x_2, currWeap.handGFX_u_y_2, x + 20 , y + 16, currWeap.handGFX_width, currWeap.handGFX_height, Drawer.FLIP_Y);
				swordHitbox = new Rectangle(Math.round(x + 20), Math.round(y + 16), 12, 24);
			}
		} else if (direction == Mob.LEFT) {
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(player_gfx, right_gfx_0 + side_pos_modifier, right_gfx_1, x + 8 * scale, y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(player_gfx, right_gfx_2 + side_pos_modifier, right_gfx_3, x, y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(player_gfx, right_gfx_4 + side_pos_modifier, right_gfx_5, x + 8 * scale, y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(player_gfx, right_gfx_6 + side_pos_modifier, right_gfx_7, x, y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);

			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_r_x_1, currWeap.handGFX_r_y_1, x - 7, y + 11, currWeap.handGFX_width, currWeap.handGFX_height, Drawer.FLIP_X);
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_r_x_2, currWeap.handGFX_r_y_2, x - 19, y + 11, currWeap.handGFX_width, currWeap.handGFX_height, Drawer.FLIP_X);
				swordHitbox = new Rectangle(Math.round(x - 19), Math.round(y + 11), 24, 12);
			}
		} else if (direction == Mob.RIGHT) {
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(player_gfx, right_gfx_0 + side_pos_modifier, right_gfx_1, x, y, 8 * scale, 8 * scale);
			Drawer.draw(player_gfx, right_gfx_2 + side_pos_modifier, right_gfx_3, x + 8 * scale, y, 8 * scale, 8 * scale);
			Drawer.draw(player_gfx, right_gfx_4 + side_pos_modifier, right_gfx_5, x, y + 8 * scale, 8 * scale, 8 * scale);
			Drawer.draw(player_gfx, right_gfx_6 + side_pos_modifier, right_gfx_7, x + 8 * scale, y + 8 * scale, 8 * scale, 8 * scale);
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_r_x_1, currWeap.handGFX_r_y_1, x + 26, y + 11, currWeap.handGFX_width, currWeap.handGFX_height);
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_r_x_2, currWeap.handGFX_r_y_2, x + 34, y + 11, currWeap.handGFX_width, currWeap.handGFX_height);
				swordHitbox = new Rectangle(Math.round(x + 26), Math.round(y + 11), 24, 12);
			}
		} else if (direction == Mob.UP) {
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_u_x_1, currWeap.handGFX_u_y_1, x + 20, y - 14, currWeap.handGFX_width, currWeap.handGFX_height);
				Drawer.draw(SpriteSheet.sheet, currWeap.handGFX_u_x_2, currWeap.handGFX_u_y_2, x + 20, y - 2, currWeap.handGFX_width, currWeap.handGFX_height);
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
			Drawer.draw(player_gfx, up_gfx_0, up_gfx_1, up_larm_pos, y, 8 * scale, 8 * scale, up_flip_arms);
			Drawer.draw(player_gfx, up_gfx_2, up_gfx_3, up_rarm_pos, y, 8 * scale, 8 * scale, up_flip_arms);

			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {

				down_legs_modifier = 0b00;
				bottom_left_pos = x + 8 * scale;
				bottom_right_pos = x;
			} else {
				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = x;
				bottom_right_pos = x + 8 * scale;
			}
			Drawer.draw(player_gfx, up_gfx_4, up_gfx_5, bottom_right_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(player_gfx, up_gfx_6, up_gfx_7, bottom_left_pos, y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
		}

		Drawer.setCol(Color.white);
	}

	public void tick() {
		rect = new Rectangle((int)x, (int)y + 16, 32, 16);
		if (attackTicks > 0) {
			attackTicks--;
			combat.function(this, lvl, x, y);
		}
		if (foodEatTicks > 0) {
			foodEatTicks--;
			special.function(this, lvl, x, y);
		}
		if (foodEatDelay > 0) foodEatDelay --;
		if (attackDelay > 0) {
			attackDelay--;
			if (ticks % 5 > 0 && ticks % 5 < 3) {
				render = false;
			} else render = true;
		}
		if (damageTicks > 0) damageTicks--;
		if (displayLifeTicks > 0) displayLifeTicks--;
		if (foodCounter > 0 && special.getType() == WeaponType.FOOD) {
			specialAvailable = true;
		}
		doEffects();
	}

	public void attack() {
		getCurrentWeapon().use(this, lvl, x, y);
	}

	public boolean move(float xa, float ya, boolean anim, boolean doMove) {
		super.move(xa, ya, doMove);
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

		if (anim) {
			move_ticks+=Options.MAX_TICK_RATE / d;
			if (move_ticks >= Options.MAX_TICK_RATE) {
				move_ticks = 0;
			}
		}

		for (Entity ent : lvl.getEntities()) {
			if (!(ent instanceof Player)) continue;
			Player mob = (Player) ent;
			if (!mob.equals(this) && mob.isBot()) {
				Player player = (Player) mob;
				if (Options.PLAYERS_MIMIC && !Zordz.zordz.player.equals(player)) {
					player.move(xa, ya, doMove);
				}
			}
		}
		return true;
	}

	public boolean isBot() {
		return bot;
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
		if (!(combatWeapon.getType() == WeaponType.FOOD) && foodCounter < 0) return;
		if (combatWeapon.equals(special) && !specialAvailable) return;
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

	public boolean eat(float delay, float ticks) {
		boolean result = foodCounter > 0 && foodEatDelay < 1;
		if (result) {
			foodCounter --;
			foodEatDelay = (int) ((int)Options.TICK_RATE * delay);
			foodEatTicks = (int) ((int)Options.TICK_RATE * ticks);
		}

		return result;
	}

	public void setSpecialAvailable(boolean b) {
		specialAvailable = b;
	}

	public void setCombatWeapon(int id) {
		combat = Util.getByID(id);
	}

	public void setSpecialWeapon(int id) {
		special = Util.getByID(id);
	}

	public void giveFood(int i) {
		if (foodCounter + i > MAX_FOOD) {
			foodCounter = MAX_FOOD;
			return;
		}
		foodCounter += i;
	}

	public void setBot(boolean b) {
		bot = b;
	}
}
