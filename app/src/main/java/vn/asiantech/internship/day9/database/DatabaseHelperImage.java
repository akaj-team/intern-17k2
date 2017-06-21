package vn.asiantech.internship.day9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by at-hoavo on 20/06/2017.
 */
public class DatabaseHelperImage extends SQLiteOpenHelper {
    private Context mContext;

    private String DB_PATH = "data/data/vn.asiantech.internship/";
    private static String DB_NAME = "list_image.sqlite";
    private SQLiteDatabase myDatabase;

    public DatabaseHelperImage(Context context) {
        super(context, DB_NAME, null, 1);

        this.mContext = context;
        boolean dbexist = checkdatabase();

        if (dbexist) {
        } else {
            System.out.println("Database doesn't exist!");

            createDatabse();
        }
    }

    private void createDatabse() {

        this.getReadableDatabase();

        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public SQLiteDatabase getMyDatabase() {
        return myDatabase;
    }


    private void copyDatabase() throws IOException {

//        AssetManager dirPath = mContext.getAssets();

        InputStream myInput = mContext.getAssets().open(DB_NAME);

//        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream("data/data/vn.asiantech.internship/list_image.sqlite");

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void open() {
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        myDatabase.close();
        super.close();
    }

    private boolean checkdatabase() {

        boolean checkdb = false;

        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Databse doesn't exist!");
        }

        return checkdb;
    }

    private DatabaseHelperImage(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
