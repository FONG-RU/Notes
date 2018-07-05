package com.example.administrator.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class NoteAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_CONTEXT = "context";
    private static final String TABLE_NAME = "note";
    private NoteHelper mNoteHelper;
    private SQLiteDatabase mNote;
    private final Context mCtx;
    private ContentValues values;

    //-----------------------------constructor---------------------------------
    public NoteAdapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }

    //-----------------------------OPEN()------------------------------------
    public void open(){
        mNoteHelper = new NoteHelper(mCtx);
        mNote = mNoteHelper.getWritableDatabase();
        Log.i("DB=",mNote.toString());
    }

    //-----------------------------CLOSE()------------------------------------
    public void close(){
        if (mNoteHelper != null) {
            mNoteHelper.close();
        }
    }

    //-----------------------------Cursor()------------------------------------
    public Cursor listNote(){
        Cursor mCursor = mNote.query(TABLE_NAME, new String[] {KEY_ID, KEY_DATE, KEY_CONTEXT},
                null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //-----------------------------新增便條------------------------------------
    public long createNote(String date, String context) {
        try{
            values = new ContentValues();
            values.put(KEY_DATE, date);
            values.put(KEY_CONTEXT, context);
        }catch(Exception e){

        }finally {
            Toast.makeText(mCtx,"新增成功!", Toast.LENGTH_SHORT).show();
        }
        return mNote.insert(TABLE_NAME,null,values);
    }

    //-----------------------------更新便條------------------------------------
    public long updateNote(int id, String date, String context){
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, date);
        values.put(KEY_CONTEXT, context);
        Toast.makeText(mCtx,"更新成功!", Toast.LENGTH_SHORT).show();
        return mNote.update(TABLE_NAME, values, "_id=" + id,null);
    }

    //-----------------------------刪除便條------------------------------------
    public boolean deleteNote(int id){
        String[] args = {Integer.toString(id)};
        mNote.delete(TABLE_NAME, "_id= ?",args);
        return true;
    }

    //-----------------------------尋找聯絡人------------------------------------
    public Cursor queryById(int item_id){
        Cursor mCursor = mNote.query(true, TABLE_NAME, new String[] {KEY_ID, KEY_DATE, KEY_CONTEXT},
                KEY_ID + " LIKE '" + item_id + "'", null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
