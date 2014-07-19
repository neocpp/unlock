package com.qihoo.unlock.view;

import com.qihoo.unlock.R;

import android.content.Context;

public class TimeMainView extends MainView{

	public TimeMainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected long getTodayTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected long getYesterdayTotal() {
		// TODO Auto-generated method stub
		return 0;
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
