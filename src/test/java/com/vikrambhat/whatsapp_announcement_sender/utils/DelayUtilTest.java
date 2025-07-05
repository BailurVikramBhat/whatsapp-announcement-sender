package com.vikrambhat.whatsapp_announcement_sender.utils;

import org.junit.jupiter.api.Test;

import com.vikrambhat.whatsapp_announcement_sender.configs.Config;
import com.vikrambhat.whatsapp_announcement_sender.utils.DelayUtil;

import static org.junit.jupiter.api.Assertions.*;

class DelayUtilTest {

	@SuppressWarnings("static-method")
	@Test
	void randomDelayWithinConfiguredRange() {
		int min = Config.getInt("delay.min.millis");
		int max = Config.getInt("delay.max.millis");

		for (int i = 0; i < 100; i++) {
			int d = DelayUtil.getRandomDelay();
			assertTrue(d >= min && d <= max, "Delay " + d + " outside [" + min + "," + max + "]");
		}
	}
}
