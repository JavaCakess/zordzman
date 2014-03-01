package zordz;

import javax.swing.JFileChooser;

import org.lwjgl.input.Keyboard;

public class InputHandler {
	Zordz zordz;
	int max_scroll_y = 320;
	int min_scroll_y = 160;
	int max_scroll_x = 426;
	int min_scroll_x = 213;
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
			float camera_speed = zordz.player.getSpeed() * ((float)Options.MAX_TICK_RATE / Options.TICK_RATE);
			float ymax = (zordz.screen.yOff+640)/32;
			//float ymax_m = (zordz.level.height*32)-640;
			float xmax = (zordz.screen.xOff+640)/32;
			//float xmax_m = (zordz.level.width*32)-640;
			if (!zordz.gamestate.paused) {
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					if (zordz.player.getY() < zordz.screen.yOff + min_scroll_y) {
						if (ymax <= zordz.level.height) {
							zordz.screen.pushScr(0, -camera_speed);
						}
					}
					zordz.player.move(0, -zordz.player.getSpeed());
					zordz.player.direction = 0x00;
				} else
				if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					if (zordz.player.getY() > zordz.screen.yOff + max_scroll_y) {
						if (ymax <= zordz.level.height) {
							zordz.screen.pushScr(0, camera_speed);
						}
					}
					zordz.player.move(0, zordz.player.getSpeed());
					zordz.player.direction = 0x01;
				} else
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					if (zordz.player.getX() < zordz.screen.xOff + min_scroll_x) {
						zordz.screen.pushScr(-camera_speed, 0);
					}
					zordz.player.move(-zordz.player.getSpeed(), 0);
					zordz.player.direction = 0x02;
				} else
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					if (zordz.player.getX() > zordz.screen.xOff + max_scroll_x) {
						if (xmax <= zordz.level.width) {
							zordz.screen.pushScr(camera_speed, 0);
						}
					}
					zordz.player.move(zordz.player.getSpeed(), 0);
					zordz.player.direction = 0x03;
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
