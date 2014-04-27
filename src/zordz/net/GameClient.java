package zordz.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import zordz.Zordz;
import zordz.entity.PlayerMP;
import zordz.net.Packet.PacketType;
public class GameClient extends Thread {
	public DatagramSocket socket;
	String host;
	private InetAddress ipAddress;
	public boolean canPlay = false;
	Zordz zordz;

	public GameClient(Zordz zordz, String host) {
		this.zordz = zordz;
		try {
			socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(host);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			handleLogin((Packet00Login) packet, address, port);
			break;
		case MOVE:
			packet = new Packet01Move(data);
			handleMove((Packet01Move) packet);
			break;
		case ATTACK:
			packet = new Packet02Attack(data);
			handleAttack((Packet02Attack) packet);
			break;
		case SWITCH_WEAPON:
			packet = new Packet03SwitchWeap(data);
			handleSwitchWeapon((Packet03SwitchWeap) packet);
			break;
		}
	}

	private void handleSwitchWeapon(Packet03SwitchWeap packet) {
		Zordz.zordz.level.playerSwitch(packet.getUsername(), packet.getSlot());
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, Net.PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleMove(Packet01Move packet) {
		Zordz.zordz.level.movePlayer(packet.getUsername(), packet.getXA(), packet.getYA(), packet.getDirection());
	}

	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
				+ " has joined the game...");
		PlayerMP player = new PlayerMP(Zordz.zordz.level, packet.getX(), packet.getY(), packet.getUsername(), address, port);
		Zordz.zordz.level.add(player);
	}

	private void handleAttack(Packet02Attack packet) {
		Zordz.zordz.level.playerAttack(packet.getUsername());
	}
}
