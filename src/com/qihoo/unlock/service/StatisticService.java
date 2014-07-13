package com.qihoo.unlock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;

import com.qihoo.unlock.model.UnlockInfo;
import com.qihoo.unlock.model.UnlockInfoManager;
import com.qihoo.unlock.utils.TimeUtil;

public class StatisticService extends Service {
	private final String TAG = "StatisticService";
	private ScreenBroadcastReceiver mScreenReceiver;
	private long startTime = System.currentTimeMillis();
	private long endTime = -1;
	private long unlockTime = -1;
	private long totalTime = 0;
	private long count = 0;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.e(TAG, "onCreate");
		mScreenReceiver = new ScreenBroadcastReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		registerReceiver(mScreenReceiver, filter);
		count = UnlockInfoManager.getInstance().getTodayCount();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mScreenReceiver);
	}

	/**
	 * screen
	 */
	private class ScreenBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
				Log.e(TAG, "ACTION_SCREEN_ON");
				startTime = System.currentTimeMillis();
			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				Log.e(TAG, "ACTION_SCREEN_OFF");
				endTime = System.currentTimeMillis();

				// TODO if time is not same day, count and totalTime should be
				// set to 0
				// and info should be apart
				if (DateUtils.isToday(startTime)) {
					UnlockInfo info = new UnlockInfo();
					info.startTime = startTime;
					info.endTime = endTime;
					totalTime += (endTime - startTime);
					info.totalTime = totalTime;
					info.unlockTime = unlockTime;
					info.totalCount = count;
					UnlockInfoManager.getInstance().addUnlockInfo(info);

				} else {
					long apartTime = TimeUtil.getStartTimeOfTheDate(endTime);
					UnlockInfo info1 = new UnlockInfo();
					UnlockInfo info2 = new UnlockInfo();
					info1.startTime = startTime;
					info2.startTime = apartTime;
					info1.endTime = apartTime;
					info2.endTime = endTime;
					totalTime += (apartTime - startTime);
					info1.totalTime = totalTime;
					totalTime = endTime - apartTime;
					info2.totalTime = totalTime;
					if (unlockTime != -1 && !DateUtils.isToday(unlockTime)) {
						// unlock yesterday
						info1.unlockTime = unlockTime;
						info1.totalCount = count;
						count = 0;
						info2.unlockTime = -1;
						info2.totalCount = count;
					} else {
						info1.unlockTime = -1;
						info1.totalCount = count - 1;
						count = 1;
						info2.unlockTime = unlockTime;
						info2.totalCount = count;
					}
					UnlockInfoManager.getInstance().addUnlockInfo(info1);
					UnlockInfoManager.getInstance().addUnlockInfo(info2);
				}
				unlockTime = -1;
			} else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
				Log.e(TAG, "ACTION_USER_PRESENT");
				unlockTime = System.currentTimeMillis();
				count++;
			}
		}
	}
}
