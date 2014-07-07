package com.qihoo.unlock;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.qihoo.unlock.model.UnlockInfo;
import com.qihoo.unlock.model.UnlockInfoManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshData();
	}

	private void refreshData() {
		TextView textView = (TextView) findViewById(R.id.unlock_counts);
		UnlockInfo info = UnlockInfoManager.getInstance().getLastInfo();
		String count = (info == null ? "0" : String.valueOf(info.totalCount)) + getString(R.string.count);
		SpannableStringBuilder style = new SpannableStringBuilder(count);
		style.setSpan(new RelativeSizeSpan(0.3f), count.length() - 1, count.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(style);

	}
}
