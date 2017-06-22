package vn.asiantech.internship.day9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by at-hoavo on 20/06/2017.
 */
public class DatabaseHelperImage extends SQLiteOpenHelper {
    private Context mContext;

    private final String DB_PATH = "data/data/vn.asiantech.internship/";
    private static final String DB_NAME = "list_image.sqlite";
    private SQLiteDatabase mMyDatabase;

    DatabaseHelperImage(Context context) {
        super(context, DB_NAME, null, 1);

        mContext = context;
        boolean dbexist = checkdatabase();

        if (!dbexist) {
            createDatabse();
        }
    }

    private void createDatabse() {

        this.getReadableDatabase();

        try {
            copyDatabase();
        } catch (IOException e) {
            Log.d("Exception", "Exception: IoException");
        }
    }


    SQLiteDatabase getmMyDatabase() {
        return mMyDatabase;
    }


    private void copyDatabase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    void open() {
        String myPath = DB_PATH + DB_NAME;
        mMyDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        mMyDatabase.close();
        super.close();
    }

    private boolean checkdatabase() {

        boolean checkdb = false;

        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            Log.d("Exception", "Exception: SQLiteException");
        }

        return checkdb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
