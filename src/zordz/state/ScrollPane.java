package zordz.state;

import java.awt.Color;
import java.util.ArrayList;

import zordz.gfx.Drawer;
import zordz.gfx.Text;

import cjaf.tools.NewGLHandler;

public class ScrollPane {

	public String name;
	public float x, y, width, height;
	private ArrayList<String> elements = new ArrayList<String>();
	ScrButton up, down;
	ScrollPaneEHandler event;

	public ScrollPane(String name, float x, float y, float width, float height,
			float block_wd, float block_ht, ScrollPaneEHandler event) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.event = event;
		down = new ScrButton(x + width - 18, y + 20, true);
		up = new ScrButton(x + width - 18, y + height - 37, false);
	}

	public void add(String s) {
		elements.add(s);
	}

	public void render() {
		NewGLHandler.setCurrentColor(new float[]{0.4f, 0.4f, 0.4f}, false);
		NewGLHandler.draw2DRect(x, y, width, height, true);
		Drawer.setCol(Color.black);
		Text.render(name, (x + (width / 2)) - (name.length() * 8), y + 2, 16, 16);
		NewGLHandler.draw2DRect(x + 20, y + 20, width - 40, height - 40, false);
		Drawer.setCol(Color.white);
		up.draw();
		down.draw();
		
	}

}
