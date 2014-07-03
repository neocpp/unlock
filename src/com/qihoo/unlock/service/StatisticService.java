package com.qihoo.unlock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class StatisticService extends Service {
	private ScreenBroadcastReceiver mScreenReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mScreenReceiver = new ScreenBroadcastReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		registerReceiver(mScreenReceiver, filter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mScreenReceiver);
	}

	/**
	 * screen状态广播接收者
	 */
	private class ScreenBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {

			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {

			} else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {

			}
		}
	}
}
