package zordz.entity;

import java.net.InetAddress;

import zordz.Zordz;
import zordz.level.Level;
import zordz.net.Packet01Move;
import zordz.net.Packet02Attack;

public class PlayerMP extends Player {

	private InetAddress addr;
	private int port;

	public PlayerMP(Level lvl, float x, float y, String username, InetAddress addr, int port) {
		super(lvl, x, y, username);
		this.addr = addr;
		this.port = port;
	}

	public InetAddress getAddr() {
		return addr;
	}

	public int getPort() {
		return port;
	}

	public void tick() {
		super.tick();
	}

	public boolean move(float xa, float ya) {
		if (super.move(xa, ya)) {
			Packet01Move movePacket = new Packet01Move(username, x, y, direction);
			movePacket.writeData(Zordz.zordz.gameclient);
			return true;
		}
		return false;
	}

	public void attack(boolean send) {
		super.attack();
		if (send) {
			Packet02Attack attackPacket = new Packet02Attack(username);
			attackPacket.writeData(Zordz.zordz.gameclient);
		}
	}

	public void setAddr(InetAddress addr2) {
		addr = addr2;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
