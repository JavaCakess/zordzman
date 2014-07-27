package zordz.state;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.NewGLHandler;
import zordz.gfx.SpriteSheet;
import zordz.gfx.Text;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class IntroState extends State {

	int ticks = 0;
	int maxticks = 90;
	int waitTicks = 0;
	int totalticks = 0;
	boolean fading = false, done = false;
	Zordz zordz;
	
	public IntroState(Zordz zordz) {
		this.zordz = zordz;
	}
	
	public void render() {
		Drawer.draw(SpriteSheet.sheet, 0, 0, 0, 0, 640, 480); // <-- leave this here
															 //  until the bug is sorted out.
		float alpha = 0.0f;
		if (fading) alpha = 1f - (ticks / 90f);
		else alpha = ticks / 90f;
		
		NewGLHandler.setCurrentColor(new float[]{alpha, alpha, alpha}, false);
		NewGLHandler.draw2DRect(0, 0, Zordz.WIDTH, Zordz.HEIGHT, true);
		NewGLHandler.setCurrentColor(new float[]{alpha / 1.75f, 0f, 0f}, false);
		Text.render("Fountanio", 320 - ("Fountanio".length() * 16), 196, 32, 32);
		NewGLHandler.resetColors();
	}

	public void tick() {
		if (ticks == maxticks) {
			if (fading) {
				if (totalticks == 241) {
					zordz.switchState(zordz.titlestate);
				}
			} else { fading = true; waitTicks = 60; ticks = 0; }
		}
		else if (waitTicks > 0) {
			waitTicks--;
			System.out.println(waitTicks);
		} else ticks++;
		
		if (totalticks == 60) SoundPlayer.play(Sound.start);
		totalticks++;
	}

	public void onSwitchAway(State to) {
		
	}

	public void onSwitchTo(State awayFrom) {
		
	}

	public int getID() {
		return 7;
	}
}
