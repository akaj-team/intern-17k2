package vn.asiantech.internship.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.compat.BuildConfig;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.ui.feed.Post;

/**
 * Created by ducle on 20/06/2017.
 */
public class ImageDatabase extends SQLiteOpenHelper {
    private Context mContext;
    private static final String DB_NAME = "list_image.sqlite";
    private static final String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private SQLiteDatabase database;

    public ImageDatabase(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        mContext = context;
        if (database != null && database.isOpen()) {
            database.close();
        }
        boolean dbexist = checkdatabase();
        if (dbexist) {
            Log.i("tag11","Database exists");
            opendatabase();
        } else {
            Log.i("tag11","Database doesn't exist");
            createdatabase();
            opendatabase();
        }
    }

    private void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            Log.i("tag11"," Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private void copydatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        String outFileName = mContext.getFilesDir().getPath() + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, lenght);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void opendatabase() throws SQLiteException {
        String myPath = mContext.getFilesDir().getPath() + DB_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkdb = dbFile.exists();
        } catch (SQLiteException e) {
            Log.i("tag11","Database doesn't exist 2");
        }
        return checkdb;
    }

    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    public List<Post> getList() {
        List<Post> posts = new ArrayList<>();
        opendatabase();
        if (database == null) {
            return null;
        }
        Cursor cursor = database.rawQuery("SELECT * FROM images", null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Post post = new Post();
            post.setName(cursor.getString(cursor.getColumnIndex("title")));
            post.setDesription(cursor.getString(cursor.getColumnIndex("decription")));
            post.setImageList(cursor.getString(cursor.getColumnIndex("link")));
            posts.add(post);
        }
        cursor.close();
        close();
        return posts;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
