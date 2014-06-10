package zordz.net;

import zordz.entity.PlayerMP;


/**
 * 	<table border="1">
 * 	<th>Field</th><th>Type</th><th>Desc</th>
 * 	<tr><td> username </td><td> String </td><td> Returns the username of the player. </td></tr>
 *  <tr><td> x </td><td> Float </td><td> The x position of the player. </td></tr>
 *  <tr><td> y </td><td> Float </td><td> The y position of the player. </td></tr>
 *  <tr><td> dir </td><td> Integer </td><td> The direction the player is facing. </td></tr>
 *  <tr><td> health </td><td> Integer </td><td> How much health the player currently possesses.</td></tr>
 *  <tr><td> max_health </td><td> Integer</td><td> The maximum health of the player.</td></tr>
 *  <tr><td> food_counter </td><td> Integer</td><td> The amount of food the player currently has.</td></tr>
 *  <tr><td> current_wep </td><td> Integer</td><td> The current weapon the player has in hand. (0 = Combat, 1 = Special) </td><tr>
 *  <tr><td> combat_wep </td><td> Integer</td><td> The ID of the combat weapon the player has.</td></tr>
 *  <tr><td> special_wep </td><td> Integer</td><td> The ID of the special weapon the player has.</td></tr>
 *  <tr><td> bleed_ticks </td><td> Integer</td><td> How many ticks until the player stops bleeding. </td><tr>
 *  <tr><td> burn_ticks </td><td> Integer</td><td> How many ticks until the player stops burning. </td><tr>
 *  <tr><td> burn_rate </td><td> Integer</td><td> The burn rate of the player in ticks. </td><tr>
 *  </table>
 */
public class Packet05PlayerInfo extends Packet {

	private String username;
	private float x, y;
	private int dir, health,  max_health,  food_counter,  current_wep,
		combat_wep,  special_wep,  bleed_ticks,  burn_ticks, burn_rate;
	
	public Packet05PlayerInfo(String username, float x, float y, int dir,
			int health, int max_health, int food_counter, int current_wep,
			int combat_wep, int special_wep, int bleed_ticks, int burn_ticks,
			int burn_rate) {
		super(05);
		this.username = username;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.health = health;
		this.max_health = max_health;
		this.food_counter = food_counter;
		this.current_wep = current_wep;
		this.combat_wep = combat_wep;
		this.special_wep = special_wep;
		this.bleed_ticks = bleed_ticks;
		this.burn_ticks = burn_ticks;
		this.burn_rate = burn_rate;
	}

	public Packet05PlayerInfo(byte[] data) {
		super(0x05);
		String strData = readData(data);
		String[] dataArray = strData.split(",");
		username = dataArray[0];
		x = (Float.parseFloat(dataArray[1]));
		y = Float.parseFloat(dataArray[2]);
		dir = Integer.parseInt(dataArray[3]);
		health = Integer.parseInt(dataArray[4]);
		max_health = Integer.parseInt(dataArray[5]);
		food_counter = Integer.parseInt(dataArray[6]);
		current_wep = Integer.parseInt(dataArray[7]);
		combat_wep = Integer.parseInt(dataArray[8]);
		special_wep = Integer.parseInt(dataArray[9]);
		bleed_ticks = Integer.parseInt(dataArray[10]);
		burn_ticks = Integer.parseInt(dataArray[11]);
		burn_rate = Integer.parseInt(dataArray[12]);
	}

	public Packet05PlayerInfo(PlayerMP player) {
		super(0x05);
		this.username = player.getUsername();
		this.x = player.getX();
		this.y = player.getY();
		this.dir = player.direction;
		this.health = player.getHealth();
		this.max_health = player.getMaxHealth();
		this.food_counter = player.foodCounter;
		this.current_wep = player.getCurrentWeapon().getType().getSlot();
		this.combat_wep = player.getCombatWeapon().getID();
		this.special_wep = player.getSpecialWeapon().getID();
		this.bleed_ticks = player.getBleedTicks();
		this.burn_ticks = player.getBurnTicks();
		this.burn_rate = player.getBurnRate();
		
	}

	public String getUsername() {
		return username;
	}

	public float getFloatField(String a) {
		switch (a) {
		case "x":
			return x;
		case "y":
			return y;
		}
		return 0.0f;
	}
	
	public int getIntField(String a) {
		switch (a) {
		case "dir":
			return dir;
		case "health":
			return health;
		case "max_health":
			return max_health;
		case "food_counter":
			return food_counter;
		case "current_wep":
			return current_wep;
		case "combat_wep":
			return combat_wep;
		case "special_wep":
			return special_wep;
		case "bleed_ticks":
			return bleed_ticks;
		case "burn_ticks":
			return burn_ticks;
		case "burn_rate":
			return burn_rate;
		default:
			return 0;
		}
	}
	
	public byte getByteField(String a) {
		return 0;
	}

	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	public byte[] getData() {
		return ("05" + username + "," + x + "," + y + "," + dir + ","
			   + health + "," + max_health + "," + food_counter + "," + current_wep + ","
			   + combat_wep + "," + special_wep + "," + bleed_ticks + "," + burn_ticks).getBytes();
	}
}
