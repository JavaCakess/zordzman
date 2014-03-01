package zordz.entity;

import java.awt.Color;
import java.awt.Rectangle;

import zordz.Options;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Player extends Mob {
	float move_ticks = 0;
	int max_health = 100;
	int ticks;
	int attackTicks = 0, attackDelay = 0;
	public Player(Level lvl, float x, float y) {
		super(lvl, x, y);
		health = max_health;
		speed = 1f; //Speed: 60
		rect = new Rectangle((int)x, (int)y, 32, 32);
	}

	public void render() {
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
			}
		} else if (direction == Mob.UP) {
			if (attackTicks > 0) {
				Drawer.draw(SpriteSheet.sheet, 8, 5, this.x + 20, this.y - 14, 12, 12);
				Drawer.draw(SpriteSheet.sheet, 9, 5, this.x + 20, this.y - 2, 12, 12);
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
		rect = new Rectangle((int)x + 6, (int)y + 5, 12, 24);
		if (attackTicks > 0) attackTicks--;
		if (attackDelay > 0) attackDelay--;
		if (damageTicks > 0) damageTicks--;
	}

	public void attack() {
		if (attackDelay < 1) {
			attackTicks = (int) ((int)Options.TICK_RATE / 6f);
			attackDelay = (int) ((int)Options.TICK_RATE / 3f);
			SoundPlayer.play(Sound.melee_swing);
		}
	}
	
	

	public void move(float xa, float ya) {
		if (!super.canMove()) return ;
		float d = Options.TICK_RATE;
		x+=xa*((float)Options.MAX_TICK_RATE / d);
		y+=ya*((float)Options.MAX_TICK_RATE / d);

		move_ticks+=Options.MAX_TICK_RATE / d;
		if (move_ticks >= Options.MAX_TICK_RATE) {
			move_ticks = 0;
		}
	}

}
