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

import vn.asiantech.internship.models.FeedItem;

/**
 * Feed Database
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/20/2017
 */
public class FeedDatabase extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DB_NAME = "list_image.sqlite";
    private static final String TABLE = "images";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase mSQLiteDatabase;

    public FeedDatabase(Context context) throws IOException {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        boolean dbExist = checkDatabase();
        if (dbExist) {
            openDatabase();
        } else {
            createDatabase();
        }
    }

    public void createDatabase() throws IOException {
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

    public void openDatabase() throws SQLException {
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

    public List<FeedItem> getFeeds() {
        List<FeedItem> feeds = new ArrayList<>();
        openDatabase();
        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * from " + TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            feeds.add(new FeedItem(cursor.getString(1), cursor.getString(3), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return feeds;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
