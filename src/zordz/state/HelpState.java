package zordz.state;

import java.awt.Color;

import zordz.Options;
import zordz.Zordz;
import zordz.gfx.Text;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class HelpState extends State {

	public final String helpText = "Welcome to Zordzman!\n"
								+  "It's an RPG, if you couldn't guess.\n \n"
								+  "You can press the arrow keys to move.\n"
								+  "Use the spacebar to attack with your\n"
								+  "current weapon.\n \n"
								+  "Now get playing!";
	
	Button back = new Button("Back", Color.white, Zordz.WIDTH / 2 - (Button.width / 2), 471 - Button.height, 52, 16);
	int ticks = 0;
	Zordz zordz;
	public HelpState(Zordz zordz) {
		this.zordz = zordz;
	}
	
	public void render() {
		int ind = -1;
		int lastLen = 0;
		for (String s : helpText.split("\n")) {
			ind++;
			lastLen = s.length();
			Text.render(s, 0, ind * 16, 16, 16);
		}
		if (ticks < Options.TICK_RATE / 2) {
			Text.render("_", (lastLen) * 16 , ind * 16, 16, 16);
		}
		Text.render("NOW!", Zordz.WIDTH / 2 - (64), 250, 32, 32);
		back.draw();
	}
	
	public void tick() {
		if (back.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.switchState(zordz.titlestate);
		}
		ticks++;
		if (ticks == Options.TICK_RATE) {
			ticks = 0;
		}
	}

	public int getID() {
		
		return 4;
	}


	public void onSwitchAway(State to) {
		
	}

	public void onSwitchTo(State awayFrom) {
		
	}
	
}
