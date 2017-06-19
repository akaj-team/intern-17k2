package vn.asiantech.internship.models;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PC on 6/19/2017.
 */

public class NoteDatabase extends Activity {
    private SQLiteDatabase mDb;
    private NoteOpenHelper mOpenHelper;
    private Context mContext;

    public NoteDatabase(Context context) {
        mContext = context;
    }

    public NoteDatabase open() throws SQLException {
        mOpenHelper = new NoteOpenHelper(mContext);
        mDb = mOpenHelper.getWritableDatabase();
        return this;
    }

    public long insertNote(NoteItem note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteOpenHelper.COLUMN_TITLE, note.getTitle());
        contentValues.put(NoteOpenHelper.COLUMN_CONTENT, note.getContent());
        contentValues.put(NoteOpenHelper.COLUMN_IMAGE, note.getImage());
        contentValues.put(NoteOpenHelper.COLUMN_TIME, note.getTime());
        return mDb.insert(NoteOpenHelper.TABLE_NOTES, null, contentValues);
    }
}
