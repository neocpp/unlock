package com.qihoo.unlock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.Window;

@SuppressLint("NewApi")
abstract public class CustomActivity extends Activity {

	public void setContentView(View view, int custom_title_layout_id) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(view);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				custom_title_layout_id);

	}

	public void setContentView(int layoutResID, int custom_title_layout_id) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(layoutResID);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				custom_title_layout_id);

	}

}
