package main.java.util;

import javafx.scene.media.AudioClip;

public class SoundPlayer {

	private static final String BEEP_PATH = "/main/resources/sounds/beep.wav";
	private static final String SUCCESS_PATH = "/main/resources/sounds/success.wav";
	private static final String ERROR_PATH = "/main/resources/sounds/error.wav";

	public static void play(String mediaPath) {
		AudioClip sound = new AudioClip(SoundPlayer.class.getResource(mediaPath).toString());
		sound.play();
	}

	public static void playBeep() {
		play(BEEP_PATH);
	}

	public static void playSuccess() {
		play(SUCCESS_PATH);
	}

	public static void playError() {
		play(ERROR_PATH);
	}

}
