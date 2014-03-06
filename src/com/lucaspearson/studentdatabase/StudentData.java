package com.lucaspearson.studentdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentData {
	StudentDataHelper helper;

	public StudentData(Context c) {
		helper = new StudentDataHelper(c);
	}

	public long insertData(String fname, String lname, String email) {
		ContentValues cv = new ContentValues();
		cv.put(StudentDataHelper.KEY_FIRST_NAME, fname);
		cv.put(StudentDataHelper.KEY_LAST_NAME, lname);
		cv.put(StudentDataHelper.KEY_EMAIL, email);
		long id = -1;
		try {
			SQLiteDatabase db = helper.getWritableDatabase();
			id = db.insert(StudentDataHelper.DATABASE_TABLE, null, cv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("SQL ERROR", e.getMessage().toString());
		}
		return id;
	}
	public Cursor returnAllCursor(){
		SQLiteDatabase db  = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * from Student", null);
		return c;
		
	}
	
	public boolean deleteStudentWithID(int id){
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete(StudentDataHelper.DATABASE_TABLE, StudentDataHelper.KEY_ROWID+"="+id, null);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	

	private class StudentDataHelper extends SQLiteOpenHelper {
		private static final String KEY_ROWID = "_id";
		private static final String KEY_FIRST_NAME = "first_name";
		private static final String KEY_LAST_NAME = "last_name";
		private static final String KEY_EMAIL = "email";

		private static final String DATABASE_NAME = "studentdata";
		private static final String DATABASE_TABLE = "Student";
		private static final int DATABASE_VERSION = 1;


		public StudentDataHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("StudentDataHelper", "Made it pass the super call");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// This is used to create the database this should be one of VERY
			// few
			// execSQL methods called
			try {
				Log.d("ON CREATE", "Got to onCreate in Helper class");
				db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
						+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ KEY_FIRST_NAME + " TEXT, " + KEY_LAST_NAME
						+ " TEXT, " + KEY_EMAIL + " TEXT);");

			} catch (SQLException sqlE) {
				sqlE.printStackTrace();
				Log.e("SQL ERROR", sqlE.getMessage().toString());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL("DROP " + DATABASE_TABLE + ";");
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
