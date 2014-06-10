package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.NewGLHandler;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class Checkbox extends Button {
	float w, h;
	boolean wasClicked = false;
	public boolean checked = false;
	final Texture check = NewGLHandler.loadTexture("res/gui/tick.png");
	public Checkbox(Color col, boolean def, float x, float y, float w, float h) {
		super("", col, x, y, 0, 2);
		this.w = w;
		this.h = h;
		this.x = x;
		this.y = y;
		checked = def;
	}

	public void draw() {
		
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f}, false);
		NewGLHandler.draw2DRect(x, y, w, h, true);
		Drawer.setCol(Color.black);
		NewGLHandler.draw2DRect(x, y, w, h, false);
		Drawer.setCol(Color.green);
		if (checked) {
			NewGLHandler.drawTexture2D(check, x+1, y, 32, 32);
		}
		Drawer.setCol(Color.white);
		if (clicked() && between_state_cd < 1) {
			Button.between_state_cd = 2;
			wasClicked = true;
			toggle();
		}
		if (between_state_cd == 0) {
			wasClicked = false;
		}
	}
	
	public boolean isMouseInside(float ox, float oy) {
		return (Mouse.getX() > x && Mouse.getX() <  x + w
				&& 480-Mouse.getY() > y && 480-Mouse.getY() <  y + h);
	}
	
	public boolean clicked() {
		float ox = Zordz.zordz.screen.xOff;
		float oy = Zordz.zordz.screen.yOff;
		if (isMouseInside(ox, oy)) {
			if (Mouse.isButtonDown(0)) {
				if (between_state_cd > 0) {
					between_state_cd = 3;
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public void toggle() {
		checked = !checked;
		SoundPlayer.play(Sound.checkbox);
	}
}
