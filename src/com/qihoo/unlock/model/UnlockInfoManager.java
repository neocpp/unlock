package com.qihoo.unlock.model;

import java.util.List;

import com.qihoo.unlock.database.SQLiteHelper;

public class UnlockInfoManager {
	private List<UnlockInfo> unlockInfos;
	private static volatile UnlockInfoManager instance;

	private UnlockInfoManager() {
		unlockInfos = SQLiteHelper.getInstance().getUnlockInfos();
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
			return unlockInfos.get(unlockInfos.size() - 1);
		}
		return null;
	}

}
