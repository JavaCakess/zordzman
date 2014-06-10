package zordz.util;

import zordz.weapons.Weapon;

public class Util {

	public static byte[] int_to_arr(byte[] e, int i, int pos) {
		e[pos] = (byte) ((i >> 24) & 0xFF);
		e[pos + 1] = (byte) ((i >> 16) & 0xFF);
		e[pos + 2] = (byte) ((i >> 8) & 0xFF);
		e[pos + 3] = (byte) ((i & 0xFF));
		return e;
	}
	
	public static Weapon getByID(int id2) {
		for (Weapon weap : Weapon.weapons) {
			if (weap.getID() == id2) {
				return weap;
			}
		}
		return null;
	}
}
