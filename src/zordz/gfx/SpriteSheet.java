package zordz.gfx;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import cjaf.tools.NewGLHandler;

public class SpriteSheet {

	public final int tilesAcross, tW, tH;
	public String path;
	public Texture tex;
	public String name;
	public static ArrayList<SpriteSheet> sheets = new ArrayList<SpriteSheet>();
	public static SpriteSheet sheet = new SpriteSheet("res/spritesheet.png", 32, 8, 8, "spritesheet");
	public static SpriteSheet backpacks = new SpriteSheet("res/backpacks.png", 32, 8, 8, "backpacks");
	
	public SpriteSheet(String path, int tA, int tW, int tH, String name) {
		tex = NewGLHandler.loadTexture(path); //Use NewGLHandler to load our spritesheet.
		tilesAcross = tA;
		this.tW = tW;
		this.tH = tH;
		this.name = name;
		sheets.add(this);
	}

	public static SpriteSheet get(String spritesheet) {
		for (SpriteSheet s : sheets) {
			if (s.name.equalsIgnoreCase(spritesheet)) {
				return s;
			}
		}
		return null;
	}
}
