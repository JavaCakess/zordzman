package zordz;

import org.lwjgl.opengl.Display;

import cjaf.tools.NewGLHandler;
import zordz.state.Button;
import zordz.state.MultiplayerState;
import zordz.state.ScrButton;
import zordz.state.WeaponSelector;

public class Screen {

	Zordz zordz;
	public long tickCount = 0;
	public float xOff;
	public int stateID = 0;
	public float yOff;
	Thread tpsThread;
	public Screen(Zordz zord) {
		this.zordz = zord;
		tpsThread = new Thread(
			new Runnable() {
				public void run() {
					while (!Display.isCloseRequested()) {
						try { Thread.sleep(1000); } catch (Exception e) {}
						Runtime runt = Runtime.getRuntime();
						stateID = zordz.state.getID();
						runt.gc();
						long maxmem = runt.maxMemory() / 1024;
						long used = maxmem - (runt.freeMemory() / 1024);
						Display.setTitle("Zordzman " + Zordz.version + " | TPS: " + tickCount + " | " + Options.USERNAME
								+ " | " + used + "/" + maxmem);
						tickCount = 0;
					}
				}
			}
		);
		
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
	
	public void startTPSThread() {
		tpsThread.start();
	}
	
	public void update() {
		
		//logic 'n' stuff goes here!
		zordz.state.tick();
		if (Button.between_state_cd != 0 ) Button.between_state_cd--;
		if (ScrButton.cooldown != 0 ) ScrButton.cooldown--;
		if (Button.between_state_over_cd != 0) Button.between_state_over_cd--;
		if (MultiplayerState.connect_cooldown != 0) MultiplayerState.connect_cooldown--;
		if (MultiplayerState.connect_wait != 0) MultiplayerState.connect_wait--;
		if (WeaponSelector.select_cd != 0) WeaponSelector.select_cd--;
		zordz.inputhandler.doInput();
		tickCount++;
		//Don't touch this stuff. Logic above! ^
		Display.update();
		Display.sync(Options.TICK_RATE); //60 FPS. My favourite...
	}

	public void setOff(float i, float j) {
		pushScr(i-xOff, j-yOff);
		xOff = i;
		yOff = j;
	}
}
