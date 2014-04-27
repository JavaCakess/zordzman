package zordz.net;

import zordz.net.GameClient;

public class Packet03SwitchWeap extends Packet {

	String username;
	int slot;

	public Packet03SwitchWeap(String username, int slot) {
		super(03);
		this.username = username;
		this.slot = slot;
	}
	
	public Packet03SwitchWeap(byte[] data) {
		super(0x03);
		String strData = readData(data);
		String[] dataArray = strData.split(",");
		username = dataArray[0];
		slot = Integer.parseInt(dataArray[1]);
	}

	public String getUsername() {
		return username;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public byte[] getData() {
		return ("03" + username + "," + slot).getBytes();
	}

	public void writeData(GameClient client) {
		client.sendData(getData());
	}

}
