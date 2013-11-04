package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import zordz.gfx.Drawer;
import zordz.gfx.Text;
import cjaf.tools.NewGLHandler;

public class Button {
	public static int between_state_cd;
	String text;
	Color col;
	float x, y;
	int tx, tsize;
	int width = (int) (256 / 1.5f), height = (int) (64 / 1.5f);
	final Texture tex = NewGLHandler.loadTexture("res/titlescr/button_plate.png");
	public Button(String text, Color col, float x, float y, int tx, int ts) {
		this.text = text;
		this.col = col;
		this.x = x;
		this.y = y;
		this.tx = tx;
		tsize = ts;
	}

	public void draw() {
		NewGLHandler.drawTexture2D(tex, x, y, width, height);
		Drawer.setCol(col);
		Text.render(text, x + tx, y + 13, tsize, tsize);
		Drawer.setCol(Color.white);
		if (isMouseInside()) {
			NewGLHandler.setCurrentColor(new float[]{1.0f, 1.0f, 1.0f, 0.5f}, true);
			NewGLHandler.draw2DRect(x, y, width, height, true);
			Drawer.setCol(Color.white);
		}
	}

	public boolean isMouseInside() {
		return (Mouse.getX() > x && Mouse.getX() < x + width
				&& 480-Mouse.getY() > y && 480-Mouse.getY() < y + height);
	}

	public boolean clicked() {
		if (isMouseInside()) {
			if (Mouse.isButtonDown(0)) {
				if (between_state_cd > 0) {
					between_state_cd = 15;
					return false;
				}
				return true;
			}
		}
		return false;
	}
}