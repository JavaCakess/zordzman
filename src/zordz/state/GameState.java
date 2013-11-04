package zordz.state;

import java.awt.Color;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class GameState extends State {
	Zordz zordz;
	boolean paused = false;
	Button backToMenu = new Button("Back to Menu", Color.white, 230, 200, 12, 12);
	public GameState(Zordz zordz) {
		this.zordz = zordz;
	}

	public void render() {
		zordz.level.render();
		if (paused) {
			NewGLHandler.setCurrentColor(new float[]{0.0f, 0.0f, 0.0f, 0.4f}, true);
			NewGLHandler.draw2DRect(0, 0, Zordz.WIDTH, Zordz.HEIGHT, true);
			Drawer.setCol(Color.white);
			Text.render("- PAUSED -", Zordz.WIDTH / 2 - 5 * 30,
					Zordz.HEIGHT / 2 - 180, 30, 30);

			backToMenu.draw();
			
			if (backToMenu.clicked()) {
				SoundPlayer.play(Sound.button_clicked);
				this.togglePaused();
				zordz.switchState(zordz.titlestate);
			}
		} else {
			renderHUD();
		}
	}

	public void renderHUD() {
		NewGLHandler.setCurrentColor(new float[] { 0.0f, 0.0f, 0.0f, 0.8f },
				true);
		NewGLHandler.draw2DRect(0, 0, Zordz.WIDTH, 32, true);
		NewGLHandler.resetColors();
		if (zordz.hp <= (100 * 0.4))
			Drawer.setCol(Color.red);
		Text.render("HP:" + zordz.hp + "/100", 0, 0, 16, 16);
		Drawer.setCol(Color.white);
		Text.render("Z", 300, 0, 8, 8);
		Text.render("X", 332, 0, 8, 8);
		NewGLHandler.resetColors();

	}

	public void tick() {
		if (!paused) {
			zordz.level.tick();
		}
	}

	public int getID() {
		return 1;
	}

	public void togglePaused() {
		paused = !paused;
	}

}
