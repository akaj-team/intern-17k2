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
 * Read Database list_image
 * Created by huypham on 20/06/2017.
 */
public class FeedItemDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "list_image.sqlite";
    private static final String TABLE = "images";
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public FeedItemDatabase(Context context) throws IOException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;

        boolean dbExists = checkDatabase();
        if (dbExists) {
            openDatabase();
        } else {
            createDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean checkDatabase() {
        String dbPath = mContext.getFilesDir().getPath() + File.separatorChar + DATABASE_NAME;
        File dbFile = new File(dbPath);
        return dbFile.exists();
    }

    private void createDatabase() throws IOException {
        boolean dbExists = checkDatabase();
        if (!dbExists) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error");
            }
        }
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DATABASE_NAME);
        String outFileName = mContext.getFilesDir().getPath() + File.separatorChar + DATABASE_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buff = new byte[1024];
        int length;
        while ((length = inputStream.read(buff)) > 0) {
            outputStream.write(buff, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase() throws SQLException {
        String dbPath = mContext.getFilesDir().getPath() + File.separatorChar + DATABASE_NAME;
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    public List<Feed> getFeedList() {
        List<Feed> feedList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            feedList.add(new Feed(cursor.getString(1), cursor.getString(3), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return feedList;
    }
}
