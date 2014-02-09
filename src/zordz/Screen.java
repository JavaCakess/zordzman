package zordz;

import org.lwjgl.opengl.Display;

import cjaf.tools.NewGLHandler;
import zordz.state.Button;
import zordz.state.ScrButton;

public class Screen {

	Zordz zordz;
	public long tickCount = 0;
	public int stateID = 0;
	public float xOff, yOff;
	Thread tpsThread;
	public Screen(Zordz zord) {
		this.zordz = zord;
		tpsThread = new Thread(
			new Runnable() {
				public void run() {
					while (!Display.isCloseRequested()) {
						try { Thread.sleep(1000); } catch (Exception e) {}
						stateID = zordz.state.getID();
						Display.setTitle("Zordzman " + Zordz.version + " | TPS: " + tickCount + " | S: " + stateID);
						tickCount = 0;
					}
				}
			}
		);
		tpsThread.start();
	}

	public void render() {
		zordz.state.render();
	}

	public void pushScr(float xa, float ya) {
		NewGLHandler.pushAll2D(-xa, -ya);
		xOff+=xa;
		yOff+=ya;
		if (xOff < 0) {
			NewGLHandler.pushAll2D(xOff, 0);
			xOff = 0;
		}
		if (yOff < 0) {
			NewGLHandler.pushAll2D(0, yOff);
			yOff = 0;
		}
	}
	
	public void update() {
		//logic 'n' stuff goes here!
		zordz.state.tick();
		if (Button.between_state_cd != 0 ) Button.between_state_cd--;
		if (ScrButton.cooldown != 0 ) ScrButton.cooldown--;
		zordz.inputhandler.doInput();
		tickCount++;
		//Don't touch this stuff. Logic above! ^
		Display.update();
		Display.sync(60); //60 FPS. My favourite...
	}

	public void setOff(float i, float j) {
		pushScr(i-xOff, j-yOff);
		xOff = i;
		yOff = j;
	}
}
