package zordz.state;

public abstract class State {

	public static enum GameState { 
		TITLE, GAME
	}
	public abstract void render();
	public abstract void tick();
	public abstract int getID();
}
