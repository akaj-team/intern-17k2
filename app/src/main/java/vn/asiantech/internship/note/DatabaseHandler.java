package vn.asiantech.internship.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by datbu on 20-06-2017.
 */
class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sql_lite";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_contacts_table = "CREATE TABLE " + NoteDatabase.TABLE_CONTACTS + "("
                + NoteDatabase.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NoteDatabase.TITLE + " TEXT,"
                + NoteDatabase.NOTE + " TEXT,"
                + NoteDatabase.TIME + " TEXT,"
                + NoteDatabase.IMAGE + " TEXT" + ")";
        db.execSQL(create_contacts_table);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteDatabase.TABLE_CONTACTS);
        onCreate(db);
    }
}
