package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class Button {
	public static int between_state_cd;
	public static int between_state_over_cd;
	String text;
	Color col;
	float x, y;
	int tx, tsize;
	int width = (int) (256 / 1.5f);
	int height = (int) (64 / 1.5f);
	static int WIDTH = (int) (256 / 1.5f), HEIGHT = (int) (64 / 1.5f);
	final Texture tex = NewGLHandler.loadTexture("res/gui/button_plate.png");
	boolean over = false;
	public Button(String text, Color col, float x, float y, int tx, int ts) {
		this.text = text;
		this.col = col;
		this.x = x;
		this.y = y;
		this.tx = tx;
		tsize = ts;
	}

	public void draw() {
		float ox = Zordz.zordz.screen.xOff;
		float oy = Zordz.zordz.screen.yOff;
		NewGLHandler.drawTexture2D(tex, ox + x, oy + y, width, height);
		Drawer.setCol(col);
		Text.render(text, ox + x + tx, oy + y + 13, tsize, tsize);
		Drawer.setCol(Color.white);
		if (isMouseInside(ox, oy)) {
			NewGLHandler.setCurrentColor(new float[]{1.0f, 1.0f, 1.0f, 0.5f}, true);
			NewGLHandler.draw2DRect(ox + x, oy + y, width, height, true);
			Drawer.setCol(Color.white);
		}
		mouseOver();
	}

	public void mouseOver() {
		
		if (mouseOn()) {
			if (between_state_over_cd > 0) {
				between_state_over_cd = 2;
				return;
			}
			if (over == false) {
				over = true;
				between_state_over_cd = 4;
				SoundPlayer.play(Sound.button_over);
			}
		} else {
			over = false;
		}
	}

	public boolean isMouseInside(float ox, float oy) {
		return (Mouse.getX() > x && Mouse.getX() <  x + width
				&& 480-Mouse.getY() > y && 480-Mouse.getY() <  y + height);
	}

	public boolean clicked() {
		float ox = Zordz.zordz.screen.xOff;
		float oy = Zordz.zordz.screen.yOff;
		if (isMouseInside(ox, oy)) {
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

	public boolean mouseOn() {
		float ox = Zordz.zordz.screen.xOff;
		float oy = Zordz.zordz.screen.yOff;
		return isMouseInside(ox, oy);
	}
}