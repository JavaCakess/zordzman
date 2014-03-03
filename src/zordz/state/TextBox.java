package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import zordz.gfx.Drawer;
import zordz.gfx.Text;
import cjaf.tools.NewGLHandler;

public class TextBox extends Button {

	float w, h;
	int txtsize, char_limit;
	int ticks;
	boolean focus;
	char[] banned_chars;

	public TextBox(String text, float x, float y, float w, float h, int txtsize, int char_limit) {
		super(text, Color.white, x, y, 1, 1);
		this.w = w;
		this.h = h;
		this.width = (int)w;
		this.height = (int)h;
		this.txtsize = txtsize;
		this.char_limit = char_limit;
	}
	
	public TextBox(String text, float x, float y, float w, float h, int txtsize, int char_limit, char[] banned_chars) {
		super(text, Color.white, x, y, 1, 1);
		this.w = w;
		this.h = h;
		this.txtsize = txtsize;
		this.char_limit = char_limit;
		this.banned_chars = banned_chars;
	}

	public void draw() {
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f}, false);
		NewGLHandler.draw2DRect(x, y, w, h, true);
		if (focus) Drawer.setCol(Color.white);
		else Drawer.setCol(Color.gray);
		Text.render(text, x, y, txtsize, txtsize);
		if (ticks > 30 && focus) {
			Drawer.setCol(Color.lightGray);
			NewGLHandler.draw2DRect(x + text.length() * txtsize, y, txtsize * 0.125f, txtsize, true);
		}
		NewGLHandler.setCurrentColor(new int[]{0, 0, 0}, false);
		NewGLHandler.draw2DRect(x, y, w, h, false);
		NewGLHandler.resetColors();
		tick();
	}

	public void tick() {
		if (Mouse.isButtonDown(0)) {
			if (this.clicked()) {
				focus = true;
			} else {
				focus = false;
			}
		}

		if (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
				if (text.length() - 1 >= 0) {
					text = text.substring(0, text.length()-1);
				}
			} else if ((int)Keyboard.getEventCharacter() != 127 && (int)Keyboard.getEventCharacter() != 0) {
				if (Keyboard.getEventKeyState() == true && text.length() != char_limit && allowed(Keyboard.getEventCharacter())) {
					text = text.concat(""+Keyboard.getEventCharacter());
				}
			}
		}

		ticks++;
		if (ticks == 61) {
			ticks = 0;
		}
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean isMouseInside(float ox, float oy) {
		return (Mouse.getX() > x && Mouse.getX() <  x + w
				&& 480-Mouse.getY() > y && 480-Mouse.getY() <  y + h);
	}
	
	public boolean allowed(char c) {
		for (char k : banned_chars) {
			if (c == k) {
				return false;
			}
		}
		return true;
	}

	public String getText() {
		return text;
	}
}
