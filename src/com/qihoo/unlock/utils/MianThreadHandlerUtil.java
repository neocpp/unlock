package com.qihoo.unlock.utils;

import android.os.Handler;
import android.os.Looper;

public class MianThreadHandlerUtil {
	private static Handler mHandler = null;

	public static Handler getMainThreadHandler() {
		if (mHandler == null) {
			mHandler = new Handler(Looper.getMainLooper());
		}
		return mHandler;
	}
}
