package zordz.backpacks;

import zordz.gfx.SpriteSheet;

/**
 *  Backpacks for players [Graphics Replacing]
 */
public abstract class Backpack {

	public static FirstAidKit first_aid_kit = new FirstAidKit();
	
	public SpriteSheet sheet;
	
	public byte[] down_gfx = new byte[8];
	public byte[] up_gfx = new byte[8];
	public byte[] right_gfx = new byte[8];
	
	public Backpack() {
		
	}
}
