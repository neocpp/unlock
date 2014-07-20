package com.qihoo.unlock.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.qihoo.unlock.database.SQLiteHelper;
import com.qihoo.unlock.utils.TimeUtil;

public class UnlockInfoManager {
	private final String TAG = "UnlockInfoManager";
	private List<UnlockInfo> unlockInfos;
	private static volatile UnlockInfoManager instance;

	private UnlockInfoManager() {
		unlockInfos = SQLiteHelper.getInstance().getUnlockInfos();
		clearRedundance();
	}

	public static UnlockInfoManager getInstance() {
		if (instance == null) {
			synchronized (UnlockInfoManager.class) {
				if (instance == null) {
					instance = new UnlockInfoManager();
				}
			}
		}
		return instance;
	}

	public void addUnlockInfo(UnlockInfo info) {
		unlockInfos.add(info);
		SQLiteHelper.getInstance().saveUnlockInfo(info);

	}

	public UnlockInfo getLastInfo() {
		if (unlockInfos.size() > 0) {
			Log.e(TAG, "infos size: " + unlockInfos.size());
			return unlockInfos.get(unlockInfos.size() - 1);
		}
		return null;
	}

	public ArrayList<ArrayList<UnlockInfo>> getTodayUnlockInfos() {
		ArrayList<ArrayList<UnlockInfo>> result = new ArrayList<ArrayList<UnlockInfo>>();
		ArrayList<UnlockInfo> earlydayinfos = new ArrayList<UnlockInfo>();
		ArrayList<UnlockInfo> dayinfos = new ArrayList<UnlockInfo>();
		ArrayList<UnlockInfo> nightinfos = new ArrayList<UnlockInfo>();
		long cur = System.currentTimeMillis();
		long nightTimeApart = TimeUtil.getNightTimeOfTheDate(cur);
		long dayTimeApart = TimeUtil.getDayTimeOfTheDate(cur);
		long dayTimeStart = TimeUtil.getStartTimeOfTheDate(cur);
		for (UnlockInfo info : unlockInfos) {
			if (info.unlockTime > dayTimeStart) {
				if (info.unlockTime < dayTimeApart) {
					earlydayinfos.add(info);
				} else if (info.unlockTime < nightTimeApart) {
					dayinfos.add(info);
				} else {
					nightinfos.add(info);
				}
			}
		}
		result.add(earlydayinfos);
		result.add(dayinfos);
		result.add(nightinfos);
		return result;
	}

	private void clearRedundance() {
		long yesterdayStartTime = TimeUtil.getYesterdayStartTimeOfTheDate(System.currentTimeMillis());

		List<UnlockInfo> redundance = new ArrayList<UnlockInfo>();
		for (UnlockInfo info : unlockInfos) {
			if (info.endTime < yesterdayStartTime) {
				redundance.add(info);
				Log.e(TAG, "redundance: " + info.toString());
			}
		}

		unlockInfos.removeAll(redundance);

		SQLiteHelper.getInstance().deleteInfos(redundance);
	}

	public long getTodayCount() {
		if (unlockInfos.size() > 0) {
			return unlockInfos.get(unlockInfos.size() - 1).totalCount;
		} else {
			return 0;
		}
	}

	public long getYesterdayCount() {
		long time = System.currentTimeMillis() - TimeUtil.getOneDayTime();
		long count = 0;
		synchronized (unlockInfos) {
			for (UnlockInfo info : unlockInfos) {
				if (info.unlockTime > 0 && info.unlockTime < time) {
					count = info.totalCount;
				} else if (info.unlockTime > time) {
					break;
				}
			}
		}
		return count;
	}

	public long getTodayTime() {
		if (unlockInfos.size() > 0) {
			return unlockInfos.get(unlockInfos.size() - 1).totalTime;
		} else {
			return 0;
		}
	}

	public long getYesterdayTime() {
		long time = System.currentTimeMillis() - TimeUtil.getOneDayTime();
		long result = 0;
		synchronized (unlockInfos) {
			for (UnlockInfo info : unlockInfos) {
				if (info.endTime > 0 && info.endTime < time) {
					result = info.totalTime;
				} else if (info.endTime > time) {
					if (info.startTime < time) {
						result += (time - info.startTime);
					}
					break;
				}
			}
		}
		return result;
	}

}
