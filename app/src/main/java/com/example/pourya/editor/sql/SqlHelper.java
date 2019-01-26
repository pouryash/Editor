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

public class SqlHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private  static final String DATABASE_NAME = "note_db";


    public SqlHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Note.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(Note.COLUMN_SUBJECT,note.getSubject());
        value.put(Note.COLUMN_CONTENT,note.getContent());
        long id = db.insert(Note.TABLE_NAME,null,value);

        db.close();

        return id;
    }

    public List<Note> SelectAllNotes(){

        List<Note> list = new ArrayList<>();

        String selectQuery = "SELECT * FROm " + Note.TABLE_NAME + " ORDER BY "
                + Note.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){

            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setSubject(cursor.getString(cursor.getColumnIndex(Note.COLUMN_SUBJECT)));
                note.setContent(cursor.getString(cursor.getColumnIndex(Note.COLUMN_CONTENT)));

                list.add(note);
            } while (cursor.moveToNext());

        }
        db.close();

        return list;
    }

    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_SUBJECT, Note.COLUMN_CONTENT},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_SUBJECT)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_CONTENT)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));

        // close the db connection
        cursor.close();

        return note;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_SUBJECT, note.getSubject());
        values.put(Note.COLUMN_CONTENT, note.getContent());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Note.TABLE_NAME);
        db.close();
    }
}
