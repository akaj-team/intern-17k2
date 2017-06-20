package vn.asiantech.internship.notesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Used to create table to contain data
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
class OpenHelper extends SQLiteOpenHelper {
    static final String TEXT_NOT_NULL = " TEXT NOT NULL, ";

    OpenHelper(Context context) {
        super(context, NoteSqlite.DB_NAME, null, NoteSqlite.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("CREATE TABLE " + NoteSqlite.TABLE_NOTE + " ("
                + NoteSqlite.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NoteSqlite.COLUMN_DATE + TEXT_NOT_NULL
                + NoteSqlite.COLUMN_DAY + TEXT_NOT_NULL
                + NoteSqlite.COLUMN_MONTH + TEXT_NOT_NULL
                + NoteSqlite.COLUMN_HOUR + TEXT_NOT_NULL
                + NoteSqlite.COLUMN_TITLE + TEXT_NOT_NULL
                + NoteSqlite.COLUMN_CONTENT + TEXT_NOT_NULL
                + NoteSqlite.COLUMN_PATH_IMAGE + " TEXT NOT NULL); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + NoteSqlite.TABLE_NOTE);
        onCreate(arg0);
    }
}
