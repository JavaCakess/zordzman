package zordz.entity;

import java.awt.Rectangle;

import zordz.Options;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.level.Level;

public class Player extends Mob {
	float move_ticks = 0;
	public Player(Level lvl, float x, float y) {
		super(lvl, x, y);
		speed = 1f; //Speed: 60
		rect = new Rectangle((int)x, (int)y, 32, 32);
	}

	public void render() {
		int scale = 2;
		int down_legs_modifier = 0;
		int side_pos_modifier = 0;
		float bottom_left_pos = this.x + 8 * scale;
		float bottom_right_pos = this.x;
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
		} else if (direction == Mob.LEFT) {
			if (move_ticks > Options.TICK_RATE / 2) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 4, this.x, this.y, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 5, this.x + 8 * scale, this.y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 5, this.x, this.y + 8 * scale, 8 * scale, 8 * scale, Drawer.FLIP_X);
		} else if (direction == Mob.RIGHT) {
			if (move_ticks > Options.TICK_RATE / 2) {
				side_pos_modifier = 2;
			} else {
				side_pos_modifier = 0;
			}
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 4, this.x, this.y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 2 + side_pos_modifier, 5, this.x, this.y + 8 * scale, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 3 + side_pos_modifier, 5, this.x + 8 * scale, this.y + 8 * scale, 8 * scale, 8 * scale);
		} else if (direction == Mob.UP) {
			Drawer.draw(SpriteSheet.sheet, 6, 4, this.x, this.y, 8 * scale, 8 * scale);
			Drawer.draw(SpriteSheet.sheet, 7, 4, this.x + 8 * scale, this.y, 8 * scale, 8 * scale);

			if (move_ticks > Options.TICK_RATE / 2) {

				down_legs_modifier = Drawer.FLIP_X;
				bottom_left_pos = this.x;
				bottom_right_pos = this.x + 8 * scale;
			} else {
				down_legs_modifier = 0b00;
				bottom_left_pos = this.x + 8 * scale;
				bottom_right_pos = this.x;
			}
			Drawer.draw(SpriteSheet.sheet, 6, 5, bottom_right_pos, this.y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
			Drawer.draw(SpriteSheet.sheet, 7, 5, bottom_left_pos, this.y + 8 * scale, 8 * scale, 8 * scale, down_legs_modifier);
		}
	}

	public void tick() {
		rect = new Rectangle((int)x, (int)y, 32, 32);
		System.out.println(x + "   " + y);
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
