package zordz.state;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import zordz.Zordz;
import zordz.gfx.Drawer;
import zordz.gfx.Text;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;
import cjaf.tools.NewGLHandler;

public class TitleState extends State {
	Zordz zordz;
	Texture titlescreen = NewGLHandler.loadTexture("res/titlescreen.png");
	Level titleLevel = new Level(640 / 32, 480 / 32);
	Button sp = new Button("Singleplayer", Color.red, 232, 200, 12, 12);
	public TitleState(Zordz zordz) {
		this.zordz = zordz;
		for (int x = 0; x < titleLevel.getWidth(); x++) {
			for (int y = 0; y < titleLevel.getHeight(); y++) {
				titleLevel.setTileIDAt(x, y, Tile.GRASS.getID());
			}
		}
	}

	public void render() {
		titleLevel.render();
		int titleX = 180;
		Drawer.setCol(new Color(255, 204, 102));
		Text.render("Z", titleX, 40, 32, 32);
		Drawer.setCol(Color.red);
		Text.render("ordzman", titleX + 32, 56, 32, 16);
		Drawer.setCol(Color.white);
		sp.draw();
	}
	public void tick() {
		if (sp.clicked()) {
			SoundPlayer.play(Sound.button_clicked);
			zordz.state = zordz.gamestate;
		}
	}

	private class Button {
		String text;
		Color col;
		float x, y;
		int tx, tsize;
		int width = (int) (256 / 1.5f), height = (int) (64 / 1.5f);
		final Texture tex = NewGLHandler.loadTexture("res/titlescr/button_plate.png");
		public Button(String text, Color col, float x, float y, int tx, int ts) {
			this.text = text;
			this.col = col;
			this.x = x;
			this.y = y;
			this.tx = tx;
			tsize = ts;
		}

		public void draw() {
			NewGLHandler.drawTexture2D(tex, x, y, width, height);
			Drawer.setCol(col);
			Text.render(text, x + tx, y + 13, tsize, tsize);
			Drawer.setCol(Color.white);
			if (isMouseInside()) {
				NewGLHandler.setCurrentColor(new float[]{1.0f, 1.0f, 1.0f, 0.5f}, true);
				NewGLHandler.draw2DRect(x, y, width, height, true);
				Drawer.setCol(Color.white);
			}
		}

		public boolean isMouseInside() {
			return (Mouse.getX() > x && Mouse.getX() < x + width
					&& 480-Mouse.getY() > y && 480-Mouse.getY() < y + height);
		}

		public boolean clicked() {
			if (isMouseInside()) {
				while (Mouse.next()){
					if (!Mouse.getEventButtonState()) {
						if (Mouse.getEventButton() == 0) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	public int getID() {
		return 0;
	}
}
