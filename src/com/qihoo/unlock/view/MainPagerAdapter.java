package com.qihoo.unlock.view;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MainPagerAdapter extends PagerAdapter {
	private List<MainView> mViewList;
	
	public MainPagerAdapter(List<MainView> list){
		mViewList = list;
	}

	@Override
	public int getCount() {
		return mViewList != null ? mViewList.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = mViewList.get(position);
		container.addView(view, 0);
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mViewList.get(position));
	}

}
