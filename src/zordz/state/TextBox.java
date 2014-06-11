package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import zordz.Options;
import zordz.gfx.Drawer;
import zordz.gfx.NewGLHandler;
import zordz.gfx.Text;

public class TextBox extends Button {

	float w, h;
	int txtsize, char_limit;
	int ticks;
	boolean focus;
	int cursor_pos;
	char[] banned_chars;

	public TextBox(String text, float x, float y, float w, float h, int txtsize, int char_limit) {
		super(text, Color.white, x, y, 1, 1);
		this.w = w;
		this.h = h;
		this.width = (int)w;
		this.height = (int)h;
		this.txtsize = txtsize;
		this.char_limit = char_limit;
		cursor_pos = text.length();
	}
	
	public TextBox(String text, float x, float y, float w, float h, int txtsize, int char_limit, char[] banned_chars) {
		super(text, Color.white, x, y, 1, 1);
		this.w = w;
		this.h = h;
		this.txtsize = txtsize;
		this.char_limit = char_limit;
		this.banned_chars = banned_chars;
		cursor_pos = text.length();
	}

	public void draw() {
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f}, false);
		NewGLHandler.draw2DRect(x, y, w, h, true);
		if (focus) Drawer.setCol(Color.white);
		else Drawer.setCol(Color.gray);
		Text.render(text, x, y, txtsize, txtsize);
		if (ticks < Options.TICK_RATE / 2 && focus) {
			Drawer.setCol(Color.lightGray);
			NewGLHandler.draw2DRect(x + cursor_pos * txtsize, y, txtsize * 0.125f, txtsize, true);
		}
		NewGLHandler.setCurrentColor(new int[]{0, 0, 0}, false);
		NewGLHandler.draw2DRect(x, y, w, h, false);
		NewGLHandler.resetColors();
		tick();
	}

	public void tick() {
		if (cursor_pos > text.length()) {
			cursor_pos = text.length();
		}
		if (Mouse.isButtonDown(0)) {
			if (this.clicked()) {
				focus = true;
			} else {
				focus = false;
			}
		}

		if (Keyboard.next() && focus) {
			if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
				if (text.length() - 1 >= 0&& cursor_pos > 0) {
					String copy = "";
					copy = copyExclude(text, cursor_pos);
					text = copy;
					cursor_pos--;
					ticks = 0;
				}
			} else if ((int)Keyboard.getEventCharacter() != 127 && (int)Keyboard.getEventCharacter() != 0) {
				if (Keyboard.getEventKeyState() == true && text.length() != char_limit && allowed(Keyboard.getEventCharacter())) {
					if (cursor_pos > 0) {
						text = addCharAt(text, cursor_pos, Keyboard.getEventCharacter());
					} else {
						text = text.concat(""+Keyboard.getEventCharacter());
					}
					ticks = 0;
					cursor_pos++;
				}
			} else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
				if (Keyboard.getEventKeyState() == true && cursor_pos > 0) {
					cursor_pos--;
					ticks = 0;
				}
			} else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
				if (Keyboard.getEventKeyState() == true && cursor_pos < text.length()) {
					cursor_pos++;
					ticks = 0;
				}
			}
		}

		ticks++;
		if (ticks == Options.TICK_RATE) {
			ticks = 0;
		}
	}

	private String addCharAt(String n, int pos, char c) {
		String a = n.substring(0, pos);
		String b = n.substring(pos);
		a = a.concat(c + "");
		a = a.concat(b);
		
		return a;
	}

	public String copyExclude(String a, int leaveOut) {
		int i = 1;
		String b = "";
		for (char c : a.toCharArray()) {
			if (i != leaveOut) {
				b = b.concat("" + c);
			}
			i++;
		}
		return b;
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

	//public String getText() {
		//return text;
	//}
}
