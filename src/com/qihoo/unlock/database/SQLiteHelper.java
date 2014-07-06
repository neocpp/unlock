package com.qihoo.unlock.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;

import com.qihoo.unlock.UnlockApplication;
import com.qihoo.unlock.model.ProviderData;
import com.qihoo.unlock.model.UnlockInfo;

public class SQLiteHelper {
	SQLiteDatabase readDb = null;
	SQLiteDatabase writeDb = null;
	private Handler mHandler = null;
	private HandlerThread mHandlerThread = null;
	private static SQLiteHelper helper = null;
	private static DatabaseHelper mDbHelper = null;

	public static SQLiteHelper getInstance() {
		if (helper == null) {
			helper = new SQLiteHelper(UnlockApplication.getContext());
		}
		return helper;
	}

	private SQLiteHelper(Context ctx) {
		//
		if (ctx == null) {
			ctx = UnlockApplication.getContext();
		}
		if (mDbHelper == null) {
			synchronized (DatabaseHelper.class) {
				try {
					mDbHelper = new DatabaseHelper(ctx,
							ProviderData.DATABASE_NAME, null,
							ProviderData.DATABASE_VERSION);
				} catch (android.database.sqlite.SQLiteException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (readDb == null) {
			readDb = mDbHelper.getReadableDatabase();
		}
		if (writeDb == null)
			writeDb = mDbHelper.getWritableDatabase();
		mHandlerThread = new HandlerThread("dbThreadHandler");
		mHandlerThread.start();
		mHandler = new Handler(mHandlerThread.getLooper());
	}

	public void saveUnlockInfo(final UnlockInfo info) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (info == null) {
					return;
				}
				ContentValues values = new ContentValues();
				values.put(ProviderData.START_TIME, info.startTime);
				values.put(ProviderData.END_TIME, info.endTime);
				values.put(ProviderData.TOTAL_TIME, info.totalTime);
				values.put(ProviderData.UNLOCK_TIME, info.unlockTime);
				values.put(ProviderData.TOTAL_COUNT, info.totalCount);
				synchronized (writeDb) {
					writeDb.insert(ProviderData.UNLOCKINFOS_TABLE_NAME, null,
							values);
				}
			}
		});
	}

	public ArrayList<UnlockInfo> getUnlockInfos() {
		String sql = "SELECT autoId, startTime, endTime, totalTime, unlockTime, totalCount FROM "
				+ ProviderData.UNLOCKINFOS_TABLE_NAME + ";";
		synchronized (readDb) {
			Cursor cursor = readDb.rawQuery(sql, null);
			ArrayList<UnlockInfo> unlockInfos = new ArrayList<UnlockInfo>(
					cursor.getCount());
			while (cursor.moveToNext()) {
				unlockInfos.add(new UnlockInfo(cursor));
			}
			cursor.close();
			return unlockInfos;
		}
	}
}
