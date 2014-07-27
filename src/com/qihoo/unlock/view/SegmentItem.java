package com.qihoo.unlock.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

import com.qihoo.unlock.R;

public class SegmentItem extends RadioButton {

	protected void initSegmentItem() {
		super.setButtonDrawable(android.R.color.transparent);
		setBackgroundResource(R.drawable.segment_item_bg_selector);
		int padding = (int) getResources().getDimension(R.dimen.segment_list_padding);
		super.setPadding(padding, getPaddingTop(), padding, getPaddingBottom());

		ColorStateList colorStateList = getResources().getColorStateList(
				R.drawable.segment_item_text_color_selector);
		if (colorStateList != null) {
			super.setTextColor(colorStateList);
		}
		setGravity(Gravity.CENTER);
	}

	public SegmentItem(Context context) {
		this(context, null);
		// this.context = context;
		// TODO Auto-generated constructor stub
		initSegmentItem();
	}

	public SegmentItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initSegmentItem();
	}

	public SegmentItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initSegmentItem();
	}

}
