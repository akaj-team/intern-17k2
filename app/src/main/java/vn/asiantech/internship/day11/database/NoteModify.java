package vn.asiantech.internship.day11.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.asiantech.internship.day11.model.Note;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 20/06/2017.
 */
public class NoteModify {
    private DatabaseHelper mDBHelper;

    public NoteModify(Context context) {
        mDBHelper = new DatabaseHelper(context);
    }

    public void insert(Note note) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, note.getTitle());
        values.put(DatabaseHelper.KEY_DESCRIPTION, note.getDescription());
        values.put(DatabaseHelper.KEY_IMAGE, note.getImageNote());
        values.put(DatabaseHelper.KEY_DAY, note.getDay());
        values.put(DatabaseHelper.KEY_DATE, note.getDate());
        values.put(DatabaseHelper.KEY_TIME, note.getTime());
        db.insert(DatabaseHelper.DATABASE_TABLE, null, values);
        db.close();
    }

    public Cursor getNoteList() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from " + DatabaseHelper.DATABASE_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void deleteNote(int id) {
        String sql = "delete from " + DatabaseHelper.DATABASE_TABLE + " where " + DatabaseHelper.KEY_ID + "=" + id;
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        db.execSQL(sql);
    }

    public void updateNote(Note note) {
        String sql = "update " + DatabaseHelper.DATABASE_TABLE + " set " + DatabaseHelper.KEY_TITLE + "= '" + note.getTitle() + "'," + DatabaseHelper.KEY_DESCRIPTION + "= '" + note.getDescription() + "' where " + DatabaseHelper.KEY_ID + "= " + note.getId();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        db.execSQL(sql);
    }
}
