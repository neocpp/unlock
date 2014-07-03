package com.qihoo.unlock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class StatisticService extends Service {

	private ScreenBroadcastReceiver mScreenReceiver = new ScreenBroadcastReceiver();

	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	private class ScreenBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {

			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {

			} else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {

			}
		}
	}

	/**
	 * 停止screen状态更新
	 */
	public void stopScreenStateUpdate() {
		unregisterReceiver(mScreenReceiver);
	}

	/**
	 * 启动screen状态广播接收器
	 */
	private void startScreenBroadcastReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_USER_PRESENT);
		registerReceiver(mScreenReceiver, filter);
	}

}
