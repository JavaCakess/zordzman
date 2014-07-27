package zordz.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import zordz.Options;
import zordz.Zordz;
import zordz.entity.Entity;
import zordz.entity.PlayerMP;
import zordz.level.tile.Tile;
import zordz.net.Packet05PlayerInfo;
import zordz.util.IOTools;

public class Level {

	public static final int WIDTH = 20;
	public static final int HEIGHT = 15;
	public int width;
	public int height;
	public int tickCount = 0;
	private byte[][] tiles;
	//private byte[][] tile_data;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	public int botcount = 0;

	public String script = "";

	public int lastUID = 0;

	public Level() {

	}

	public static Level loadMap(String path, String fileName) {
		// First the MAP loading.
		// Load the .lvl bit.
		Level l = Level.loadLevel(path + File.separatorChar + fileName.substring(0, fileName.length()-4) + ".lvl");
		// Then the entities.
		ArrayList<String> data = 
				IOTools.readFile(new File(path + File.separatorChar + 
						fileName.substring(0, fileName.length()-4) + ".ents"));
		for (String s : data) {
			l.add(Entity.parseEntity(l, s));
		}

		// Attach the Lua script.

		try {
			File script = new File(path + File.separatorChar + "script.lua");
			if (!script.exists()) return l;
			BufferedReader reader = new BufferedReader(new FileReader(script));
			String ln;
			while ((ln = reader.readLine()) != null) l.script = l.script.concat(ln + "\n");

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return l;
	}

	public static Level loadLevel(String path) {
		int yPos = 0;
		Scanner x = null;

		try {
			x = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR] Could not find file " + path + "!");
		}
		int wd = 0, ht = 0;
		while (x.hasNextLine()) {
			String row = x.nextLine();
			byte[] tiles = row.getBytes();
			wd = tiles.length;
			ht++;
		}
		x.close();
		try {
			x = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Level level = new Level(wd, ht);
		while (x.hasNextLine()) {
			String row = x.nextLine();
			byte[] tiles = row.getBytes();
			for (int i = 0; i < wd; i++) {
				level.setTileIDAt(i, yPos, tiles[i]);
			}
			yPos++;
		}
		ht = yPos;
		level.setWidth(wd);
		level.setHeight(ht);
		x.close();


		return level;
	}

	public Level(int WIDTH, int HEIGHT) {
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);
		tiles = new byte[WIDTH][HEIGHT];
	}

	public byte getTileIDAt(int x, int y) {	
		return tiles[x][y];
	}

	public void render() {
		for (int x = (int)(Zordz.zordz.screen.xOff / 32) - 1; x < (int)(Zordz.zordz.screen.xOff / 32) + WIDTH + 1; x++) {
			for (int y = (int)(Zordz.zordz.screen.yOff / 32) - 1; y < (int)(Zordz.zordz.screen.yOff / 32) + HEIGHT + 1; y++) {
				int tempX = x, tempY = y;
				if (x >= width) {
					tempX = width-1;
				} else if (x < 0) {
					tempX = 0;
				} else {
					tempX = x;
				}
				if (y >= height) {
					tempY = height-1;
				} else if (y < 0) {
					tempY = 0;
				} else {
					tempY = y;
				}
				Tile.getByID(tiles[tempX][tempY]).render(this, x * 32, y * 32);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render();
		}
	}

	public void add(Entity e) {
		e.uid = lastUID;
		lastUID++;
		entities.add(e);
	}

	public void remove(Entity e) {
		if (entities.contains(e)) {
			lastUID = e.uid;
			entities.remove(e);
		}
	}

	public Tile getTileAt(int x, int y) {
		if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
			return Tile.SAND_ROCK;
		}
		return Tile.getByID(tiles[x][y]);
	}

	public void tick() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile.getByID(tiles[x][y]).tick(this, x * 32, y * 32);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		tickCount++;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTileIDAt(int x, int y, byte id) {
		this.tiles[x][y] = id;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void movePlayer(String username, float xa, float ya, int direction) {
		for (int a = 0; a < getEntities().size(); a++) {
			Entity e = getEntities().get(a);
			if (e instanceof PlayerMP) {
				PlayerMP player = (PlayerMP) e;
				if (player.getUsername().equals(username)) {
					player.setX(xa);
					player.setY(ya);
					player.setDirection(direction);
					player.move_ticks++;
				}
			}
		}
	}

	public void playerAttack(String username) {
		for (Entity mob : entities) {
			if (mob instanceof PlayerMP) {
				PlayerMP player = (PlayerMP) mob;
				if (player.getUsername().equals(username)) {
					player.attack(false);
				}
			}
		}
	}

	public void playerSwitch(String username, int slot) {
		for (Entity mob : entities) {
			if (mob instanceof PlayerMP) {
				PlayerMP player = (PlayerMP) mob;
				if (player.getUsername().equals(username)) {
					if (slot == 0) {
						player.setCurrentWeapon(player.getCombatWeapon(), false);
					} else {
						player.setCurrentWeapon(player.getSpecialWeapon(), false);
					}
				}
			}
		}
	}

	public PlayerMP getPlayerMP(String username) {
		for (Entity mob : entities) {
			if (mob instanceof PlayerMP) {
				PlayerMP player = (PlayerMP) mob;
				if (player.getUsername().equals(username)) {
					return player;
				}
			}
		}
		return null;
	}

	public void clear() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			entities.remove(e);
			lastUID = 0;
		}
	}

	public void playerInfo(Packet05PlayerInfo packet) {
		PlayerMP player = getPlayerMP(packet.getUsername());
		if (player == null) {
			return;
		}
		
		if (player.getX() != packet.getFloatField("x") || player.getY() != packet.getFloatField("y")) {
			System.out.println("gaypenis");
			float d = Options.TICK_RATE;
			player.move_ticks+=Options.MAX_TICK_RATE / d;
			if (player.move_ticks >= Options.MAX_TICK_RATE) {
				player.move_ticks = 0;
			}
		}
		player.setX(packet.getFloatField("x"));
		player.setY(packet.getFloatField("y"));
		player.direction = packet.getIntField("dir");
		player.setHealth(packet.getIntField("health"));
		player.setMaxHealth(packet.getIntField("max_health"));
		player.stats.healthAdditive = packet.getIntField("max_health") - 100;

		player.foodCounter = packet.getIntField("food_counter");

		int currwep = packet.getIntField("current_wep");
		if (currwep == 0) player.setCurrentWeapon(player.getCombatWeapon(), false);
		else player.setCurrentWeapon(player.getSpecialWeapon(), false);

		player.setCombatWeapon(packet.getIntField("combat_wep"));
		player.setSpecialWeapon(packet.getIntField("special_wep"));

		player.setBleedTicks(packet.getIntField("bleed_ticks"));
		player.setBurnTicks(packet.getIntField("burn_ticks"));

		player.setBurnRate(packet.getIntField("burn_rate"));
	}
}
