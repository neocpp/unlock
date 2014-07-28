package com.qihoo.unlock.view;

import com.qihoo.unlock.R;
import com.qihoo.unlock.model.UnlockInfoManager;

import android.content.Context;

public class CountMainView extends MainView{

	public CountMainView(Context context) {
		super(context);
	}

	@Override
	protected long getTodayTotal() {
		return UnlockInfoManager.getInstance().getTodayCount();
	}

	@Override
	protected long getYesterdayTotal() {
		// TODO Auto-generated method stub
		return UnlockInfoManager.getInstance().getYesterdayCount();
	}

	@Override
	protected int getUnitId() {
		// TODO Auto-generated method stub
		return R.string.count;
	}

	@Override
	public int getTitleId() {
		// TODO Auto-generated method stub
		return R.string.title_unlock_count;
	}

	@Override
	DetailListAdapter createAdapter(Context context) {
		// TODO Auto-generated method stub
		return new UnlockDetailListAdapter(context);
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 20;
	}

}
