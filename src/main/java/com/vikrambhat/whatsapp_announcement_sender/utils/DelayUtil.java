package com.vikrambhat.whatsapp_announcement_sender.utils;

import java.util.Random;

import com.vikrambhat.whatsapp_announcement_sender.configs.Config;

public class DelayUtil {
	private static final Random RAND = new Random();

	private DelayUtil() {
	}

	public static int getRandomDelay() {
		int min = Config.getInt("delay.min.millis");
		int max = Config.getInt("delay.max.millis");
		return RAND.nextInt(max - min + 1) + min;
	}

	public static void randomSleep() throws InterruptedException {
		int d = getRandomDelay();
		System.out.println("Sleeping for + " + d / 1000 + " seconds...");
		Thread.sleep(d);
	}
}
