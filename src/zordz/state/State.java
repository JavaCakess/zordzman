package zordz.state;

public abstract class State {

	public static enum GameState { 
		TITLE, GAME, CHOOSELEVEL, OPTIONS
	}
	public abstract void render();
	public abstract void tick();
	public abstract int getID();
}
