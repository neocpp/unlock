package com.qihoo.unlock.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private final static String TAG = "DaatBaseHelper";
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createdTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			while (oldVersion++ < newVersion) {
				alterTables(db, oldVersion);
			}
		} else if (newVersion < oldVersion) {
			onDowngrade(db, oldVersion, newVersion);
		}
	}

	/**
	 * 增量升级数据库
	 * 
	 * @param db
	 * @param currentVersion
	 *            增量对应的数据库版本号
	 */
	private void alterTables(SQLiteDatabase db, int currentVersion) {
		Log.d(TAG, "onUpgrade: " + currentVersion);
		switch (currentVersion) {
		default:
			break;
		}
	}

	public void createdTables(SQLiteDatabase db) {
		Log.d(TAG, "createdTables");
		String unlockinfos = "CREATE TABLE IF NOT EXISTS unlockinfos(autoId integer PRIMARY KEY autoincrement default NULL, startTime long, endTime long, totalTime long, unlockTime long, totalCount long);";

		db.execSQL(unlockinfos);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onDowngrade");
		// TODO Auto-generated method stub
		String sql = "select tbl_name from sqlite_master WHERE type='table'";
		Cursor cursor = db.rawQuery(sql, null);
		ArrayList<String> tables = new ArrayList<String>();
		while (cursor.moveToNext()) {
			tables.add(cursor.getString(0));
		}
		cursor.close();

		tables.remove("sqlite_sequence");
		tables.remove("android_metadata");

		for (String tableName : tables) {
			String drop = "DROP TABLE " + tableName + ";";
			executeSQL(db, drop);
		}
		createdTables(db);
	}

	private void executeSQL(SQLiteDatabase db, String sql) {
		db.execSQL(sql);
	}
}
