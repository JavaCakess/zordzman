package zordz.util;
import static org.lwjgl.openal.AL10.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.util.WaveData;

public class SoundPlayer {

	public static void init() {

	}

	public static Sound newSound(String path, int id) {
		WaveData data = null;
		try {
			data = WaveData.create(new BufferedInputStream(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int buffer = alGenBuffers();
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		int source = alGenSources();
		alSourcei(source, AL_BUFFER, buffer);
		return new Sound(source, id);
	}

	public static void play(Sound sound) {
		alSourcePlay(sound.s);
	}

	public static void deleteAll() {

	}
}