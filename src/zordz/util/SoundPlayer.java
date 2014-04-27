package zordz.util;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.util.WaveData;

import zordz.Options;

public class SoundPlayer {

	public static boolean play = true;
	
	public static void init() {

	}

	public static Sound newSound(String path, int id) {
		if (!play) { return null; }
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
		if (!play) { return; }
		alSourcef(sound.s, AL_GAIN, Options.SOUND_LEVEL / 100f);
		alSourcePlay(sound.s);
	}
	
	public static void play(Sound sound, float volumePercent) {
		if (!play) { return; }
		alSourcef(sound.s, AL_GAIN, (Options.SOUND_LEVEL / 100f) * volumePercent);
		alSourcePlay(sound.s);
	}

	public static void deleteAll() {

	}
}