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

public class UnlockDetailListAdapter extends DetailListAdapter {

	public UnlockDetailListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void refreshData() {
		mDatas = UnlockInfoManager.getInstance().getTodayUnlockInfos();
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
			String text = TimeUtil.getTimeString(child.unlockTime);
			SpannableStringBuilder style = new SpannableStringBuilder(text);
			style.setSpan(new RelativeSizeSpan(0.8f), text.length() - 2,
					text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			holder.time.setText(style);
			String info = mContext.getString(R.string.detail_list_info_count);
			holder.info.setText(info.replace("?",
					String.valueOf(child.totalCount)));

		}

		return convertView;
	}

}
