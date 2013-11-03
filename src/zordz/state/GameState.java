package zordz.state;

import java.awt.Color;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import cjaf.tools.NewGLHandler;

public class GameState extends State {
	Zordz zordz;
	boolean paused = false;

	public GameState(Zordz zordz) {
		this.zordz = zordz;
	}

	public void render() {
		zordz.level.render();
		renderHUD();
	}

	public void renderHUD() {
		if (!paused) {
			NewGLHandler.setCurrentColor(
					new float[] { 0.0f, 0.0f, 0.0f, 0.8f }, true);
			NewGLHandler.draw2DRect(0, 0, Zordz.WIDTH, 32, true);
			NewGLHandler.resetColors();
			if (zordz.hp <= (100 * 0.4))
				Drawer.setCol(Color.red);
			Text.render("HP:" + zordz.hp + "/100", 0, 0, 16, 16);
			Drawer.setCol(Color.white);
			Text.render("Z", 300, 0, 8, 8);
			Text.render("X", 332, 0, 8, 8);
			NewGLHandler.resetColors();
		} else {
			Text.render("PAUSED", zordz.WIDTH / 2 - 3 * 30,
					zordz.HEIGHT / 2 - 30, 30, 30);
		}
	}

	public void tick() {

	}

	public int getID() {
		return 1;
	}

	public void togglePaused() {
		paused = !paused;
	}

}
