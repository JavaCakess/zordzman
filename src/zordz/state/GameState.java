package zordz.state;

import java.awt.Color;

import zordz.Options;
import zordz.Zordz;
import zordz.entity.HealthPickup;
import zordz.entity.Player;
import zordz.entity.PlayerMP;
import zordz.gfx.Drawer;
import zordz.gfx.SpriteSheet;
import zordz.gfx.Text;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import zordz.weapons.Weapon;
import zordz.weapons.Weapon.WeaponType;
import cjaf.tools.NewGLHandler;

public class GameState extends State {
	Zordz zordz;
	public boolean paused = false;
	Button backToMenu = new Button("Back to Menu", Color.white, 230, 180, 12, 12);
	Button options =    new Button("Options", Color.orange, 230, 240, 28, 16);
	Button resume     = new Button("Resume", Color.blue, 230, 300, 36, 16);
	Button backToMenu2 =new Button("Back to Menu", Color.red, 120, 400, 12, 12);
	Button newGame    = new Button("Restart", Color.yellow, 310, 400, 28, 16);
	float privOx, privOy;
	Player player;
	int ticks = 0;
	int statusY = 6;
	public GameState(Zordz zordz) {
		this.zordz = zordz;
		zordz.player = new PlayerMP(zordz.level, 100, 100, Options.USERNAME, null, -1);
		
	}

	public void init(Zordz zordz) {
		this.zordz = zordz;
		zordz.level.remove(zordz.player);
		zordz.player = new PlayerMP(zordz.level, 100, 100, Options.USERNAME, null, -1);
		zordz.level.add(zordz.player);
		zordz.screen.setOff(0, 0);
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
			if (zordz.player.isDead()) {
				NewGLHandler.setCurrentColor(new float[]{0.0f, 0.0f, 0.0f, 0.4f}, true);
				NewGLHandler.draw2DRect(ox + 0, oy + 0, Zordz.WIDTH, Zordz.HEIGHT, true);
				Drawer.setCol(Color.white);
				Drawer.setCol(Color.RED);
				Text.render("YOU ARE DED", ox + 40, oy + 190, 48, 48);
				Text.render("GAME OVER", ox + 88, oy + 190 + 56, 48, 48);
				Drawer.setCol(Color.white);
				
				backToMenu2.draw();
				newGame.draw();
				if (backToMenu2.clicked()) {
					SoundPlayer.play(Sound.button_clicked);
					zordz.switchState(zordz.titlestate);
				} else if (newGame.clicked()) {
					SoundPlayer.play(Sound.button_clicked);
					zordz.switchState(zordz.gamestate);
					init(zordz);
				}
			}
			renderHUD();
		}
	}

	public void renderHUD() {
		float ox = zordz.screen.xOff;
		float oy = zordz.screen.yOff;
		NewGLHandler.setCurrentColor(new float[] { 0.3f, 0.3f, 0.3f, 0.85f },
				true);
		NewGLHandler.draw2DRect(ox + 0, oy + 2, Zordz.WIDTH, 34, true);
		Drawer.setCol(Color.black);
		NewGLHandler.draw2DRect(ox + 0, oy + 2, Zordz.WIDTH, 34, false);
		NewGLHandler.resetColors();
		if (zordz.player.healthPerc() < 0.33f && ticks < Options.TICK_RATE / 2)
			Drawer.setCol(Color.red);
		Text.render("HP:" + zordz.player.health + "/" + zordz.player.getMaxHealth(), ox + 0, oy + 0, 16, 16);
		Drawer.setCol(Color.white);
		Text.render("WEPS:", ox + 0, oy + 16, 16, 16);
		
		WeaponType type = zordz.player.getCurrentWeapon().getType();
		Weapon weap = zordz.player.getCurrentWeapon();
		boolean active = false;
		//Combat weapon, display the name.
		if (zordz.player.getCombatWeapon().equals(weap)) {
			Drawer.setCol(Color.green);
			active = true;
		} else {
			Drawer.setCol(new Color(150, 150, 150));
		}
		if (!type.canSwitch()) {
			Drawer.setCol(new Color(145, 145, 145));
		} 
		Text.render(zordz.player.getCombatWeapon().getName(), ox + 80, oy + 16, 8, 8);
		if (active) 
			Drawer.setCol(Color.white);
		Drawer.draw(SpriteSheet.sheet, zordz.player.getCombatWeapon().texX, zordz.player.getCombatWeapon().texY, ox + 292, oy + 10, 24, 24);
		//Special weapon, display the name!!
		active = false;
		if (zordz.player.getSpecialWeapon().equals(weap)) {
			Drawer.setCol(Color.green);
			active = true;
		} else {
			Drawer.setCol(Color.LIGHT_GRAY);
		}
		if (!zordz.player.getSpecialWeapon().getType().canSwitch()) {
			Drawer.setCol(Color.gray);
		} 
		Text.render(zordz.player.getSpecialWeapon().getName(), ox + 80, oy + 24, 8, 8);
		if (active) 
			Drawer.setCol(Color.white);
		Drawer.draw(SpriteSheet.sheet, zordz.player.getSpecialWeapon().texX, zordz.player.getSpecialWeapon().texY, ox + 324, oy + 10, 24, 24);
		Drawer.setCol(Color.white);
		Text.render("Z", ox + 300, oy + 0, 8, 8);
		Text.render("X", ox + 332, oy + 0, 8, 8);
		int statusX = 0;
		if (zordz.player.getBurnTicks() < 1) {
			statusX = 1;
		}
		Drawer.draw(SpriteSheet.sheet, statusX, statusY, ox + 372, oy + 2, 32, 32);
		NewGLHandler.resetColors();

	}

	public void tick() {
		if (!paused) {
			zordz.level.tick();
		}
		ticks++;
		if (ticks == Options.TICK_RATE) {
			ticks = 0;
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
