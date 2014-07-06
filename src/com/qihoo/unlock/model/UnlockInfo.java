package com.qihoo.unlock.model;

import android.database.Cursor;

public class UnlockInfo {
	public int autoId;
	public long startTime;
	public long endTime;
	public long totalTime;
	public long totalCount;
	public long unlockTime;

	public String toString() {
		return "UnlockInfo [ startTime=" + startTime + ",endTime=" + endTime
				+ ",totalTime=" + totalTime + ",totalCount=" + totalCount
				+ ",unlockTime=" + unlockTime + " ]";
	}
	
	public UnlockInfo(){
		
	}

	public UnlockInfo(Cursor cursor) {
		if(cursor!=null){
			autoId = cursor.getInt(cursor.getColumnIndex(ProviderData.AUTO_ID));
			startTime = cursor.getInt(cursor.getColumnIndex(ProviderData.START_TIME));
			endTime = cursor.getInt(cursor.getColumnIndex(ProviderData.END_TIME));
			totalTime = cursor.getInt(cursor.getColumnIndex(ProviderData.TOTAL_TIME));
			totalCount = cursor.getInt(cursor.getColumnIndex(ProviderData.TOTAL_COUNT));
			unlockTime = cursor.getInt(cursor.getColumnIndex(ProviderData.UNLOCK_TIME));
		}
	}
}
