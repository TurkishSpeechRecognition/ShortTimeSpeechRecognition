package main.util;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.TargetDataLine;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 */
public class SoundRecorder {
	private static SoundRecorder INSTANCE;
	// record duration, in milliseconds
	public static final long RECORD_TIME = 6000; // 6 seconds

	// path of the wav file
	File wavFile = new File("./wavfiles/command.wav");

	// format of audio file
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

	// the line from which audio data is captured
	TargetDataLine line;

	/**
	 * Defines an audio format
	 */
	AudioFormat getAudioFormat() {
		float sampleRate = 16000;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	private SoundRecorder() {
	}

	public static SoundRecorder getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SoundRecorder();
		return INSTANCE;
	}

	/**
	 * Captures the sound and record into a WAV file
	 */
	public void start() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AudioFormat format = getAudioFormat();
					DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

					// checks if system supports the data line
					if (!AudioSystem.isLineSupported(info)) {
						System.out.println("Line not supported");
						System.exit(0);
					}
					line = (TargetDataLine) AudioSystem.getLine(info);
					line.open(format);
					line.start(); // start capturing

					System.out.println("Start capturing...");

					AudioInputStream ais = new AudioInputStream(line);

					System.out.println("Start recording...");

					// start recording
					AudioSystem.write(ais, fileType, wavFile);

				} catch (Exception e) {
					e.printStackTrace();
					finish();
				}

			}
		});
		thread.start();
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	public void finish() {
		if (line != null) {
			line.stop();
			line.close();
			System.out.println("Finished");
		}
	}

	public void setMicrophoneVolume(int value) {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();

		for (int i = 0; i < mixerInfos.length; i++) {
			Mixer mixer = AudioSystem.getMixer(mixerInfos[i]);
			int maxLines = mixer.getMaxLines(Port.Info.MICROPHONE);
			Port lineIn = null;
			FloatControl volCtrl = null;
			if (maxLines > 0) {
				try {
					lineIn = (Port) mixer.getLine(Port.Info.MICROPHONE);
					lineIn.open();
					CompoundControl cc = (CompoundControl) lineIn.getControls()[0];
					Control[] controls = cc.getMemberControls();
					for (Control c : controls) {
						if (c instanceof FloatControl) {
							volCtrl = (FloatControl) c;
							volCtrl.setValue((float) value / 100);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public int getMicrophoneVolume() {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		System.out.println("Mixer Infos : " + mixerInfos.length);
		for (int i = 0; i < mixerInfos.length; i++) {
			Mixer mixer = AudioSystem.getMixer(mixerInfos[i]);
			int maxLines = mixer.getMaxLines(Port.Info.MICROPHONE);
			System.out.println("Max Lines : " + maxLines);
			Port lineIn = null;
			FloatControl volCtrl = null;
			if (maxLines > 0) {
				try {
					lineIn = (Port) mixer.getLine(Port.Info.MICROPHONE);
					lineIn.open();
					CompoundControl cc = (CompoundControl) lineIn.getControls()[0];
					Control[] controls = cc.getMemberControls();
					for (Control c : controls) {
						if (c instanceof FloatControl) {
							volCtrl = (FloatControl) c;
							return (int) (volCtrl.getValue() * 100);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return -1;
	}
}