package vn.asiantech.internship.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Feed;

/**
 * Created by root on 6/21/17.
 */

public class FeedDatabaseHelper extends SQLiteOpenHelper {

    private static final String SEPARATOR = ",";
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DATABASE_NAME.sqlite";

    // Table Names
    private static final String TABLE_IMAGES = "images";

    // Common column names
    private static final String SELECT_FROM = "SELECT * FROM ";

    // NOTES Table - column
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "decription";
    private static final String KEY_LINK = "link";

    private Context mContext;
    private SQLiteDatabase mSqLiteDatabase;

    public FeedDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        } else {
            Toast.makeText(mContext, "true", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method checks whether database is exists or not
     **/
    private boolean checkDataBase() {
        try {
            final String mPath = mContext.getFilesDir().getPath() + DATABASE_NAME;
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
            InputStream mInputStream = mContext.getAssets().open(DATABASE_NAME);
            String outFileName = mContext.getFilesDir().getPath() + DATABASE_NAME;
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
        String mPath = mContext.getFilesDir().getPath() + DATABASE_NAME;
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
     * get all FEEDS
     *
     * @return List<Feed> FEEDS
     */
    public List<Feed> getAllFeeds() {

        List<Feed> feeds = new ArrayList<>();
        String selectQuery = SELECT_FROM + TABLE_IMAGES;

        SQLiteDatabase db = this.getReadableDatabase();
//        openDataBaseFeed();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Feed feed = new Feed();
                feed.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                feed.setName(c.getString((c.getColumnIndex(KEY_TITLE))));
                feed.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
                String[] s = convertStringToArray(c.getString(c.getColumnIndex(KEY_LINK)));
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

        String selectQuery = SELECT_FROM + TABLE_IMAGES + " WHERE "
                + KEY_ID + " = " + feed_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
        }

        Feed feed = new Feed();
        feed.setName(c.getString((c.getColumnIndex(KEY_TITLE))));
        feed.setDescription((c.getString(c.getColumnIndex(KEY_DESCRIPTION))));
        String[] s = convertStringToArray(c.getString(c.getColumnIndex(KEY_LINK)));
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
        db.delete(TABLE_IMAGES, KEY_ID + " = ?",
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
        values.put(KEY_TITLE, feed.getName());
        values.put(KEY_DESCRIPTION, feed.getDescription());
        String s = convertArrayToString(feed.getIdImgThumb());
        values.put(KEY_LINK, s);
        db.insert(TABLE_IMAGES, null, values);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
