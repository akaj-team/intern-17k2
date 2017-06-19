package vn.asiantech.internship.note.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by at-dinhvo on 19/06/2017.
 */

public class NoteDatabase {

    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    public NoteDatabase(Context context) {
        mContext = context;
    }

    public NoteDatabase open(){
        mSqLiteDatabase = DatabaseHelper.getInstance(mContext);
        return this;
    }
}
