package vn.asiantech.internship.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Feed;

/**
 * Created by Thanh Thien on 6/21/17.
 * Feed Database
 */
public class FeedDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String SEPARATOR = ",";
    private static final String DB_NAME = "list_image.sqlite";
    private static final String TABLE_IMAGES = "images";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase mSQLiteDatabase;

    public FeedDatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        boolean dbExist = checkDatabase();
        if (dbExist) {
            openDatabase();
        } else {
            createDatabase();
        }
    }

    private void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        String myPath = mContext.getFilesDir().getPath() + File.separatorChar + DB_NAME;
        File dbFile = new File(myPath);
        return dbFile.exists();
    }

    private void copyDatabase() throws IOException {
        InputStream source = mContext.getAssets().open(DB_NAME);
        String target = mContext.getFilesDir().getPath() + File.separatorChar + DB_NAME;
        OutputStream outputStream = new FileOutputStream(target);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = source.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        source.close();
    }

    private void openDatabase() throws SQLException {
        // Open the database
        String dbPath = mContext.getFilesDir().getPath() + File.separatorChar + DB_NAME;
        mSQLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mSQLiteDatabase != null) {
            mSQLiteDatabase.close();
        }
        super.close();
    }

    public List<Feed> getAllFeeds() {
        List<Feed> feeds = new ArrayList<>();
        openDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * from " + TABLE_IMAGES, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String[] strings = convertStringToArray(cursor.getString(3));
            Feed feed = new Feed(cursor.getInt(0), cursor.getString(1), strings, cursor.getString(2));
            feeds.add(feed);
            cursor.moveToNext();
        }
        cursor.close();
        return feeds;
    }

    private String[] convertStringToArray(String str) {
        return str.split(SEPARATOR);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
