package vn.asiantech.internship.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.asiantech.internship.databases.NoteDataBase;

/**
 * Created by ducle on 20/06/2017.
 */

public class NoteOpenHelper extends SQLiteOpenHelper {
    public NoteOpenHelper(Context context) {
        super(context, NoteDataBase.DATABASE_NAME, null, NoteDataBase.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NoteDataBase.TABLE_NOTE + " ( " +
                NoteDataBase.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NoteDataBase.COLUMN_TITLE + " TEXT NOT NULL, " +
                NoteDataBase.COLUMN_CONTENT + " TEXT NOT NULL, " +
                NoteDataBase.COLUMN_DATE + " TEXT NOT NULL, " +
                NoteDataBase.COLUMN_URL_IMAGE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+NoteDataBase.TABLE_NOTE);
        onCreate(db);
    }
}
