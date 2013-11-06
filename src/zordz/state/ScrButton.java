package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import zordz.gfx.Drawer;
import zordz.gfx.Text;
import cjaf.tools.NewGLHandler;

public class ScrButton {

	float x, y;
	int width = 16, height = 16;
	final Texture tex = NewGLHandler.loadTexture("res/levelselectscr/scr_button.png");
	boolean flipped = false;
	public ScrButton(float x, float y, boolean flipped) {
		this.x = x;
		this.y = y;
		this.flipped = flipped;
	}

	public void draw() {
		if (flipped) {
			NewGLHandler.drawTexture2D(tex, x, y, width, height, NewGLHandler.FLIP_UPSIDE_DOWN);
		} else {
			NewGLHandler.drawTexture2D(tex, x, y, width, height);
		}
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
				if (Button.between_state_cd > 0) {
					Button.between_state_cd = 15;
					return false;
				}
				return true;
			}
		}
		return false;
	}
}
