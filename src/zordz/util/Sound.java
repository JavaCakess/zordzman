package zordz.util;

public class Sound {

    public int s;
    public static Sound[] sounds = new Sound[256];
    public static final Sound button_clicked = SoundPlayer.newSound("res/sfx/button_select.wav", 0);
    public static final Sound scroll = SoundPlayer.newSound("res/sfx/scroll.wav", 1);
    
    public Sound(int source, int id) {
            this.s = source;
            sounds[id] = this;
    }
    
}
