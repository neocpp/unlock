package com.qihoo.unlock.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihoo.unlock.R;
import com.qihoo.unlock.model.UnlockInfo;

abstract public class DetailListAdapter extends BaseExpandableListAdapter {
	protected GroupInfo[] mGroup;
	protected ArrayList<ArrayList<UnlockInfo>> mDatas;
	protected Context mContext;

	class GroupInfo {
		int textId;
		int imageId;
	}

	public DetailListAdapter(Context context) {
		mGroup = new GroupInfo[3];

		GroupInfo info = new GroupInfo();
		info.textId = R.string.earlyday;
		info.imageId = R.drawable.earlyday;
		mGroup[2] = info;

		info = new GroupInfo();
		info.textId = R.string.day;
		info.imageId = R.drawable.day;
		mGroup[1] = info;

		info = new GroupInfo();
		info.textId = R.string.night;
		info.imageId = R.drawable.night;
		mGroup[0] = info;

		mContext = context;
		refreshData();
	}

	abstract public void refreshData();

	ArrayList<UnlockInfo> getChild(int groupPosition) {
		return groupPosition < mDatas.size() ? mDatas.get(groupPosition) : null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mDatas.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	abstract public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent);

	@Override
	public int getChildrenCount(int groupPosition) {
		List<UnlockInfo> child = (List<UnlockInfo>) getChild(groupPosition);
		return child == null ? 0 : child.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mGroup[groupPosition - getGroupCount() + 3];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
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
