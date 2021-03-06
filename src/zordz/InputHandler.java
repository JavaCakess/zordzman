package zordz;

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
					if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
						zordz.player.attack(true);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
						zordz.player.setCurrentWeapon(zordz.player.getCombatWeapon(), true);
					} else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
						zordz.player.setCurrentWeapon(zordz.player.getSpecialWeapon(), true);
					}
				}
				float camera_speed = zordz.player.getSpeed() * ((float)Options.MAX_TICK_RATE / Options.TICK_RATE);
				float ymax = (zordz.screen.yOff+640)/32;
				float xmax = (zordz.screen.xOff+640)/32;
				if (!zordz.gamestate.paused) {
				if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
					if (zordz.player.getY() < zordz.screen.yOff + min_scroll_y) {
						zordz.screen.pushScr(0, -camera_speed);
					}
					zordz.player.direction = 0x00;
					zordz.player.move(0, -zordz.player.getSpeed());
				} else
					if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
						if (zordz.player.getY() > zordz.screen.yOff + max_scroll_y) {
							if (ymax <= zordz.level.height) {
								zordz.screen.pushScr(0, camera_speed);
							}
						}
						zordz.player.direction = 0x01;
						zordz.player.move(0, zordz.player.getSpeed());
					} else {
						if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
							if (zordz.player.getX() < zordz.screen.xOff + min_scroll_x) {
								zordz.screen.pushScr(-camera_speed, 0);
							}
							zordz.player.direction = 0x02;
							zordz.player.move(-zordz.player.getSpeed(), 0);
						} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
							if (zordz.player.getX() > zordz.screen.xOff + max_scroll_x) {
								if (xmax <= zordz.level.width) {
									zordz.screen.pushScr(camera_speed, 0);
								}
							}
							zordz.player.direction = 0x03;
							zordz.player.move(zordz.player.getSpeed(), 0);
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
		case 5:
			break;
		case 6:
			break;
		}
	}
}
