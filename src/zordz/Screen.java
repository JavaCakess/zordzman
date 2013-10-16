package zordz;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import zordz.gfx.Drawer;
import zordz.gfx.Text;

import cjaf.tools.NewGLHandler;

public class Screen {

	Zordz zordz;
	public long tickCount = 0;
	public float xOff, yOff;
	
	public Screen(Zordz zordz) {
		this.zordz = zordz;
	}

	public void render() {
		zordz.level.render();
		renderHUD();
	}
	
	private void renderHUD() {
		NewGLHandler.setCurrentColor(new float[]{0.0f, 0.0f, 0.0f, 0.8f}, true);
		NewGLHandler.draw2DRect(0, 0, Zordz.WIDTH, 32, true);
		NewGLHandler.resetColors();
		if (zordz.hp <= (100 * 0.4)) Drawer.setCol(Color.red);
		Text.render("HP:"+ zordz.hp + "/100", 0, 0, 16, 16);
		NewGLHandler.resetColors();
	}

	public void setOff(float xOff, float yOff) {
		if (xOff < 0) xOff = 0;
		if (yOff < 0) yOff = 0; 
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public void update() {
		//logic 'n' stuff goes here!
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && zordz.hp < 100) zordz.hp++;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && zordz.hp > 0) zordz.hp--;
		
		zordz.level.tick();
		tickCount++;
		//Don't touch this stuff. Logic above! ^
		Display.update();
		Display.sync(60); //60 FPS. My favourite...
	}
}
