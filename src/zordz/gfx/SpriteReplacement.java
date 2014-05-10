package zordz.gfx;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteReplacement {

	public static final SpriteReplacement first_aid_kit = 
		new SpriteReplacement("res/zsr/first_aid_kit.zsr", 0x3132, "spritesheet", "backpacks");
	
	private static int MODE_START = 0, MODE_SHEETS = 1, MODE_PUTS = 2;
	public String spritesheet = "";
	public String rspritesheet = "";
	private static int SHEET_COPY    = 0;
	private static int SHEET_REPLACE = 1;
	public Map<Character, Character> sheet = new HashMap<Character, Character>();
	
	private static String byt(int e) {
		return Integer.toHexString(e).toUpperCase();
	}
	
	public SpriteReplacement(String path, int sheet_size, String rsheet, String spritesheet) {
		rspritesheet = rsheet;
		this.spritesheet = spritesheet;
		
		try {
			File file = new File(path);
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			int c;
			int mode = MODE_PUTS; // start mode
			int put_bytes_read = 0;
			int curr_pos_char = 0x0000;
			int curr_put_char = 0x0000;
			while ((c = dis.read()) != -1) {
				if (mode == MODE_PUTS) {
					if (put_bytes_read % 4 == 0) {
						curr_put_char = c;
						System.out.println("currputchar 0x00__, " + curr_put_char);
					} else if (put_bytes_read % 4 == 1) {
						curr_put_char = curr_put_char << 8;
						curr_put_char = curr_put_char | c;
					} else if (put_bytes_read % 4 == 2) {
						curr_pos_char = c;
					} else if (put_bytes_read % 4 == 3) {
						curr_pos_char = curr_pos_char << 8;
						curr_pos_char = curr_pos_char | c;
						sheet.put((char)curr_put_char, (char)curr_pos_char);
						System.out.println("Putting 2-byte co-ord " + byt(curr_put_char) + " at " + byt(curr_pos_char));
					}
					put_bytes_read++;
				}
//				if (c == 0x3B) {
//					if (mode == MODE_SHEETS || mode == MODE_START) {
//						sh_count++;
//						if (sh_count == 1) {
//							// We're talking about the sheet whose tiles we're going to be using.
//							mode = MODE_SHEETS;
//						}
//						continue;
//					}
//				} else if (c == 0x21)  {
//					mode = MODE_PUTS;
//					continue;
//				}
//				if (mode == MODE_SHEETS) {
//					if (sh_count == 1) {
//						//spritesheet = spritesheet.concat("" + ((char)c));
//					} else if (sh_count == 2) {
//						//rspritesheet = rspritesheet.concat("" + ((char)c));
//					}
//				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (char c : sheet.keySet()) {
			System.out.println(byt(c) + "   " + byt(sheet.get(c)));
		}
	}
}
