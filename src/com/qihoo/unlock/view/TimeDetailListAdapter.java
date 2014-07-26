package com.qihoo.unlock.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihoo.unlock.R;
import com.qihoo.unlock.model.UnlockInfo;
import com.qihoo.unlock.model.UnlockInfoManager;
import com.qihoo.unlock.utils.TimeUtil;

public class TimeDetailListAdapter extends DetailListAdapter {

	public TimeDetailListAdapter(Context context) {
		super(context);
	}

	@Override
	public void refreshData() {
		mDatas = UnlockInfoManager.getInstance().getTodayTimeInfos();

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.detaillist_child_layout, null);
			holder = new ChildViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.child_imageview);
			holder.time = (TextView) convertView.findViewById(R.id.child_time);
			holder.info = (TextView) convertView.findViewById(R.id.child_info);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}

		UnlockInfo child = (UnlockInfo) getChild(groupPosition, childPosition);
		if (child != null) {
			String text = TimeUtil.getTimeString(child.startTime);
			SpannableStringBuilder style = new SpannableStringBuilder(text);
			style.setSpan(new RelativeSizeSpan(0.8f), text.length() - 2,
					text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.time.setText(style);
			StringBuilder strBuilder = new StringBuilder(
					mContext.getString(R.string.detail_list_info_time));
			int idx_start = strBuilder.indexOf("n");

			int sum = getChildrenCount(groupPosition) - childPosition;
			for (int i = groupPosition + 1; i < getGroupCount(); i++) {
				sum += getChildrenCount(i);
			}

			strBuilder.replace(idx_start, idx_start + 1, String.valueOf(sum));
			idx_start = strBuilder.indexOf("?");
			strBuilder.replace(idx_start, idx_start + 1,
					String.valueOf(TimeUtil.toMinute(child.itemTotalTime)));
			holder.info.setText(strBuilder.toString());

		}

		return convertView;
	}
}
