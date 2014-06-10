package zordz.util;

public class Sound {

    public int s;
    public static Sound[] sounds = new Sound[256];
    public static final Sound button_clicked = SoundPlayer.newSound("res/sfx/gui/button_select_new.wav", 0);
    public static final Sound scroll = SoundPlayer.newSound("res/sfx/gui/scroll.wav", 1);
    public static final Sound button_over = SoundPlayer.newSound("res/sfx/gui/button_over.wav", 2);
    public static final Sound checkbox = SoundPlayer.newSound("res/sfx/gui/checkbox.wav", 3);
    public static final Sound hurt = SoundPlayer.newSound("res/sfx/player/hurt.wav", 4);
    public static final Sound melee_swing = SoundPlayer.newSound("res/sfx/items/melee_swing.wav", 5);
    public static final Sound burn_small = SoundPlayer.newSound("res/sfx/player/burn_small.wav", 6);
    public static final Sound bleed_small = SoundPlayer.newSound("res/sfx/player/bleed_small.wav", 7);
	public static final Sound health_pickup = SoundPlayer.newSound("res/sfx/pickup/health_pickup.wav", 8);
	public static final Sound foodkit = SoundPlayer.newSound("res/sfx/pickup/foodkit.wav", 9);
	public static final Sound katana_swing = SoundPlayer.newSound("res/sfx/items/katana_swing01.wav", 10);
    
    public Sound(int source, int id) {
    	this.s = source;
        sounds[id] = this;
    }
    
}
