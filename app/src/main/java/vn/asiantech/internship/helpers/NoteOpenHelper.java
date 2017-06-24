package vn.asiantech.internship.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper for Note
 * Created by huypham on 22/06/2017.
 */
public class NoteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE = "Note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_TIME = "time";


    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_CONTENT + " TEXT, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_TIME + " LONG);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABALE IF EXISTS" + TABLE);
        onCreate(sqLiteDatabase);
    }
}
