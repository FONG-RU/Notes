package com.example.administrator.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper extends SQLiteOpenHelper {
    //--------------------宣告變數------------------------
    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CONTEXT = "context";
    private static final String DATABASE_NAME = "Notes";
    private static final String TABLE_NAME = "note";
    private static final int DATABASE_VERSION = 1;

    //-----------------------constructor-----------------------------
    public NoteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + "(" + KEY_ID + " integer PRIMARY KEY autoincrement," +
                KEY_DATE + "," + KEY_CONTEXT  + ");";
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
