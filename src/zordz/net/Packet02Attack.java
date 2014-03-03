package zordz.net;

public class Packet02Attack extends Packet {

	private String username;
	
	public Packet02Attack(byte[] data) {
		super(0x02);
		
		String strData = readData(data);
		String[] dataArray = strData.split(",");
		
		username = dataArray[0];
	}

	public Packet02Attack(String username) {
		super(0x02);
		
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("02" + username).getBytes();
	}

}
