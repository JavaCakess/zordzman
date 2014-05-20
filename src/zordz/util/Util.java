package zordz.util;

public class Util {

	public static byte[] int_to_arr(byte[] e, int i, int pos) {
		e[pos] = (byte) ((i >> 24) & 0xFF);
		e[pos + 1] = (byte) ((i >> 16) & 0xFF);
		e[pos + 2] = (byte) ((i >> 8) & 0xFF);
		e[pos + 3] = (byte) ((i & 0xFF));
		return e;
	}
}
