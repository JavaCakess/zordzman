package zordz.state;

import java.awt.Color;
import java.util.ArrayList;

import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class ScrollPane {

	public String name;
	public float x, y, width, height;
	private ArrayList<String> elements = new ArrayList<String>();
	int currentInd = 0;
	ScrButton up, down;
	ScrollPaneEHandler event;
	public int fullBarSize = 289;
	
	public ScrollPane(String name, float x, float y, float width, float height,
			float block_wd, float block_ht, ScrollPaneEHandler event) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.event = event;
		up = new ScrButton(x + width - 18, y + 20, true);
		down = new ScrButton(x + width - 18, y + height - 37, false);
	}

	public void add(String s) {
		elements.add(s);
	}
	int start, finish = start + 8;
	public void render() {
		NewGLHandler.setCurrentColor(new float[]{0.4f, 0.4f, 0.4f}, false);
		NewGLHandler.draw2DRect(x, y, width, height, true);
		Drawer.setCol(Color.black);
		NewGLHandler.draw2DRect(x-1, y, width+1, height+1, false);
		Text.render(name, (x + (width / 2)) - (name.length() * 8), y + 2, 16, 16);
		NewGLHandler.draw2DRect(x + 20, y + 20, width - 40, height - 40, false);
		Drawer.setCol(Color.white);
		up.draw();
		down.draw();
		NewGLHandler.setCurrentColor(new float[]{0.8f, 0.8f, 0.8f}, false);
		int barSize = fullBarSize / elements.size();
		NewGLHandler.setCurrentColor(new float[]{0.85f, 0.85f, 0.85f, 0.7f}, true);
		NewGLHandler.draw2DRect(x + width - 18, y + 36, 16, fullBarSize, true);
		Drawer.setCol(Color.white);
		NewGLHandler.draw2DRect(x + width - 18, y + 36 + (barSize * currentInd), 16, barSize, true);
		
		int correctedStart = start, correctedFinish = start + 8;
		if (currentInd > correctedFinish-1) {
			correctedStart = currentInd-7;
		}
		if (finish > elements.size()) {
			correctedFinish = elements.size();
		} else {
			correctedFinish = correctedStart + 8;
		}
		
		for (int i = correctedStart; i < correctedFinish; i++) {
			String v = elements.get(i);
			float x = this.x + 21, y = this.y + 21 + ((i - correctedStart) * 40);
			NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f}, false);
			NewGLHandler.draw2DRect(x, y, width - 42, 40, false);
			Drawer.setCol(Color.WHITE);
			int size = 32;
			if (v.length() > 10) {
				size = 24;
			}
			if (v.length() > 16) {
				size = 16;
			}
			if (v.length() > 24) {
				size = 12;
			}
			Text.render(v, (x + 210 - (v.length() * (size/2))), y + ((40 - size)/2) - 2, size, size);
		}
		if (currentInd < correctedFinish-1) {
			NewGLHandler.draw2DRect(x + 21, y + 21 + (currentInd * 40), width - 42, 40, false);
		} else {
			if (elements.size() > 7) {
				NewGLHandler.draw2DRect(x + 21, y + 21 + (7 * 40), width - 42, 40, false);
			} else {
				NewGLHandler.draw2DRect(x + 21, y + 21 + (currentInd * 40), width - 42, 40, false);
			}
		}
		if (up.clicked()) {
			if (currentInd != 0) currentInd--;
			else return;
			SoundPlayer.play(Sound.scroll);
			event(ScrollPaneEType.SCROLLBAR_UP);
		} else if (down.clicked()) {
			if (currentInd != elements.size()-1) currentInd++;
			else return;
			SoundPlayer.play(Sound.scroll);
			event(ScrollPaneEType.SCROLLBAR_DOWN);
		}
	}

	public void event(ScrollPaneEType type) {
		if (event != null) {
			event.onEvent(type);
		}
	}

	public String getSelected() {
		return elements.get(currentInd);
	}
}
