package zordz.state;

import java.io.File;

import zordz.Zordz;
import zordz.level.Level;
import zordz.level.tile.Tile;

public class ChooseLevelState extends State {
	Zordz zordz;
	Level stateLevel = new Level(20, 15);
	ScrollPane selectLevel = new ScrollPane("Select Level", 80, 60, 480, (480 / 12 * 9) + 2, 0, 0, null);
	
	public ChooseLevelState(Zordz zordz) {
		this.zordz = zordz;
		for (int x = 0; x < stateLevel.getWidth(); x++) {
			for (int y = 0; y < stateLevel.getHeight(); y++) {
				stateLevel.setTileIDAt(x, y, Tile.WATER.getID());
			}
		}
		loadLevels();
	}

	public void loadLevels() {
		File levelsDir = new File("res/levels/");
//		for (File x : levelsDir.listFiles()) {
//			selectLevel.add(x.getName().substring(0, x.getName().length()-4));
//		}
		//Just some random strings to test
		selectLevel.add("Hello");
		selectLevel.add("World");
		selectLevel.add("TOPKEK LAL");
		selectLevel.add("Pain ain't enuff for me");
		selectLevel.add("CJAF!");
		selectLevel.add("Castleknock");
		selectLevel.add("TYT B)");
		selectLevel.add("this is getting funny lol");
		selectLevel.add("Is cool!");
		
		selectLevel.add("refl lidl");
		selectLevel.add("JavaCakess");
		selectLevel.add("Mycroecoed");
		selectLevel.add("CFXR is amazing!");
		selectLevel.add("I love LWJGL");
		selectLevel.add("It's so cool and amazing!");
	}
	
	public void render() {
		stateLevel.render();
		selectLevel.render();
	}

	public void tick() {
		stateLevel.tick();
	}

	public int getID() {
		return 2;
	}
	
}
