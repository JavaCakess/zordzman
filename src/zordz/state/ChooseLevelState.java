package zordz.state;

import zordz.Zordz;

public class ChooseLevelState extends State {
	Zordz zordz;
	
	ScrollPane selectLevel = new ScrollPane("Select Level", 80, 80, 480, 480 / 12 * 9, 0, 0, null);
	
	public ChooseLevelState(Zordz zordz) {
		this.zordz = zordz;
	}

	public void render() {
		selectLevel.render();
	}

	public void tick() {
		
	}

	public int getID() {
		return 2;
	}
	
}
