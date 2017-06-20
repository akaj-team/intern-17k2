package vn.asiantech.internship.models;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public List<NoteItem> getNoteList() {
        List<NoteItem> listNote = new ArrayList<>();
        String[] columns = {NoteOpenHelper.COLUMN_TITLE, NoteOpenHelper.COLUMN_CONTENT, NoteOpenHelper.COLUMN_IMAGE, NoteOpenHelper.COLUMN_TIME};
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + NoteOpenHelper.TABLE_NOTES, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listNote.add(new NoteItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }
        return listNote;
    }
}
