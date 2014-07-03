package com.qihoo.unlock;

import android.app.Application;
import android.content.Intent;

import com.qihoo.unlock.service.StatisticService;

public class UnlockApplication extends Application {

	public void onCreate() {
		startService(new Intent(this, StatisticService.class));
	};
}
