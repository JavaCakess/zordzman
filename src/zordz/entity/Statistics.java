package zordz.entity;

public class Statistics {
	
	public static final int DEFAULT_BURN_RATE = 15;
	public float burnPercentage = 1f;
	public static final int DEFAULT_MAX_HEALTH = 100;
	public int healthAdditive;
	public static final float DEFAULT_SPEED = 1f;
	public float speedPercentage = 1f;
	
	public void reset() {
		healthAdditive = 0;
		speedPercentage = 1f;
		burnPercentage = 1f;
	}
}
