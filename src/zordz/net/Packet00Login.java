package zordz.net;

public class Packet00Login extends Packet {

	private String username;
	private float x, y;
	
	public Packet00Login(byte[] data) {
		super(0x00);
		String strData = readData(data);
		String[] dataArray = strData.split(",");
		username = dataArray[0];
		x = Float.parseFloat(dataArray[1]);
		y = Float.parseFloat(dataArray[2]);
	}
	
	public Packet00Login(String username, float x, float y) {
		super(0x00);
		this.username = username;
		this.x = x;
		this.y = y;
	}

	public String getUsername() {
		return username;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}

	public byte[] getData() {
		return ("00" + username + "," + x + "," + y).getBytes();
	}

	@Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

}
