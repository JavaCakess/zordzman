package zordz.net;

public abstract class Packet {

	public byte id;
	
	public static enum PacketType {
		NONE(-1), LOGIN(0x00), MOVE(0x01), ATTACK(0x02), SWITCH_WEAPON(0x03), PLAYER_INFO(0x05);
		
		private int id;
		PacketType(int id) {
			this.id = id;
		}
		public int getID() {
			return id;
		}
	}
	
	public Packet(int id) {
		this.id = (byte)id;
	}
	
    public abstract void writeData(GameClient client);
    //public abstract void writeData(GameServer server);
    
    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }

    public abstract byte[] getData();
    
    public static PacketType getFromID(int id) {
    	for (PacketType p : PacketType.values()) {
            if (p.getID() == id) {
                return p;
            }
        }
        return PacketType.NONE;
    }
    
    public static PacketType getFromStringID(String id) {
    	return getFromID(Integer.parseInt(id));
    }
}
