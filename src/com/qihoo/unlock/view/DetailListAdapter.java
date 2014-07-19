package com.qihoo.unlock.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihoo.unlock.R;
import com.qihoo.unlock.model.UnlockInfo;
import com.qihoo.unlock.model.UnlockInfoManager;
import com.qihoo.unlock.utils.TimeUtil;

public class DetailListAdapter extends BaseExpandableListAdapter {
	private GroupInfo[] mGroup;
	private ArrayList<ArrayList<UnlockInfo>> mChilds;
	private Context mContext;

	class GroupInfo {
		int textId;
		int imageId;
	}

	public DetailListAdapter(Context context) {
		mGroup = new GroupInfo[2];

		GroupInfo info = new GroupInfo();
		info.textId = R.string.day;
		info.imageId = R.drawable.day;
		mGroup[0] = info;

		info = new GroupInfo();
		info.textId = R.string.night;
		info.imageId = R.drawable.night;
		mGroup[1] = info;

		mContext = context;
	}

	Object getChild(int groupPosition) {
		return UnlockInfoManager.getInstance().getTodayUnlockInfos()
				.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return UnlockInfoManager.getInstance().getTodayUnlockInfos()
				.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
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

	@Override
	public int getChildrenCount(int groupPosition) {
		List<UnlockInfo> child = (List<UnlockInfo>) getChild(groupPosition);
		return child == null ? 0 : child.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mGroup[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ParentViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.detaillist_group_layout, null);
			holder = new ParentViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.group_imageview);
			holder.title = (TextView) convertView
					.findViewById(R.id.group_title);
			convertView.setTag(holder);
		} else {
			holder = (ParentViewHolder) convertView.getTag();
		}

		GroupInfo group = (GroupInfo) getGroup(groupPosition);
		holder.image.setBackgroundResource(group.imageId);
		holder.title.setText(group.textId);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	static class ParentViewHolder {
		ImageView image;
		TextView title;
	}

	static class ChildViewHolder {
		ImageView image;
		TextView time;
		TextView info;
	}

}
