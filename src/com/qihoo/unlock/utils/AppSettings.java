package com.qihoo.unlock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.qihoo.unlock.UnlockApplication;

public class AppSettings {
	private static volatile AppSettings instance;
	private Editor editor = null;
	private SharedPreferences sp = null;
	private long alarmUnlockCount;
	private long alarmUseTime;

	private final String ALARM_UNLOCK_COUNT = "alarm_unlock_count";
	private final String ALARM_USE_TIME = "alarm_use_time";

	public static AppSettings getInstance() {
		if (instance == null) {
			synchronized (AppSettings.class) {
				if (instance == null) {
					instance = new AppSettings(UnlockApplication.getContext());
				}
			}
		}
		return instance;
	}

	private AppSettings(Context context) {
		sp = context.getSharedPreferences("settings",
				android.content.Context.MODE_PRIVATE);
		if (sp != null) {
			editor = sp.edit();
			alarmUnlockCount = sp.getLong(ALARM_UNLOCK_COUNT, 0);
			alarmUseTime = sp.getLong(ALARM_USE_TIME, 0);
		}
	}

	public void setAlarmUnlockCount(long count) {
		alarmUnlockCount = count;
		if (editor != null) {
			editor.putLong(ALARM_UNLOCK_COUNT, count);
			editor.commit();
		}
	}

	public void setAlarmUseTime(long time) {
		alarmUseTime = time;
		if (editor != null) {
			editor.putLong(ALARM_USE_TIME, time);
			editor.commit();
		}
	}

	public long getAlarmUnlockCount() {
		return alarmUnlockCount;
	}

	public long setAlarmUseTime() {
		return alarmUseTime;
	}

}
