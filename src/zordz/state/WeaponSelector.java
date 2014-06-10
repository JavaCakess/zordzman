package zordz.state;

import java.awt.Color;

import zordz.gfx.Drawer;
import zordz.gfx.NewGLHandler;
import zordz.gfx.SpriteSheet;
import zordz.gfx.Text;
import zordz.weapons.Weapon;

public class WeaponSelector {

	Weapon[] weapons;
	WeaponSelectorBox[] boxes;
	Weapon weap;
	float x, y;
	public static int select_cd = 15;
	int index = 0;
	public class WeaponSelectorBox extends Button {

		public WeaponSelectorBox() {
			super("", Color.white, 0, 0, 0, 0);
			width = (int) (32);
			height = (int) (32);
		}

		Weapon wep; // The weapon we're displaying.

		public void render() {

			// Draw a box.
			if (isMouseInside(0, 0)) {
				Drawer.setCol(Color.lightGray);
			} else {
				Drawer.setCol(Color.gray);
			}
			NewGLHandler.setCurrentColor(new float[]{0.4f, 0.4f, 0.4f, 0.5f}, true);
			NewGLHandler.draw2DRect(x, y, 32, 32, true);
			if (isMouseInside(0, 0)) {
				Drawer.setCol(Color.BLUE);
			} else {
				Drawer.setCol(Color.gray);
			}
			NewGLHandler.draw2DRect(x, y, 32, 32, false);
			NewGLHandler.resetColors();

			// And the weapon, too!
			Drawer.draw(SpriteSheet.sheet, wep.texX, wep.texY,
					x + 4, y + 4, 24, 24);

			// When they click... swap out the weapons.
			if (this.clicked()) {
				if (select_cd < 1) {
					Weapon swap;

					swap = wep;
					wep = weap;
					weap = swap;
				}
				select_cd = 15;
			}

		}

	}

	public WeaponSelector (Weapon weap, Weapon[] weapons, float x, float y) {
		boxes = new WeaponSelectorBox[3];
		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = new WeaponSelectorBox();
			boxes[i].y = y - 48;
			boxes[i].wep = weapons[i];
		}
		this.weapons = weapons;
		this.weap = weap;
		this.x = x;
		this.y = y;
		boxes[0].x = x - 32;
		boxes[1].x = x + 16;
		boxes[2].x = x + 64;
	}

	public void render() {
		NewGLHandler.setCurrentColor(new float[]{0.4f, 0.4f, 0.4f, 0.5f}, true);
		NewGLHandler.draw2DRect(x, y, 64, 64, true);
		Drawer.setCol(Color.gray);
		NewGLHandler.draw2DRect(x, y, 64, 64, false);
		NewGLHandler.draw2DRect(x + 1, y + 1, 64 - 2, 64 - 2, false);
		NewGLHandler.resetColors();

		// And the weapon, too!
		Drawer.draw(SpriteSheet.sheet, weap.texX, weap.texY,
				x + 8, y + 8, 48, 48);

		int j = 0;
		for (int i = 0; i < boxes.length; i++) {
			boxes[i].wep = weapons[i];
			if (j == 3) { break; }
			j++;
			boxes[i].render();
		}

		// We probably should put the name of the weapon, too.
		String wepName = weap.getName();
		Text.render(wepName, (x + 32) - (8 * wepName.length()), y + 64, 16, 16);
		// As well as it's type.
		String typeName = weap.getType().getName();
		Text.render(typeName, (x + 32) - (4 * typeName.length()), y + 64 + 16, 8, 8);
		
	}
}
