package zordz.util;

public class Sound {

    public int s;
    public static Sound[] sounds = new Sound[256];
    public static final Sound button_clicked = SoundPlayer.newSound("res/sfx/button_select_new.wav", 0);
    public static final Sound scroll = SoundPlayer.newSound("res/sfx/scroll.wav", 1);
    public static final Sound button_over = SoundPlayer.newSound("res/sfx/button_over.wav", 2);
    public static final Sound checkbox = SoundPlayer.newSound("res/sfx/checkbox.wav", 3);
    public static final Sound hurt = SoundPlayer.newSound("res/sfx/hurt.wav", 4);
    public static final Sound melee_swing = SoundPlayer.newSound("res/sfx/melee_swing.wav", 5);
    
    public Sound(int source, int id) {
            this.s = source;
            sounds[id] = this;
    }
    
}
