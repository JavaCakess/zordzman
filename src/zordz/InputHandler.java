package zordz;

import org.lwjgl.input.Keyboard;

public class InputHandler {
	Zordz zordz;

	public InputHandler(Zordz zordz) {
		this.zordz = zordz;
	}

	public void doInput() {
		while (Keyboard.next()) {
			switch (zordz.state.getID()) {
			case 1:
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					zordz.gamestate.togglePaused();
				}
				break;
			}
		}
	}
}
