package zordz.util;

public class Sound {

    public int s;
    public static Sound[] sounds = new Sound[255];
    public Sound(int source, int id) {
            this.s = source;
            sounds[id] = this;
    }
    
}
