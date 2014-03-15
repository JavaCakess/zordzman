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

public class Player extends Mob {
	float move_ticks = 0;
	int max_health = 100;
	public int ticks;
	public int attackTicks = 0;
	public int attackDelay = 0;
	public int displayLifeTicks = 0;
	public Rectangle swordHitbox = new Rectangle();
	String username = "Player";
	Weapon combat = Weapon.minizord;
	public Player(Level lvl, float x, float y, String username) {
		super(lvl, x, y);
		health = max_health;
		this.username = username;
		speed = 1f; //Speed: 60
		rect = new Rectangle((int)x, (int)y, 32, 32);
		combatEquip(Weapon.minizord);
	}

	public void combatEquip(Weapon weapon) {
		health = max_health;
		speed = 1f; //Speed: 60
		combat = weapon;
		combat.equip(this);
	}

	public float healthPerc() {
		return (float)health / max_health;
	}

	public void render() {
		
		// Debug
		burnRate = 15;
		// END
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f, 0.3f}, true);
		NewGLHandler.draw2DRect((this.x + 12) - (username.length() * 4), this.y - 20, (8 * username.length()) + 4, 9, true);
		
		if (displayLifeTicks > 0) {
			NewGLHandler.draw2DRect((this.x + 18) - (36), this.y - 11, 64, 8, true);
			if (healthPerc() > 0.66) {
				Drawer.setCol(Color.green);
			} else if (healthPerc() > 0.33) {
				Drawer.setCol(Color.orange);
			} else if (healthPerc() > 0) {
				Drawer.setCol(Color.red);
			}
			NewGLHandler.draw2DRect((this.x + 14) - (27), this.y - 10, 50 * ((float)health / max_health), 4, true);
			Drawer.setCol(Color.black);
			NewGLHandler.draw2DRect((this.x + 14) - (27), this.y - 9, 50, 4, false);
		}
		Drawer.setCol(Color.white);
		Text.render(username, (this.x + 12) - (username.length() * 4), this.y - 20, 8, 8);

		int scale = 2;
		int down_legs_modifier = 0;
		int side_pos_modifier = 0;
		int up_flip_arms = 0b00;
		int up_rarm_pos = 8;
		int up_larm_pos = 0;
		float bottom_left_pos = this.x + 8 * scale;
		float bottom_right_pos = this.x;
		if (damageTicks > 0) {
			Drawer.setCol(Color.red);
		}
		// Down
		if (direction == Mob.DOWN) {
			Drawer.draw(SpriteSheet.sheet, 0, 4, this.x, this.y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 1, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale);

			if (move_ticks > Options.TICK_RATE / 2) {

				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = this.x;
				bottom_right_pos = this.x + 8 * scale;
			} else {
				down_legs_modifier = 0b00;
				bottom_left_pos = this.x + 8 * scale;
				bottom_right_pos = this.x;
			}
			Drawer.draw(SpriteSheet.sheet, 1, 5, bottom_left_pos, this.y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(SpriteSheet.sheet, 0, 5, bottom_right_pos, this.y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 5, this.x + 20, this.y + 28, 12, 12, Drawer.FLIP_Y);
				Drawer.draw(SpriteSheet.sheet, 9, 5, this.x + 20 , this.y + 16, 12, 12, Drawer.FLIP_Y);
				swordHitbox = new Rectangle(Math.round(this.x + 20), Math.round(this.y + 16), 12, 24);
			}
		} else if (direction == Mob.LEFT) {
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 4, this.x, this.y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 5, this.x + 8 * scale, this.y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 5, this.x, this.y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);

			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 4, this.x - 7, this.y + 11, 12, 12, Drawer.FLIP_X);
				Drawer.draw(SpriteSheet.sheet, 9, 4, this.x - 19, this.y + 11, 12, 12, Drawer.FLIP_X);
				swordHitbox = new Rectangle(Math.round(this.x - 19), Math.round(this.y + 11), 24, 12);
			}
		} else if (direction == Mob.RIGHT) {
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 4, this.x, this.y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 5, this.x, this.y + 8 * scale, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 5, this.x + 8 * scale, this.y + 8 * scale, 8 * scale, 8 * scale);

			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 4, this.x + 26, this.y + 11, 12, 12);
				Drawer.draw(SpriteSheet.sheet, 9, 4, this.x + 34, this.y + 11, 12, 12);
				swordHitbox = new Rectangle(Math.round(this.x + 26), Math.round(this.y + 11), 24, 12);
			}
		} else if (direction == Mob.UP) {
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 5, this.x + 20, this.y - 14, 12, 12);
				Drawer.draw(SpriteSheet.sheet, 9, 5, this.x + 20, this.y - 2, 12, 12);
				swordHitbox = new Rectangle(Math.round(this.x + 20), Math.round(this.y - 14), 12, 24);
			}
			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {
				up_flip_arms = 0b00;
				up_rarm_pos = (int)this.x + 8 * scale;
				up_larm_pos = (int)this.x + 0 * scale;
			} else {
				up_flip_arms = Drawer.FLIP_X;
				up_larm_pos = (int)this.x + 8 * scale;
				up_rarm_pos = (int)this.x + 0 * scale;

			}
			Drawer.draw(SpriteSheet.sheet, 6, 4, up_larm_pos, this.y, 8 * scale, 8 * scale, up_flip_arms);
			Drawer.draw(SpriteSheet.sheet, 7, 4, up_rarm_pos, this.y, 8 * scale, 8 * scale, up_flip_arms);

			if (move_ticks > Options.TICK_RATE / 2 || attackTicks > 0) {

				down_legs_modifier = 0b00;
				bottom_left_pos = this.x + 8 * scale;
				bottom_right_pos = this.x;
			} else {
				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = this.x;
				bottom_right_pos = this.x + 8 * scale;
			}
			Drawer.draw(SpriteSheet.sheet, 6, 5, bottom_right_pos, this.y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(SpriteSheet.sheet, 7, 5, bottom_left_pos, this.y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
		}

		Drawer.setCol(Color.white);
	}

	public void tick() {
		rect = new Rectangle((int)x, (int)y, 32, 32);
		if (attackTicks > 0) {
			attackTicks--;
			combat.function(this, lvl, x, y);
		}
		if (attackDelay > 0) attackDelay--;
		if (damageTicks > 0) damageTicks--;
		if (displayLifeTicks > 0) displayLifeTicks--;
		burn();
	}

	public void attack() {
		combat.use(this, lvl, x, y);
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

}
