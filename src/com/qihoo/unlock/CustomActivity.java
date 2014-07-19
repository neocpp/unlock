package com.qihoo.unlock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CustomActivity extends Activity {
	private TextView mTitle;

	public void setContentView(View view, int custom_title_layout_id) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(view);

		init(custom_title_layout_id);

	}

	public void setContentView(int layoutResID, int custom_title_layout_id) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(layoutResID);

		init(custom_title_layout_id);

	}

	private void init(int custom_title_layout_id) {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				custom_title_layout_id);
		mTitle = (TextView) findViewById(R.id.title);
	}

	public void setMainTitle(int id) {
		mTitle.setText(id);
	}

}
