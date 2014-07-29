package com.qihoo.unlock;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

import com.qihoo.unlock.utils.IncrementAnimationUtil;
import com.qihoo.unlock.view.CountMainView;
import com.qihoo.unlock.view.DetailListAdapter;
import com.qihoo.unlock.view.MainPagerAdapter;
import com.qihoo.unlock.view.MainView;
import com.qihoo.unlock.view.MyColorBackground;
import com.qihoo.unlock.view.SingleColorDrawable;
import com.qihoo.unlock.view.TimeDetailListAdapter;
import com.qihoo.unlock.view.TimeMainView;
import com.qihoo.unlock.view.UnlockDetailListAdapter;

public class MainActivity extends CustomActivity implements OnPageChangeListener {

	private ViewPager mViewPager;
	private MyColorBackground mBackground;
	private List<MainView> mViewList;
	private RadioGroup mRadioGroup;
	private SlidingDrawer slidingDrawer;
	private DetailListAdapter[] mAdapters;
	private ExpandableListView mListView;
	MyColorBackground mListBackground;
	SingleColorDrawable slidingDrawable;

	@SuppressWarnings("deprecation")
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

		mListView = (ExpandableListView) findViewById(R.id.unlock_detail_list);
		mListView.setGroupIndicator(null);
		mAdapters = new DetailListAdapter[2];
		mAdapters[0] = new UnlockDetailListAdapter(this);
		mAdapters[1] = new TimeDetailListAdapter(this);
		mListView.setDivider(null);
		mListView.setChildDivider(null);
		mListBackground = new MyColorBackground();
		mListView.setBackground(mListBackground);

		slidingDrawer = (SlidingDrawer) findViewById(R.id.sliding_detail);
		final ImageView arrow = (ImageView) findViewById(R.id.sliding_handle_arrow);
		final TextView handleText = (TextView) findViewById(R.id.sliding_handle_text);
		final ViewGroup slidingHandle = (ViewGroup) findViewById(R.id.sliding_handle);
		slidingDrawable = new SingleColorDrawable(1, true);
		slidingHandle.setBackground(slidingDrawable);

		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

			@Override
			public void onDrawerOpened() {
				handleText.setVisibility(View.GONE);
				arrow.setImageResource(R.drawable.down_arrow);
				mAdapters[mViewPager.getCurrentItem()].refreshData();
				mAdapters[mViewPager.getCurrentItem()].notifyDataSetChanged();
				int n = mListView.getCount();
				for (int i = 0; i < n; i++) {
					mListView.expandGroup(i);
				}
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				handleText.setVisibility(View.VISIBLE);
				arrow.setImageResource(R.drawable.up_arrow);

			}
		});

		setMainTitle(R.string.main_title);

		View title_layout = (View) findViewById(R.id.titlebar_layout_root);
		SingleColorDrawable back = new SingleColorDrawable(0, false);
		title_layout.setBackground(back);
		IncrementAnimationUtil.getInstance().addObserver(back);
		IncrementAnimationUtil.getInstance().addObserver(mBackground);
		IncrementAnimationUtil.getInstance().addObserver(mListBackground);
		IncrementAnimationUtil.getInstance().addObserver(slidingDrawable);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mListView.setAdapter(mAdapters[mViewPager.getCurrentItem()]);
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
		for (int i = 0; i < mViewList.size(); i++) {
			if (i != index) {
				mViewList.get(index).unRegisterAnimate();
			}
		}
		mViewList.get(index).refreshData();
		mViewList.get(index).registerAnimate();

		mListView.setAdapter(mAdapters[index]);
		mAdapters[index].notifyDataSetChanged();

		if (mRadioGroup.getChildCount() > index) {
			RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(index);
			radioButton.setChecked(true);
		}

		IncrementAnimationUtil.getInstance().setChangeLevel(mViewList.get(index).getScore());
		IncrementAnimationUtil.getInstance().startAnimation();
	}

}
