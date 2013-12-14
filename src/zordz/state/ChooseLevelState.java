package zordz.state;

import java.awt.Color;
import java.io.File;

import zordz.Zordz;
import zordz.level.Level;
import zordz.level.tile.Tile;
import zordz.util.Sound;
import zordz.util.SoundPlayer;

public class ChooseLevelState extends State {
	Zordz zordz;
	Level stateLevel = new Level(20, 15);
	ScrollPane selectLevel = new ScrollPane("Select Level", 80, 60, 480, (480 / 12 * 9) + 2, 0, 0, null);
	Button select;
	Button backToTitle;
	
	public ChooseLevelState(Zordz zordz) {
		this.zordz = zordz;
		for (int x = 0; x < stateLevel.getWidth(); x++) {
			for (int y = 0; y < stateLevel.getHeight(); y++) {
				stateLevel.setTileIDAt(x, y, Tile.WATER.getID());
			}
		}
		select = new Button("Select", Color.cyan, Zordz.WIDTH / 2 - (Button.width / 2), 471 - Button.height, 34, 16);
		backToTitle = new Button("Go Back", Color.yellow, Zordz.WIDTH / 2 - (Button.width / 2), 3, 26, 16);
		loadLevels();
	}

	public void loadLevels() {
		File levelsDir = new File("res/levels/");
		for (File x : levelsDir.listFiles()) {
			selectLevel.add(x.getName().substring(0, x.getName().length()-4));
		}
	}
	
	public void render() {
		stateLevel.render();
		selectLevel.render();
		select.draw();
		backToTitle.draw();
	}

	public void tick() {
		stateLevel.tick();
		
		if (select.clicked()) {
			zordz.level = Level.loadLevel("res/levels/" + selectLevel.getSelected() + ".lvl");
			zordz.switchState(zordz.gamestate);
			SoundPlayer.play(Sound.button_clicked);
		} else if (backToTitle.clicked()) {
			zordz.switchState(zordz.titlestate);
			SoundPlayer.play(Sound.button_clicked);
		}
	}

	public int getID() {
		return 2;
	}
	
}
