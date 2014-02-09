package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import zordz.Zordz;
import zordz.gfx.Text;
import cjaf.tools.NewGLHandler;

public class Meter extends Button {

	float w, h;
	float value, max;
	float[] cols;
	public Meter(float x, float y, float w, float h, float max, float def, float[] cols) {
		super("", Color.black, x, y, 0, 2);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.max = max;
		this.cols = cols;
		this.value = def;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public void draw() {
		
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f}, false);
		NewGLHandler.draw2DRect(x, y, max, h, true);
		NewGLHandler.setCurrentColor(new float[]{cols[0], cols[1], cols[2]}, false);
		NewGLHandler.draw2DRect(x, y, value, h, true);
		NewGLHandler.setCurrentColor(new int[]{0, 0, 0}, false);
		NewGLHandler.draw2DRect(x-1, y, w, h+1, false);
		NewGLHandler.resetColors();

		String s = Math.round((value / max) * 100) + "%";
		Text.render(s, x + (w / 2) - s.length() * 8, y + (h / 2) - 8, 16, 16);
	}
	
	public boolean isMouseInside(float ox, float oy) {
		return (Mouse.getX() > x && Mouse.getX() <  x + w
				&& 480-Mouse.getY() > y && 480-Mouse.getY() <  y + h);
	}
	
}
