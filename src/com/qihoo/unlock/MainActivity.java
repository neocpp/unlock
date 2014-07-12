package com.qihoo.unlock;

import com.qihoo.unlock.model.UnlockInfo;
import com.qihoo.unlock.model.UnlockInfoManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends CustomActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity, R.layout.main_titlebar_layout);

		refreshData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshData();
	}
	
	private void refreshData(){
		TextView textView = (TextView) findViewById(R.id.unlock_counts);
		UnlockInfo info = UnlockInfoManager.getInstance().getLastInfo();
		if (info != null) {
			textView.setText(String.valueOf(info.totalCount));
		}
	}
}
