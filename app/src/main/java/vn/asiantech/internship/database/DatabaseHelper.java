package vn.asiantech.internship.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Feed;
import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 * Database helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SEPARATOR = ",";
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String LIST_IMAGE = "list_image.sqlite";
    private static final String DATABASE_NAME = "INTERNSHIP_DATABASE";

    // Table Names
    private static final String TABLE_NOTES = "NOTES";
    private static final String TABLE_FEEDS = "FEEDS";
    private static final String TABLE_IMAGES = "images";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String TEXT = " TEXT,";
    private static final String SELECT_FROM = "SELECT * FROM ";

    // NOTES Table - column
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "decription";
    private static final String KEY_DATE = "date";
    private static final String KEY_IMAGE_URI = "image_uri";
    // FEEDS Table - column
    private static final String KEY_ID_IMG = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LIST_IMAGES = "link";

    // TODO NOTES table create statement
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE "
            + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE
            + TEXT + KEY_DESCRIPTION + TEXT + KEY_DATE
            + " DATETIME" + KEY_IMAGE_URI + " TEXT" + ")";

    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;

    public DatabaseHelper(Context context) {
        super(context, LIST_IMAGE, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mSqLiteDatabase = db;
        try {
            createDataBase();
        } catch (IOException e) {
            Log.d(LOG, "onCreate: " + e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    /**
     * This method will create database in application package /databases
     * directory when first time application launched
     **/
    private void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                Log.d(LOG, "onCreate: " + mIOException.toString());
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }
    }

    /**
     * This method checks whether database is exists or not
     **/
    private boolean checkDataBase() {
        try {
            final String mPath = mContext.getFilesDir().getPath() + LIST_IMAGE;
            final File file = new File(mPath);
            return file.exists();
        } catch (SQLiteException e) {
            Log.d(LOG, "checkDataBase: " + e.toString());
            return false;
        }
    }

    /**
     * This method will copy database from /assets directory to application
     * package /databases directory
     **/
    private void copyDataBase() throws IOException {
        try {
            InputStream mInputStream = mContext.getAssets().open(LIST_IMAGE);
            String outFileName = mContext.getFilesDir().getPath() + LIST_IMAGE;
            OutputStream mOutputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
        } catch (Exception e) {
            Log.d(LOG, "copyDataBase: " + e.toString());
        }
    }

    private void openDataBaseFeed() throws SQLException {
        String mPath = mContext.getFilesDir().getPath() + LIST_IMAGE;
        mSqLiteDatabase = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        mSqLiteDatabase.isOpen();
    }

    /**
     * This method close database connection and released occupied memory
     **/
    @Override
    public synchronized void close() {
        if (mSqLiteDatabase != null) {
            mSqLiteDatabase.close();
        }
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    /**
     * @param array want to cover
     * @return string after cover
     */
    private String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + SEPARATOR;
            }
        }
        return str;
    }

    private String[] convertStringToArray(String str) {
        return str.split(SEPARATOR);
    }

    /**
     * get all Notes
     *
     * @return List<Note> Notes
     */
    public List<Note> getAllNotes() {

        List<Note> notes = new ArrayList<>();
        String selectQuery = SELECT_FROM + TABLE_NOTES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                note.setNoteTile(c.getString((c.getColumnIndex(KEY_TITLE))));
                note.setNoteDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                note.setNoteDate(c.getString(c.getColumnIndex(KEY_DATE)));
                note.setNoteImagesThumb(c.getString(c.getColumnIndex(KEY_IMAGE_URI)));

                // adding to Note list
                notes.add(note);
            } while (c.moveToNext());
        }
        c.close();
        return notes;
    }

    /**
     * get Note
     *
     * @param note_id is a ID of Note want to get
     * @return note from id
     */
    public Note getNote(long note_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = SELECT_FROM + TABLE_NOTES + " WHERE "
                + KEY_ID + " = " + note_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }
        Note note = new Note();
        note.setId(c.getInt((c.getColumnIndex(KEY_ID))));
        note.setNoteTile(c.getString((c.getColumnIndex(KEY_TITLE))));
        note.setNoteDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        note.setNoteDate(c.getString(c.getColumnIndex(KEY_DATE)));
        note.setNoteImagesThumb(c.getString(c.getColumnIndex(KEY_IMAGE_URI)));

        c.close();
        return note;
    }

    /**
     * update note
     *
     * @param note is a note wanna update
     * @return 1 = true, 0 = false;
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getNoteTile());
        values.put(KEY_DESCRIPTION, note.getNoteDescription());
        values.put(KEY_DATE, note.getNoteDate());
        values.put(KEY_IMAGE_URI, note.getNoteImagesThumb());

        // updating row
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    /**
     * delete Note
     *
     * @param note_id is a id wanna delete
     */
    public void deleteNote(long note_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?",
                new String[]{String.valueOf(note_id)});
    }

    /**
     * Create new note to database
     *
     * @param note is a note wanna add to database
     */
    public void createNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getNoteTile());
        values.put(KEY_DESCRIPTION, note.getNoteDescription());
        values.put(KEY_DATE, note.getNoteDate());
        values.put(KEY_IMAGE_URI, note.getNoteImagesThumb());
        db.insert(TABLE_NOTES, null, values);
    }

    /**
     * get all FEEDS
     *
     * @return List<Feed> FEEDS
     */
    public List<Feed> getAllFeeds() {

        List<Feed> feeds = new ArrayList<>();
        String selectQuery = SELECT_FROM + TABLE_IMAGES;

        //SQLiteDatabase db = this.getReadableDatabase();
        openDataBaseFeed();
        Cursor c = mSqLiteDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Feed feed = new Feed();
                feed.setId(c.getInt((c.getColumnIndex(KEY_ID_IMG))));
                feed.setName(c.getString((c.getColumnIndex(KEY_TITLE))));
                feed.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                String[] s = convertStringToArray(c.getString(c.getColumnIndex(KEY_LIST_IMAGES)));
                feed.setIdImgThumb(s);

                // adding to Feed list
                feeds.add(feed);
            } while (c.moveToNext());
        }
        c.close();
        return feeds;
    }

    /**
     * get Feed
     *
     * @param feed_id is a ID of Feed want to get
     * @return Feed from id
     */
    public Feed getFeed(long feed_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = SELECT_FROM + TABLE_FEEDS + " WHERE "
                + KEY_ID + " = " + feed_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        Feed feed = new Feed();
        feed.setName(c.getString((c.getColumnIndex(KEY_NAME))));
        feed.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        String[] s = convertStringToArray(c.getString(c.getColumnIndex(KEY_LIST_IMAGES)));
        feed.setIdImgThumb(s);
        c.close();
        return feed;
    }

    /**
     * delete Feed
     *
     * @param feed_id is a id wanna delete
     */
    public void deleteFeed(long feed_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FEEDS, KEY_ID + " = ?",
                new String[]{String.valueOf(feed_id)});
    }

    /**
     * Create new Feed to database
     *
     * @param feed is a Feed wanna add to database
     */
    public void createFeed(Feed feed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, feed.getName());
        values.put(KEY_DESCRIPTION, feed.getDescription());
        String s = convertArrayToString(feed.getIdImgThumb());
        values.put(KEY_LIST_IMAGES, s);
        db.insert(TABLE_FEEDS, null, values);
    }
}
