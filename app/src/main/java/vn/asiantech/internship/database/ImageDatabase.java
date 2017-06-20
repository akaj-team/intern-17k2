package vn.asiantech.internship.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.compat.BuildConfig;

import com.nostra13.universalimageloader.core.ImageLoader;

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
    private static String DB_NAME = "list_image.splite";
    private static String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    private SQLiteDatabase mImageData;

    public ImageDatabase(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        mContext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    private void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
            System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                //throw new Error("Error copying database");
            }
        }
    }

    private void copydatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
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

    private void opendatabase() throws SQLiteException {
        String myPath = DB_PATH + DB_NAME;
        mImageData = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbFile = new File(myPath);
            checkdb = (dbFile.exists() && !dbFile.isDirectory());
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    public synchronized void close() {
        if (mImageData != null) {
            mImageData.close();
        }
        super.close();
    }

    public List<Post> getList() {
        List<Post> posts = new ArrayList<>();
        opendatabase();
        Cursor cursor=mImageData.rawQuery("SELECT _id,title,decription,link FROM images",null);
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            Post post=new Post();
            post.setName(cursor.getString(cursor.getColumnIndex("title")));
            post.setDesription(cursor.getString(cursor.getColumnIndex("decription")));
            ImageLoader imageLoader=ImageLoader.getInstance();
            String links=cursor.getString(cursor.getColumnIndex("link"));
            List<Bitmap> images=new ArrayList<>();
            String[] linkAraay=links.split(",");
            for (int i=0;i<linkAraay.length;i++){
                images.add(imageLoader.loadImageSync(linkAraay[i]));
            }
            post.setImageList(images);
        }
        return posts;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
