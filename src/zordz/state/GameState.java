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
	public boolean paused = false;
	Button backToMenu = new Button("Back to Menu", Color.white, 230, 180, 12, 12);
	Button options =    new Button("Options", Color.orange, 230, 240, 28, 16);
	Button resume     = new Button("Resume", Color.blue, 230, 300, 36, 16);
	float privOx, privOy;
	public GameState(Zordz zordz) {
		this.zordz = zordz;
	}

	public void render() {
		float ox = zordz.screen.xOff;
		float oy = zordz.screen.yOff;

		zordz.level.render();
		if (paused) {
			NewGLHandler.setCurrentColor(new float[]{0.0f, 0.0f, 0.0f, 0.4f}, true);
			NewGLHandler.draw2DRect(ox + 0, oy + 0, Zordz.WIDTH, Zordz.HEIGHT, true);
			Drawer.setCol(Color.white);
			Text.render("- PAUSED -", ox +Zordz.WIDTH / 2 - 5 * 30,
					oy +Zordz.HEIGHT / 2 - 180, 30, 30);

			backToMenu.draw();
			options.draw();
			resume.draw();
			
			if (backToMenu.clicked()) {
				SoundPlayer.play(Sound.button_clicked);
				this.togglePaused();
				zordz.switchState(zordz.titlestate);
			} else if (options.clicked()) {
				SoundPlayer.play(Sound.button_clicked);
				zordz.optionsstate.backToState = getID();
				zordz.switchState(zordz.optionsstate);
			} else if (resume.clicked()) {
				SoundPlayer.play(Sound.button_clicked);
				togglePaused();
			}
		} else {
			renderHUD();
		}
	}

	public void renderHUD() {
		float ox = zordz.screen.xOff;
		float oy = zordz.screen.yOff;
		NewGLHandler.setCurrentColor(new float[] { 0.0f, 0.0f, 0.0f, 0.8f },
				true);
		NewGLHandler.draw2DRect(ox + 0, oy + 0, Zordz.WIDTH, 32, true);
		NewGLHandler.resetColors();
		if (zordz.hp <= (100 * 0.4))
			Drawer.setCol(Color.red);
		Text.render("HP:" + zordz.hp + "/100", ox + 0, oy + 0, 16, 16);
		Text.render("WEP:" + "Zord", ox + 0, oy + 16, 16, 16);
		Drawer.setCol(Color.white);
		Text.render("Z", ox + 300, oy + 0, 8, 8);
		Text.render("X", ox + 332, oy + 0, 8, 8);
		Text.render("COINS", ox + 460, oy + 0, 8, 8);
		Text.render("" + zordz.coins, (ox + 472) - ((""+zordz.coins).length()-1) * 8, oy + 12, 16, 16);
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
	
	public void onSwitchAway(State to) {
		privOx = zordz.screen.xOff;
		privOy = zordz.screen.yOff;
		
	}

	public void onSwitchTo(State awayFrom) {
		zordz.screen.setOff(privOx, privOy);
		
	}
}
