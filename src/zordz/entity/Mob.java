package zordz.entity;

import java.awt.Rectangle;

import zordz.level.Level;
import zordz.level.tile.Tile;

public abstract class Mob extends Entity {

	protected int health;
	public Rectangle rect;
	protected float speed;
	public static final int UP = 00, DOWN = 01, LEFT = 02, RIGHT = 03;
	boolean isMoving = false;
	public int direction = 0x01; //Up = 0x00, Down = 0x01, Left = 0x02, Right = 0x03;
	int tile_check_w = 2;
	int tile_check_h = 2;
	public Mob(Level lvl, float x, float y) {
		super(lvl, x, y);
	}

	public int getHealth() {
		return health;
	}
	
	public void move(float xa, float ya) {
		if (canMove()) {
			x+=xa;
			y+=ya;
		}
	}
	
	public boolean canMove() {
		int a = Math.round(x / 32);
		int b = Math.round(y / 32);
		if (a - 1 < 0 || b - 1 < 0) {
			return false;
		}
		for (int x = a - 1; x < a + tile_check_w; x++) {
			for (int y = b - 1; y < b + tile_check_h; y++) {
				if (!Tile.getByID(lvl.getTileIDAt(x, y)).pass(lvl, this, x*32, y*32)) {
					if (this.x < x*32 && direction == RIGHT) {
						this.x-=speed;
						return false;
					} else if (this.x > x*32 && direction == LEFT) {
						this.x+=speed;
						return false;
					} else if (this.y < y*32 && direction == DOWN) {
						this.y-=speed;
						return false;
					} else if (this.y > y*32 && direction == UP) {
						this.y+=speed;
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
