package zordz.net;

public class Packet01Move extends Packet {

	private String username;
	private float xa, ya;
	private int direction;
	private float moveTicks;
	
	public Packet01Move(byte[] data) {
		super(0x01);
		
		String strData = readData(data);
		String[] dataArray = strData.split(",");
		
		username = dataArray[0];
		xa = Float.parseFloat(dataArray[1]);
		ya = Float.parseFloat(dataArray[2]);
		direction = Integer.parseInt(dataArray[3]);
	}
	
	public Packet01Move(String username, float xa, float ya, int direction) {
		super(0x01);
		this.username = username;
		this.xa = xa;
		this.ya = ya;
		this.direction = direction;
	}
	
	public String getUsername() {
		return username;
	}
	
	public float getXA() {
		return xa;
	}
	
	public float getYA() {
		return ya;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public float getMoveTicks() {
		return moveTicks;
	}
	
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	public byte[] getData() {
		return ("01" + username + "," + xa + "," + ya + "," + direction).getBytes();
	}
}
