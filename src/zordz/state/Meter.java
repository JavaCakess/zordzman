package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;

import zordz.gfx.NewGLHandler;
import zordz.gfx.Text;

public class Meter extends Button {

	public static enum TextType {
		PERCENT, NORMAL
	}
	
	float w, h;
	float value, max;
	int textSize = 16;
	TextType textType = TextType.PERCENT;
	float[] cols;
	String meterText = "";
	boolean customText = false;
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
	
	public Meter setTextType(TextType textType) {
		this.textType = textType;
		return this;
	}
	
	public void setTextSize(int size) {
		textSize = size;
	}
	
	public void setMeterText(String s) {
		meterText = s;
	}
	
	public Meter setCustomText(boolean tof) {
		customText = tof;
		return this;
	}
	
	public void draw() {
		
		NewGLHandler.setCurrentColor(new float[]{0.3f, 0.3f, 0.3f}, false);
		NewGLHandler.draw2DRect(x, y, max, h, true);
		NewGLHandler.setCurrentColor(new float[]{cols[0], cols[1], cols[2]}, false);
		NewGLHandler.draw2DRect(x, y, value, h, true);
		NewGLHandler.setCurrentColor(new int[]{0, 0, 0}, false);
		NewGLHandler.draw2DRect(x-1, y, w, h+1, false);
		NewGLHandler.draw2DLine(x + value, y, x + value, h + y);
		NewGLHandler.resetColors();
		String s = ""+Math.round((value / max) * 100);
		if (textType == TextType.PERCENT) {
			s = s.concat("%");
		}
		if (customText == true) {
			s = meterText;
		}
		Text.render(s, x + (w / 2) - s.length() * (textSize / 2), y + (h / 2) - (textSize / 2), textSize, textSize);
	}
	
	public boolean isMouseInside(float ox, float oy) {
		return (Mouse.getX() > x && Mouse.getX() <  x + w
				&& 480-Mouse.getY() > y && 480-Mouse.getY() <  y + h);
	}
	
}
