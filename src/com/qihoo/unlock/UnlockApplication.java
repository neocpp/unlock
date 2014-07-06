package com.qihoo.unlock;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.qihoo.unlock.database.SQLiteHelper;
import com.qihoo.unlock.service.StatisticService;

public class UnlockApplication extends Application {
	private static Context instance = null;

	public static Context getContext() {
		return instance;
	}

	@Override
	public void onCreate() {
		Log.e("wcb", "application oncreate()");
		super.onCreate();
		instance = this;
		SQLiteHelper.getInstance();
		startService(new Intent(this, StatisticService.class));
	};

}
