package zordz.entity;

import java.awt.Rectangle;

import zordz.Options;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public abstract class Mob extends Entity {

	public int health;
	public Rectangle rect;
	protected float speed;
	int damageTicks;
	int burnTicks;
	int burnRate = 5;
	public static final int UP = 00, DOWN = 01, LEFT = 02, RIGHT = 03;
	boolean isMoving = false;
	public int direction = 0x01; //Up = 0x00, Down = 0x01, Left = 0x02, Right = 0x03;
	int tile_check_w = 2;
	int tile_check_h = 2;
	public boolean removeOnKill = true;
	public Mob(Level lvl, float x, float y) {
		super(lvl, x, y);
	}

	public int getHealth() {
		return health;
	}

	public boolean isSolid(int x, int y) {
		if (lvl.getTileAt(x >> 5, y >> 5).isSolid()) {
			return true;
		}
		return false;
	}

	public Tile getPointIn(int x, int y) {
		return lvl.getTileAt(x >> 5, y >> 5);
	}

	public boolean canMove() {
		return true;
		//		System.out.println(rect.x + "   " + rect.width);
		//		
		//
		//		System.out.println("------------------------");
		//		for (int x = xMin; x < xMax; x++) {
		//			for (int y = yMin; y < yMax; y++) {
		//				
		//				System.out.println("Player X: " + this.x + ", X: " + x);
		//				System.out.println("Tile ID: " + lvl.getTileAt(x >> 5, y >> 5));
		//				System.out.println("Tile Pos: " + (x >> 5) + ", " + (y >> 5));
		//				if (isSolid(x, y)) {
		//					
		//					ax = (x >> 5) * 32;
		//					ay = (y >> 5) * 32;
		//					if (direction == LEFT) {
		//						rect.x = ax;
		//						return false;
		//					} else if (direction == RIGHT) {
		//						rect.x = (ax + 32);
		//						return false;
		//					} else if (direction == DOWN) {
		//						rect.y = (ay);
		//						return false;
		//					} else if (direction == UP) {
		//						rect.y = ay;
		//						return false;
		//					}
		//				}
		//			}
		//		}
		//		return true;
	}

	public boolean move(float xa, float ya) {
		int xMin = ((int)x / 32) - 2;
		int xMax = ((int)x / 32) + 2;
		int yMin = ((int)y / 32) - 2;
		int yMax = ((int)y / 32) + 2;

		for (int x = xMin; x < xMax; x++) {
			for (int y = yMin; y < yMax; y++) {
				if (!lvl.getTileAt(x, y).pass(lvl, this, x * 32, y * 32)) {
					System.out.println("no");
					if (direction == UP && rect.y >= (y * 32) + 32) {
						y+=speed;
						return false;
					}
					if (direction == DOWN && rect.y < (y * 32)) {
						y-=speed;
						return false;
					}
					if (direction == LEFT && rect.x > (x * 32)) {
						x+=speed;
						return false;
					}
					if (direction == RIGHT && rect.x < (x * 32) + 32) {
						x-=speed;
						return false;
					}
				}
			}
		}
		x += xa * (Options.MAX_TICK_RATE / Options.TICK_RATE);
		y += ya * (Options.MAX_TICK_RATE / Options.TICK_RATE);
		return true;
	}

	public void softDamage(double d) {
		int damage = (int)Math.ceil(d);
		health -= damage;
		damageTicks = 15;
		for (int k = 0; k < 2; k++) {
			Particle p = new Particle(lvl, x + 12, y + 10, 2, new float[]{1f, 0f, 0f});
			lvl.add(p);
		}
		if (health <= 0) {
			if (removeOnKill) {
				lvl.remove(this);
			}
		}
	}

	public void heal(double d) {
		int damage = (int)Math.ceil(d);
		health += damage;
		if (Options.DAMAGE_FEEDBACK) {
			TextParticle tp = new TextParticle(lvl, (x + 12) - ("+" + damage).length() * 8, y - 16, ("+" + damage), new float[]{0f, 1f, 0});
			lvl.add(tp);
		}
	}

	public void damage(double d) {
		if (damageTicks > 0) {
			return;
		}
		int damage = (int)Math.ceil(d);
		health -= damage;
		if (Options.DAMAGE_FEEDBACK) {
			TextParticle tp = new TextParticle(lvl, (x + 12) - ("" + -damage).length() * 8, y - 16, ("" + -damage), new float[]{1.0f, 0, 0});
			lvl.add(tp);
		}
		SoundPlayer.play(Sound.hurt, 0.5f);
		damageTicks = 15;
		for (int k = 0; k < 4; k++) {
			Particle p = new Particle(lvl, x + 12, y + 10, 3, new float[]{1f, 0f, 0f});
			lvl.add(p);
		}
		if (health <= 0) {
			if (removeOnKill) {
				lvl.remove(this);
			}
		}
	}

	public int getBurnTicks() {
		return burnTicks;
	}

	public int getBurnRate() {
		return burnRate;
	}

	public void burn() {
		if (burnTicks > 0) {
			burnTicks--;
			if (burnTicks % burnRate * (int)(Options.TICK_RATE / Options.MAX_TICK_RATE) == 0) {
				softDamage(1);
				SoundPlayer.play(Sound.burn_small, 0.5f);
			}
		}
	}

	public void setBurnRate(int b) {
		burnRate = b;
	}

	public void setBurnTicks(int i) {
		burnTicks = i;
	}

	public void setHealth(int i) {
		health = i;
	}

	public boolean intersects(Rectangle rect) {
		return this.rect.intersects(rect);
	}

	public boolean intersects(Mob mob) {
		return this.rect.intersects(mob.rect);
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

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
