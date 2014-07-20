package com.qihoo.unlock.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static final int SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;
	// 白天时长
	public static final long MILLIS_IN_EARLYDAYTIME = 1000L * 60 * 60 * 6;
	public static final long MILLIS_IN_DAYTIME = 1000L * 60 * 60 * 18;

	public static long getStartTimeOfTheDate(long date) {
		return date / MILLIS_IN_DAY * MILLIS_IN_DAY;
	}

	public static long getYesterdayStartTimeOfTheDate(long date) {
		return date / MILLIS_IN_DAY * MILLIS_IN_DAY - MILLIS_IN_DAY;
	}

	public static long getNightTimeOfTheDate(long date) {
		return date / MILLIS_IN_DAY * MILLIS_IN_DAY + MILLIS_IN_DAYTIME;
	}

	public static long getDayTimeOfTheDate(long date) {
		return date / MILLIS_IN_DAY * MILLIS_IN_DAY + MILLIS_IN_EARLYDAYTIME;
	}

	public static String getTimeString(long date) {
		return new SimpleDateFormat("HH:mm ss").format(new Date(date));
	}

	public static long getOneDayTime() {
		return MILLIS_IN_DAY;
	}

	public static long toMinute(long millonSecond) {
		return millonSecond / 1000 / 60;
	}
}