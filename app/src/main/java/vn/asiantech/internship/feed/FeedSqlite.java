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
    private final Context mContext;
    private static final String DB_NAME = "list_image.sqlite";
    private static final String TABLE = "images";
    private final String mPath;
    private SQLiteDatabase mDataBase;

    FeedSqlite(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
        mPath = context.getFilesDir().getPath();
        boolean dbExist = checkDatabase();
        if (dbExist) {
            openDatabase();
        } else {
            createDatabase();
        }
    }

    private void createDatabase() {
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
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

    private void copyDatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        String outFileName = mPath + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    void openDatabase() {
        String path = mPath + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    public List<Image> getList() {
        List<Image> images = new ArrayList<>();
        openDatabase();
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
