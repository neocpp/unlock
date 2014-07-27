package com.qihoo.unlock;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.qihoo.unlock.utils.IncrementAnimationUtil;
import com.qihoo.unlock.view.CountMainView;
import com.qihoo.unlock.view.MainPagerAdapter;
import com.qihoo.unlock.view.MainView;
import com.qihoo.unlock.view.MyColorBackground;
import com.qihoo.unlock.view.SingleColorDrawable;
import com.qihoo.unlock.view.TimeMainView;

public class MainActivity extends CustomActivity implements
		OnPageChangeListener {

	private ViewPager mViewPager;
	private MyColorBackground mBackground;
	private List<MainView> mViewList;
	private TextView mTitleText;
	private RadioGroup mRadioGroup;
	private int[] mLevel = new int[]{5, 10};

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity, R.layout.main_titlebar_layout);
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

		mRadioGroup = (RadioGroup) findViewById(R.id.pagerRadioGroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int count = group.getChildCount();
				for (int index = 0; index < count; index++) {
					RadioButton btn = (RadioButton) group.getChildAt(index);
					if (btn.getId() == checkedId) {
						if (mViewPager.getCurrentItem() != index) {
							mViewPager.setCurrentItem(index, true);
						}
					}
				}
			}
		});
		
		setMainTitle(R.string.main_title);
		
		View title_layout = (View) findViewById(R.id.titlebar_layout_root);
		SingleColorDrawable back = new SingleColorDrawable();
		title_layout.setBackgroundDrawable(back);
		IncrementAnimationUtil.getInstance().addObserver(back);
		IncrementAnimationUtil.getInstance().addObserver(mBackground);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MainView mainView = mViewList.get(mViewPager.getCurrentItem());
		mainView.refreshData();
		mainView.registerAnimate();
		IncrementAnimationUtil.getInstance().setChangeLevel(mainView.getScore());
		IncrementAnimationUtil.getInstance().startAnimation();
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
		IncrementAnimationUtil.getInstance().endAnimation();
		for(int i=0;i<mViewList.size();i++){
			if(i!=index){
				mViewList.get(index).unRegisterAnimate();
			}
		}
		mViewList.get(index).refreshData();
		mViewList.get(index).registerAnimate();
		
		if (mRadioGroup.getChildCount() > index) {
			RadioButton radioButton = (RadioButton) mRadioGroup
					.getChildAt(index);
			radioButton.setChecked(true);
		}
		
		IncrementAnimationUtil.getInstance().setChangeLevel(mViewList.get(index).getScore());
		IncrementAnimationUtil.getInstance().startAnimation();
	}

}
