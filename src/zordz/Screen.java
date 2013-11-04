package zordz;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import zordz.entity.TextParticle;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.state.Button;

import cjaf.tools.NewGLHandler;

public class Screen {

	Zordz zordz;
	public long tickCount = 0;
	public float xOff, yOff;
	
	public Screen(Zordz zordz) {
		this.zordz = zordz;
	}

	public void render() {
		zordz.state.render();
	}
	public void setOff(float xOff, float yOff) {
		if (xOff < 0) xOff = 0;
		if (yOff < 0) yOff = 0; 
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public void update() {
		//logic 'n' stuff goes here!
		zordz.state.tick();
		if (Button.between_state_cd != 0 ) Button.between_state_cd--;
		zordz.inputhandler.doInput();
		//Don't touch this stuff. Logic above! ^
		Display.update();
		Display.sync(60); //60 FPS. My favourite...
	}
}
