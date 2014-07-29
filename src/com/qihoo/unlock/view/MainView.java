package com.qihoo.unlock.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qihoo.unlock.R;
import com.qihoo.unlock.utils.IncrementAnimationUtil;

abstract public class MainView extends LinearLayout {

	private TextView todayTotalText;
	private TextView yesterdayTotalText;
	private TextView totdayAddText;
	private TextView compareScoreText;
	private Context mContext;

	ProgressCircle progressBack;

	@SuppressLint("NewApi")
	public MainView(Context context) {
		super(context);
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.main_view, this);

		todayTotalText = (TextView) findViewById(R.id.unlock_counts);
		BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.circle_under);
		Bitmap bmp = bmpDraw.getBitmap();
		progressBack = new ProgressCircle(bmp);
		todayTotalText.setBackground(progressBack);
		yesterdayTotalText = (TextView) findViewById(R.id.yesterday_total);
		totdayAddText = (TextView) findViewById(R.id.today_add);

		compareScoreText = (TextView) findViewById(R.id.compare_score);

		TextView share = (TextView) findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
				intent.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_content));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(Intent.createChooser(intent, mContext.getString(R.string.share_choose)));

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
		Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "my_number.TTF");
		strBuilder.setSpan(new MyTypefaceSpan(typeFace), 0, strBuilder.length() - unit.length(),
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

		String score = mContext.getString(R.string.compare_percentage);
		strBuilder.clear();
		String score_percentage = String.valueOf(getScore());
		strBuilder.append(score.replace("?", score_percentage));
		strBuilder.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), strBuilder.length() - score_percentage.length() - 3,
				strBuilder.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		compareScoreText.setText(strBuilder);

		progressBack.reset();
	}

	public void registerAnimate() {
		IncrementAnimationUtil.getInstance().addObserver(progressBack);
	}

	public void unRegisterAnimate() {
		IncrementAnimationUtil.getInstance().deleteObserver(progressBack);
		progressBack.reset();
	}

	abstract protected long getTodayTotal();

	abstract protected long getYesterdayTotal();

	abstract protected int getUnitId();

	abstract public int getTitleId();

	abstract DetailListAdapter createAdapter(Context context);

	public abstract int getScore();

}
