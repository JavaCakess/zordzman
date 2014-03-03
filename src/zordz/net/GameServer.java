package zordz.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import zordz.Zordz;
import zordz.entity.PlayerMP;
import zordz.net.Packet.PacketType;
public class GameServer extends Thread {
	DatagramSocket server = null;
	ArrayList<PlayerMP> players = new ArrayList<PlayerMP>();

	public GameServer() {
		try {
			server = new DatagramSocket(Net.PORT);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				server.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(new String(data));
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketType type = Packet.getFromStringID(message.substring(0, 2));
		Packet packet = null;
		switch (type) {
		default:
		case NONE:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet00Login) packet).getUsername() + " has connected...");
			PlayerMP player = new PlayerMP(Zordz.zordz.level, 100, 100, ((Packet00Login) packet).getUsername(), address, port);
			this.addConnection(player, (Packet00Login) packet);
			break;
		case MOVE:
			packet = new Packet01Move(data);
			this.handleMove(((Packet01Move) packet));
			break;
		case ATTACK:
			packet = new Packet02Attack(data);
			this.handleAttack(((Packet02Attack) packet));
			break;
		}
	}

	private void handleAttack(Packet02Attack packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			PlayerMP player = this.players.get(index);
			player.attack();
			packet.writeData(this);
		}
		
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			this.server.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : players) {
			sendData(data, p.getAddr(), p.getPort());
		}
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		Packet00Login permPacket = packet;
		for (PlayerMP p : this.players) {
			if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
				if (p.getAddr() == null) {
					p.setAddr(player.getAddr());
				}
				if (p.getPort() == -1) {
					p.setPort(p.getPort());
				}
				alreadyConnected = true;
			} else {
				// relay to the current connected player that there is a new
				// player
				sendData(permPacket.getData(), p.getAddr(), p.getPort());

				// relay to the new player that the currently connect player
				// exists
				packet = new Packet00Login(p.getUsername(), p.getX(), p.getY());
				sendData(packet.getData(), player.getAddr(), player.getPort());
			}
		}
		if (!alreadyConnected) {
			this.players.add(player);
		}
	}

	public PlayerMP getPlayerMP(String username) {
		for (PlayerMP player : this.players) {
			if (player.getUsername().equals(username)) {
				return player;
			}
		}
		return null;
	}

	public int getPlayerMPIndex(String username) {
		int index = 0;
		for (PlayerMP player : this.players) {
			if (player.getUsername().equals(username)) {
				break;
			}
			index++;
		}
		return index;
	}


	private void handleMove(Packet01Move packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			PlayerMP player = this.players.get(index);
			player.setX(packet.getXA());
			player.setY(packet.getYA());
			player.setDirection(packet.getDirection());
			packet.writeData(this);
		}
	}

}
