package vn.asiantech.internship.feed;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to save data.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */
class FeedSqlite extends SQLiteOpenHelper {
    private Context mContext;
    private static String DB_NAME = "list_image.sqlite";
    private final String TABLE = "images";
    private String mPath;
    private SQLiteDatabase mDataBase;

    FeedSqlite(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        mPath = context.getFilesDir().getPath();
        boolean dbexist = checkDatabase();
        if (dbexist) {
            try {
                opendatabase();
            } catch (SQLException e) {
                Log.e("Can't open database", e.toString());
            }
        } else {
            createDatabase();
        }
    }

    private void createDatabase() {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                Log.e("Can't open database", e.toString());
            }
        }
    }

    private boolean checkDatabase() {
        boolean checkDb = false;
        try {
            String myPath = mPath + DB_NAME;
            File dbfile = new File(myPath);
            checkDb = dbfile.exists();
        } catch (SQLiteException e) {
            Log.e("Database doesn't exist", e.toString());
        }
        return checkDb;
    }

    private void copydatabase() throws IOException {
        InputStream myinput = mContext.getAssets().open(DB_NAME);
        String outfilename = mPath + DB_NAME;
        OutputStream myoutput = new FileOutputStream(outfilename);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    private void opendatabase() throws SQLException {
        String mypath = mPath + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    public List<Image> getList() {
        List<Image> images = new ArrayList<>();
        try {
            opendatabase();
        } catch (SQLException e) {
            Log.e("Can't open databse", e.toString());
        }
        Cursor cursor = mDataBase.rawQuery("SELECT * from " + TABLE, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            images.add(new Image(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return images;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }
}
