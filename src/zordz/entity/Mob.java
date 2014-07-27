package zordz.entity;

import java.awt.Rectangle;

import zordz.Options;
import zordz.level.Level;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public abstract class Mob extends Entity {

	int health, max_health;
	public Rectangle rect;
	protected float speed;
	int damageTicks;
	int burnTicks;
	int bleedTicks;
	int burnRate = 15;
	int bleedRate = 30;
	Mob bleedCauser;
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

	public int getBleedTicks() {
		return bleedTicks;
	}

	public void setBleedTicks(int bt) {
		bleedTicks = bt;
	}

	public boolean move(float xa, float ya, boolean doMove) {


		int xMin = ((int)rect.x / 32) - 2;
		int xMax = ((int)rect.x / 32) + 2;
		int yMin = ((int)rect.y / 32) - 2;
		int yMax = ((int)rect.y / 32) + 2;
		if (bleedTicks > 0) xa *= 0.75;


		for (int x = xMin; x < xMax; x++) {
			for (int y = yMin; y < yMax; y++) {
				if (!lvl.getTileAt(x, y).pass(lvl, this, x * 32, y * 32)) {
					System.out.println(this.y + "   " + ((int)rect.y >> 5));
					if (direction == UP && (rect.y < (y+1) << 5 && rect.y > ((y+1) << 5) - 4)) {
						return false;
					} else if (direction == DOWN && (rect.y + rect.height > y << 5 && rect.y + rect.height < (y << 5) + 4)) {
						return false;
					} else if (direction == LEFT && (rect.x < (x+1) << 5 && rect.x > ((x+1) << 5) - 4) ) {
						return false;
					} else if (direction == RIGHT && (rect.x + rect.width > x << 5 && rect.x + rect.width < (x << 5) + 4)) {
						return false;
					}
				}
			}
		}
		if (doMove) {
			x += xa * (Options.MAX_TICK_RATE / Options.TICK_RATE);
			y += ya * (Options.MAX_TICK_RATE / Options.TICK_RATE);
		}
		return true;
	}

	public void softDamage(double d, Player p) {
		softDamage(d);
		p.onDoDamage(this, d);
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
		if (d < 1) {
			d = 1;
		}
		int damage = (int)Math.round(d);
		health += damage;
		if (health > max_health) {
			health = max_health;
		}
		if (Options.DAMAGE_FEEDBACK) {
			TextParticle tp = new TextParticle(lvl, (x + 12) - ("+" + damage).length() * 8, y - 16, ("+" + damage), new float[]{0f, 1f, 0});
			lvl.add(tp);
		}
	}

	public void damage(double d) {
		if (damageTicks > 0) {
			return;
		}
		int damage = (int)Math.round(d);
		health -= damage;
		if (Options.DAMAGE_FEEDBACK) {
			TextParticle tp = new TextParticle(lvl, (x + 12) - ("" + -damage).length() * 8, y - 16, ("" + -damage), new float[]{1.0f, 0, 0});
			lvl.add(tp);
		}
		SoundPlayer.play(Sound.hurt, 0.5f);
		damageTicks = 20;
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

	public void doEffects() {
		if (burnTicks > 0) {
			burnTicks--;
			if (burnTicks % burnRate * (int)(Options.TICK_RATE / Options.MAX_TICK_RATE) == 0) {
				softDamage(1);
				SoundPlayer.play(Sound.burn_small, 0.5f);
			}
		}
		if (bleedTicks > 0) {
			bleedTicks--;
			if (bleedTicks % bleedRate * (int)(Options.TICK_RATE / Options.MAX_TICK_RATE) == 0) {
				if (bleedCauser != null && bleedCauser instanceof Player) softDamage(1, (Player)bleedCauser);
				else softDamage(1);
				SoundPlayer.play(Sound.bleed_small, 0.6f);
			}
		} else {
			bleedCauser = null;
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

	public void damage(int damage, Player player) {
		damage(damage);
		player.onDoDamage(this, damage);
	}

	public void setBleedTicks(int i, Player player) {
		bleedCauser = player;
		setBleedTicks(i);
	}
}
