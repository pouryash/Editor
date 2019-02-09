package com.example.pourya.editor.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pourya.editor.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poorya on 12/31/2018.
 */

public class DAO {

    private DBSchema mHelper;
    public Context context;
    public static final String SORT_ORDER_DEFULT = DBSchema.COLUMN_ID + " DESC";

    public DAO(Context context) {
        this.context = context;
        mHelper = new DBSchema(context);
    }

    private SQLiteDatabase getReadDB(){
        return mHelper.getReadableDatabase();
    }
    private SQLiteDatabase getWriteDB(){
        return mHelper.getWritableDatabase();
    }

    public long insertNote(Note note){
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(mHelper.COLUMN_SUBJECT,note.getSubject());
        value.put(mHelper.COLUMN_CONTENT,note.getContent());
        long id = getWriteDB().insert(mHelper.TABLE_NAME,null,value);

        getWriteDB().close();

        return id;
    }

    public List<Note> SelectAllNotes(){

        List<Note> list = new ArrayList<>();

        String selectQuery = "SELECT * FROm " + mHelper.TABLE_NAME + " ORDER BY "
                + SORT_ORDER_DEFULT;

//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getWriteDB().rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){

            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_ID)));
                note.setSubject(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_SUBJECT)));
                note.setContent(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_CONTENT)));

                list.add(note);
            } while (cursor.moveToNext());

        }
        getWriteDB().close();

        return list;
    }

    public Note getNote(long id) {
        // get readable database as we are not inserting anything
//        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = getReadDB().query(mHelper.TABLE_NAME,
                new String[]{mHelper.COLUMN_ID, mHelper.COLUMN_SUBJECT, mHelper.COLUMN_CONTENT},
                mHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_CONTENT)),
                cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_ID)));

        // close the db connection
        cursor.close();

        return note;
    }

    public int updateNote(Note note) {
//        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mHelper.COLUMN_SUBJECT, note.getSubject());
        values.put(mHelper.COLUMN_CONTENT, note.getContent());

        // updating row
        return getWriteDB().update(mHelper.TABLE_NAME, values, mHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
//        SQLiteDatabase db = this.getWritableDatabase();
        getWriteDB().delete(mHelper.TABLE_NAME, mHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        getWriteDB().close();
    }

    public void deleteAll(){
//        SQLiteDatabase db = this.getWritableDatabase();
        getWriteDB().execSQL("delete from "+ mHelper.TABLE_NAME);
        getWriteDB().close();
    }
}
