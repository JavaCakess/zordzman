package zordz.entity;

import zordz.gfx.Text;

public class PlayerInventory {

	Item[] items = new Item[6];
	int render_width = 72;
	Player player;
	
	public PlayerInventory(Player player) {
		this.player = player;
	}
	
	public void render(float x, float y) {
		Text.render("--ITEMS--", x, y, 8, 8);
		
	}
}
