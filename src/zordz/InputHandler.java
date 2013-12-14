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
			float camera_speed = 1.5f;
			float ymax = (zordz.screen.yOff+640)/32;
			float xmax = (zordz.screen.xOff+640)/32;
			if (!zordz.gamestate.paused) {
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					zordz.screen.pushScr(0, -camera_speed);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					zordz.screen.pushScr(0, camera_speed);
					if (ymax > zordz.level.height) {
						zordz.screen.setOff((float)zordz.screen.xOff, ymax);
					}
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					zordz.screen.pushScr(-camera_speed, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					zordz.screen.pushScr(camera_speed, 0);
					zordz.screen.pushScr(0, -camera_speed);
					if (xmax > zordz.level.width) {
						zordz.screen.setOff((float)xmax, zordz.screen.yOff);
					}
				}
			}
			break;
		}
	}
}
