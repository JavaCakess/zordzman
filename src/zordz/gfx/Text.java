package zordz.gfx;

public class Text {

	private static String chars = 
		 "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + 
		"0123456789.,:;'\"!?$%()-=+/*_     ";

	public static void render(String msg, float x, float y, float w, float h) {
		msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0)
				Drawer.draw(SpriteSheet.sheet, charIndex, 28 + charIndex / 32, x + (i * w), y, w, h);
		}
	}
}
