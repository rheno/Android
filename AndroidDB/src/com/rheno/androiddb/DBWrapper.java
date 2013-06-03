package com.rheno.androiddb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBWrapper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "model";
	public static final String DB_ID = "id";
	public static final String DB_STRING = "content";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Database.db";

	private String DATABASE_CREATE = "create table " + TABLE_NAME + " ("
			+ DB_ID + " integer primary key autoincrement, " + DB_STRING
			+ " text not null)";

	public DBWrapper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}// end of method

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
			db.execSQL(DATABASE_CREATE);
		}// end of try
		catch (Exception e) {
			throw new RuntimeException(e.toString());
		}// end of catch

	}// end of method

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion != newVersion) {
			db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
			onCreate(db);
		}// end if
	}// end of method

	public long insertData(String noteString) {
		ContentValues values = new ContentValues();
		values.put(DB_STRING, noteString);

		long result = this.getWritableDatabase().insert(TABLE_NAME, null,
				values);

		close();

		return result;
	}// end of method

	public ArrayList<DBModel> queryAllData() {

		Cursor c = this.getReadableDatabase().query(TABLE_NAME, // The table to
																// query
				new String[] { DB_ID, DB_STRING }, // The
													// columns
													// to
													// return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		ArrayList<DBModel> result = null;

		if (c != null) {
			result = new ArrayList<DBModel>();

			if (c.getCount() > 0) {
				c.moveToFirst();

				do {
					DBModel dbModel = new DBModel();
					dbModel.setId(c.getLong(c.getColumnIndex(DB_ID)));
					dbModel.setContent(c.getString(c.getColumnIndex(DB_STRING)));
					result.add(dbModel);
				} while (c.moveToNext());
			}
			c.close();
		}

		close();

		return result;
	}

	public int updateData(long note_id, String new_string) {

		ContentValues values = new ContentValues();
		values.put(DB_STRING, new_string);

		String selection = DB_ID + " LIKE ?";
		String[] selelectionArgs = { String.valueOf(note_id) };

		int result = this.getReadableDatabase().update(TABLE_NAME, values,
				selection, selelectionArgs);

		close();

		return result;
	}

	public int deleteData(long note_id) {

		String selection = DB_ID + " LIKE ?";

		String[] selelectionArgs = { String.valueOf(note_id) };

		int result = this.getWritableDatabase().delete(TABLE_NAME, selection,
				selelectionArgs);

		close();

		return result;
	}
}
