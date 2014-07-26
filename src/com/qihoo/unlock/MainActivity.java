package com.qihoo.unlock;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qihoo.unlock.utils.IncrementAnimationUtil;
import com.qihoo.unlock.view.CountMainView;
import com.qihoo.unlock.view.MainPagerAdapter;
import com.qihoo.unlock.view.MainView;
import com.qihoo.unlock.view.MyColorBackground;
import com.qihoo.unlock.view.TimeMainView;

public class MainActivity extends Activity implements OnPageChangeListener {

	private ViewPager mViewPager;
	private MyColorBackground mBackground;
	private List<MainView> mViewList;
	private TextView mTitleText;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);
		mBackground = new MyColorBackground();

		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
		mViewList = new ArrayList<MainView>();
		MainView view = new CountMainView(this);
		mViewList.add(view);
		view = new TimeMainView(this);
		mViewList.add(view);
		MainPagerAdapter pagerAdapter = new MainPagerAdapter(mViewList);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnPageChangeListener(this);

		LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.main_layout_root);
		layoutRoot.setBackground(mBackground);

		IncrementAnimationUtil.getInstance().addObserver(mBackground);
		IncrementAnimationUtil.getInstance().setChangeLevel(1);
		IncrementAnimationUtil.getInstance().startAnimation();

		mTitleText = (TextView) findViewById(R.id.title);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewList.get(mViewPager.getCurrentItem()).refreshData();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int index) {
		mTitleText.setText(mViewList.get(index).getTitleId());
		mViewList.get(index).refreshData();
		IncrementAnimationUtil.getInstance().startAnimation();
	}

}
