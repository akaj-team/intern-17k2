package vn.asiantech.internship.feed;

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

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "list_image.sqlite";
    private static final String TABLE_NAME = "images";
    private String mPath;
    private Context mContext;
    private SQLiteDatabase mDb;

    DataBaseHandler(Context context) throws IOException {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        mPath = mContext.getFilesDir().getPath() + File.separatorChar;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            opendatabase();
        } else {
            createdatabase();
        }
    }

    private void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            Log.d("tag", "createdatabase: ");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {
        boolean checkdb;
        try {
            String myPath = mPath + DATABASE_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            throw new Error("Error copying database");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        InputStream myinput = mContext.getAssets().open(DATABASE_NAME);
        String myPath = mPath + DATABASE_NAME;
        OutputStream myoutput = new FileOutputStream(myPath);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    void opendatabase() throws SQLException {
        String myPath = mPath + DATABASE_NAME;
        mDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mDb != null) {
            mDb.close();
        }
        super.close();
    }

    List<Feed> getFeeds() {
        List<Feed> feed = new ArrayList<>();
        opendatabase();
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            feed.add(new Feed(cursor.getString(1), cursor.getString(3), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return feed;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
