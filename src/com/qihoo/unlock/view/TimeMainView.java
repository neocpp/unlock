package com.qihoo.unlock.view;

import android.content.Context;

import com.qihoo.unlock.R;
import com.qihoo.unlock.model.UnlockInfoManager;
import com.qihoo.unlock.utils.TimeUtil;

public class TimeMainView extends MainView {

	public TimeMainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected long getTodayTotal() {
		// TODO Auto-generated method stub
		return TimeUtil.toMinute(UnlockInfoManager.getInstance().getTodayTime());
	}

	@Override
	protected long getYesterdayTotal() {
		// TODO Auto-generated method stub
		return TimeUtil.toMinute(UnlockInfoManager.getInstance().getYesterdayTime());
	}

	@Override
	protected int getUnitId() {
		// TODO Auto-generated method stub
		return R.string.minute;
	}

	@Override
	public int getTitleId() {
		// TODO Auto-generated method stub
		return R.string.title_use_time;
	}

}
