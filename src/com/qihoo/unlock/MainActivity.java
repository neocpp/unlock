package com.qihoo.unlock;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

import com.qihoo.unlock.model.UnlockInfoManager;
import com.qihoo.unlock.view.DetailListAdapter;

public class MainActivity extends CustomActivity {
	private DetailListAdapter mAdapter;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity, R.layout.main_titlebar_layout);
		ExpandableListView listview = (ExpandableListView) findViewById(R.id.unlock_detail_list);
		listview.setGroupIndicator(null);
		mAdapter = new DetailListAdapter(this);
		listview.setAdapter(mAdapter);
		listview.setDivider(null);
		listview.setChildDivider(null);

		SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.sliding_detail);
		final ImageView arrow = (ImageView) findViewById(R.id.sliding_handle_arrow);
		final TextView handleText = (TextView) findViewById(R.id.sliding_handle_text);
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

	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshData();
	}

	private void refreshData() {
		TextView textView = (TextView) findViewById(R.id.unlock_counts);
		long countLong = UnlockInfoManager.getInstance().getTodayCount();
		String count = String.valueOf(countLong) + getString(R.string.count);
		SpannableStringBuilder style = new SpannableStringBuilder(count);
		style.setSpan(new RelativeSizeSpan(0.3f), count.length() - 1,
				count.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(style);
		mAdapter.notifyDataSetChanged();

	}

}
