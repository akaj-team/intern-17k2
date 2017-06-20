package vn.asiantech.internship.day11.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import vn.asiantech.internship.day11.model.Note;

/**
 * Created by at-hoavo on 20/06/2017.
 */
public class NoteModify {
    private DatabaseHelper mDBHelper;

    public NoteModify(Context context) {
        mDBHelper = new DatabaseHelper(context);
    }

    public void insert(Note note) {
        // Mo ket noi DataBase
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_TITLE, note.getTitle());
        values.put(DatabaseHelper.KEY_DESCRIPTION, note.getDescription());
        values.put(DatabaseHelper.KEY_IMAGE, note.getImageNote());
        values.put(DatabaseHelper.KEY_TIME, note.getTime());
        db.insert(DatabaseHelper.DATABASE_TABLE, null, values);
        //Dong ket noi
        db.close();
    }

    public Cursor getNoteList() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String sql = "select * from " + DatabaseHelper.DATABASE_TABLE;
//        Cursor cursor = db.query(DatabaseHelper.DATABASE_TABLE,new String[]{DatabaseHelper.KEY_ID,DatabaseHelper.KEY_TITLE,DatabaseHelper.KEY_DESCRIPTION,DatabaseHelper.KEY_IMAGE,DatabaseHelper.KEY_TIME}, null,null,null,null,null);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}
