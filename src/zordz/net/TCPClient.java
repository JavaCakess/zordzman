package zordz.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import zordz.Options;
import zordz.Zordz;
import zordz.entity.Entity;
import zordz.entity.PlayerMP;

public class TCPClient extends Thread {

	String host;
	BufferedWriter writer;
	BufferedReader reader;
	Socket socket;
	Zordz zordz;

	String lastReceived;

	// Status when connecting
	public static final int NOT_CONNECTED = 0;
	public static final int SUCCESSFUL = 1;
	public static final int FAILED_TO_CONNECT = -1;
	public static final int MAP_NOT_FOUND = 2;
	public static final int USERNAME_TAKEN = 3;
	public int status = 0;

	// Packet Type (Sent by client | Sent by server)
	public static final int LOGIN = 0;
	public static final int DISCONNECT = 1;

	// Packet Types sent by server ONLY
	public static final int MAP = 2;
	
	public TCPClient(Zordz zordz, String host) {
		this.host = host;
		this.zordz = zordz;
	}

	public void run() {
		try {
			socket = new Socket(host, Net.TCP_PORT);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		} catch (IOException ioe) {
			status = FAILED_TO_CONNECT;
			ioe.printStackTrace();
			return;
		}
		status = SUCCESSFUL;
		if (status != SUCCESSFUL) return;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] args = line.split(" ");
				System.out.println(line);
				if (args[0].equals("INV")) {
					if (args[1] == "0") {
						// Invalid username
						status = USERNAME_TAKEN;
					}
				} else if (args[0].equals("MAP")) {
					zordz.level = zordz.getLevel(args[1].substring(0, args[1].length() - 4));
					if (zordz.level == null) {
						status = MAP_NOT_FOUND;
						continue;
					}
					zordz.level.clear();
					zordz.mutliplayerstate.canPlay = true;
				} else if (args[0].equals("ENT")) {
					Entity e = Entity.parseEntity(zordz.level, args[1] + ";" + args[2] + ";" + args[3]);
					zordz.level.add(e);
				} else if (args[0].equals("LOGIN")) {
					PlayerMP player = new PlayerMP(zordz.level, Float.parseFloat(args[2]), Float.parseFloat(args[3]), args[1], null, -1);
					zordz.level.add(player);
				} else if (args[0].equals("PLAYERINFO")) {
					// First find the player.
					String username = args[1];
					float x = Float.parseFloat(args[2]);
					float y = Float.parseFloat(args[3]);
					int direction = Integer.parseInt(args[4]);
					int health = Integer.parseInt(args[5]);
					int max_health = Integer.parseInt(args[6]);
					int foodCounter = Integer.parseInt(args[7]);
					int currentWeapon = Integer.parseInt(args[8]);
					int combatWeaponID = Integer.parseInt(args[9]);
					int specialWeaponID = Integer.parseInt(args[10]);
					
					PlayerMP playermp = Zordz.zordz.level.getPlayerMP(username);
					if (playermp == null) {
						playermp = new PlayerMP(Zordz.zordz.level, x, y, username, null, -1);
					}
					// Set all the fields
					
					playermp.setX(x);
					playermp.setY(y);
					playermp.direction = direction;
					playermp.setHealth(health);
					playermp.setMaxHealth(max_health);
					playermp.foodCounter = foodCounter;
					playermp.setCombatWeapon(combatWeaponID);
					playermp.setSpecialWeapon(specialWeaponID);
					if (currentWeapon == 0) {
						playermp.setCurrentWeapon(playermp.getCombatWeapon());
					}
				}
				lastReceived = line;
			}
		} catch (IOException ioe) {

		}
	}

	public void write(int type) {
		switch (type) {
		case LOGIN:
			write("0," + Options.USERNAME);
			break;
		case DISCONNECT:
			write(DISCONNECT);
			break;
		}
	}

	public void write(byte[] data) {
		if (status != SUCCESSFUL) return;
		try {
			writer.write(new String(data) + "\r\n");
			writer.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void write(String s) {
		if (status != SUCCESSFUL) return;
		try {
			writer.write(s + "\r\n");
			writer.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String lastReceived() {
		return lastReceived;
	}

}
