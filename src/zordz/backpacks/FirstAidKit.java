package zordz.backpacks;

import zordz.gfx.SpriteSheet;
import zordz.util.Util;

public class FirstAidKit extends Backpack {
	
	public FirstAidKit() {
		sheet = SpriteSheet.backpacks;
		down_gfx = Util.int_to_arr(down_gfx, 0x00000100, 0);
		down_gfx = Util.int_to_arr(down_gfx, 0x01010001, 4);
		
		right_gfx = Util.int_to_arr(right_gfx, 0x02000300, 0);
		right_gfx = Util.int_to_arr(right_gfx, 0x02010301, 4);
		
		up_gfx = Util.int_to_arr(up_gfx, 0x06000700, 0);
		up_gfx = Util.int_to_arr(up_gfx, 0x06010701, 4);
	}
}
