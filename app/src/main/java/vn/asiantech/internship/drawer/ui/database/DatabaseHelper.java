package vn.asiantech.internship.drawer.ui.database;

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

/**
 * Created by at-dinhvo on 05/07/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "list_image.sqlite";
    private static final String TABLE_IMAGE = "images";
    private static final String COL_LINK = "link";
    private SQLiteDatabase mDatabase;
    private String dataPath;
    private Context mContext;

    public DatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        dataPath = context.getFilesDir().getPath();
        this.mContext = context;
        boolean dbexist = checkDatabase();
        if (dbexist) {
            Log.e("Ok", "Database exists");
            openDatabase();
        } else {
            Log.e("Error", "Database exists");
            createDatabase();
        }
    }

    private void createDatabase() throws IOException {
        boolean isExist = checkDatabase();
        if (isExist) {
            Log.e("IOException", "Database exists");
        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        boolean checkDatabase = false;
        try {
            String path = dataPath + DB_NAME;
            File dbFile = new File(path);
            checkDatabase = dbFile.exists();
        } catch (SQLiteException e) {
            Log.e("SQLiteException", "Database doesn't exist");
        }
        return checkDatabase;
    }

    private void copyDatabase() throws IOException {
        InputStream myinput = mContext.getAssets().open(DB_NAME);
        String outfilename = dataPath + DB_NAME;
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

    private void openDatabase() throws SQLException {
        String mypath = dataPath + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    public ArrayList<String[]> getAllData() {
        ArrayList<String[]> links = new ArrayList<>();
        String sql = "SELECT " + COL_LINK + " FROM " + TABLE_IMAGE;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        String[] listUrl;
        String url;
        int c = cursor.getColumnIndex(COL_LINK);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            url = cursor.getString(c);
            listUrl = url.split(",");
            for (int i = 0; i < listUrl.length; i++) {
                listUrl[i] = listUrl[i].trim();
            }
            links.add(listUrl);
        }
        cursor.close();
        return links;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
