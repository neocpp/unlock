package com.qihoo.unlock.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

import com.qihoo.unlock.R;
import com.qihoo.unlock.utils.IncrementAnimationUtil;

abstract public class MainView extends LinearLayout {
	private DetailListAdapter mAdapter;
	private ExpandableListView mListView;
	private TextView todayTotalText;
	private TextView yesterdayTotalText;
	private TextView totdayAddText;
	private Context mContext;
	private SlidingDrawer slidingDrawer;
	ProgressCircle progressBack;

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public MainView(Context context) {
		super(context);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.main_view, this);

		todayTotalText = (TextView) findViewById(R.id.unlock_counts);
		BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.circle_under);
		Bitmap bmp = bmpDraw.getBitmap();
		progressBack = new ProgressCircle(bmp);
		IncrementAnimationUtil.getInstance().addObserver(progressBack);
		todayTotalText.setBackground(progressBack);
		yesterdayTotalText = (TextView) findViewById(R.id.yesterday_total);
		totdayAddText = (TextView) findViewById(R.id.today_add);

		mListView = (ExpandableListView) findViewById(R.id.unlock_detail_list);
		mListView.setGroupIndicator(null);
		mAdapter = new DetailListAdapter(context);
		mListView.setAdapter(mAdapter);
		mListView.setDivider(null);
		mListView.setChildDivider(null);
		MyColorBackground background = new MyColorBackground();
		IncrementAnimationUtil.getInstance().addObserver(background);
		mListView.setBackground(background);

		slidingDrawer = (SlidingDrawer) findViewById(R.id.sliding_detail);
		final ImageView arrow = (ImageView) findViewById(R.id.sliding_handle_arrow);
		final TextView handleText = (TextView) findViewById(R.id.sliding_handle_text);
		final ViewGroup slidingHandle = (ViewGroup) findViewById(R.id.sliding_handle);
		SlidingHandleDrawable slidingDrawable = new SlidingHandleDrawable();
		IncrementAnimationUtil.getInstance().addObserver(slidingDrawable);
		slidingHandle.setBackground(slidingDrawable);

		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

			@Override
			public void onDrawerOpened() {
				handleText.setVisibility(View.GONE);
				arrow.setImageResource(R.drawable.down_arrow);
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				handleText.setVisibility(View.VISIBLE);
				arrow.setImageResource(R.drawable.up_arrow);

			}
		});

		refreshData();
	}

	public void refreshData() {
		long today = getTodayTotal();
		long yesterday = getYesterdayTotal();
		String unit = mContext.getString(getUnitId());
		SpannableStringBuilder strBuilder = new SpannableStringBuilder();

		strBuilder.append(String.valueOf(today));
		strBuilder.append(unit);
		strBuilder.setSpan(new RelativeSizeSpan(0.3f), strBuilder.length() - unit.length(), strBuilder.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		todayTotalText.setText(strBuilder);

		strBuilder.clear();
		strBuilder.append(mContext.getString(R.string.yesterday_total));
		String data = String.valueOf(yesterday);
		strBuilder.append(data);
		strBuilder.append(unit);
		strBuilder.setSpan(new RelativeSizeSpan(1.2f), strBuilder.length() - data.length() - unit.length(),
				strBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		strBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), strBuilder.length() - data.length() - unit.length(),
				strBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		yesterdayTotalText.setText(strBuilder);

		strBuilder.clear();
		strBuilder.append(mContext.getString(R.string.today_add));
		data = String.valueOf(today - yesterday);
		strBuilder.append(data);
		strBuilder.append(unit);
		strBuilder.setSpan(new RelativeSizeSpan(1.2f), strBuilder.length() - data.length() - unit.length(),
				strBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		strBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), strBuilder.length() - data.length() - unit.length(),
				strBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		totdayAddText.setText(strBuilder);

		mAdapter.notifyDataSetChanged();

		progressBack.reset();
	}

	abstract protected long getTodayTotal();

	abstract protected long getYesterdayTotal();

	abstract protected int getUnitId();

	abstract public int getTitleId();
}
