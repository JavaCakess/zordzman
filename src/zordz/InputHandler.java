package zordz;

import javax.swing.JFileChooser;

import org.lwjgl.input.Keyboard;

public class InputHandler {
	Zordz zordz;

	public InputHandler(Zordz zordz) {
		this.zordz = zordz;
	}

	public void doInput() {
		switch (zordz.state.getID()) {
		case 0:
			break;
		case 1:
			while (Keyboard.next()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					zordz.gamestate.togglePaused();
				}
			}
			float camera_speed = 2f * ((float)Options.MAX_TICK_RATE / Options.TICK_RATE);
			float ymax = (zordz.screen.yOff+640)/32;
			float ymax_m = (zordz.level.height*32)-640;
			float xmax = (zordz.screen.xOff+640)/32;
			float xmax_m = (zordz.level.width*32)-640;
			if (!zordz.gamestate.paused) {
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					zordz.screen.pushScr(0, -camera_speed);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					if (ymax <= zordz.level.height) {
						zordz.screen.pushScr(0, camera_speed);
					}
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					zordz.screen.pushScr(-camera_speed, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					if (xmax <= zordz.level.width) {
						zordz.screen.pushScr(camera_speed, 0);
					}
				}
			}
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}
}
