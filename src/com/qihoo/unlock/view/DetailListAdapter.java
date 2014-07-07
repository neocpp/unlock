package com.qihoo.unlock.view;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.qihoo.unlock.R;
import com.qihoo.unlock.model.UnlockInfo;

public class DetailListAdapter extends BaseExpandableListAdapter {
	private GroupInfo[] mGroup;
	private ArrayList<ArrayList<UnlockInfo>> mChilds;

	class GroupInfo {
		int textId;
		int imageId;
	}

	public DetailListAdapter(ArrayList<ArrayList<UnlockInfo>> infos) {
		mGroup = new GroupInfo[2];
		mGroup[0].textId = R.string.day;
		mGroup[0].imageId = R.drawable.day;
		mGroup[1].textId = R.string.night;
		mGroup[1].imageId = R.drawable.night;

		mChilds = infos;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
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
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
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

}
