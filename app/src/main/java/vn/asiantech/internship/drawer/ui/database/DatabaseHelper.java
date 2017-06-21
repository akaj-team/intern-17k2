package vn.asiantech.internship.drawer.ui.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by at-dinhvo on 21/06/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "list_image.sqlite";
    private static final String TABLE_IMAGE = "images";
    private SQLiteDatabase mDatabase;
//    private static final String DB_PATH = "/data/data/databases/";
    private String DB_PATH;
    private Context mContext;

    public DatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        DB_PATH = context.getFilesDir().getPath();
        this.mContext = context;
        boolean dbexist = checkDatabase();
        if (dbexist) {
            System.out.println("Database exists");
            openDatabase();
        } else {
            System.out.println("Database doesn't exist");
            createDatabase();
        }
    }

    private void createDatabase() throws IOException {
        boolean isExist = checkDatabase();
        if (isExist) {
            System.out.println(" Database exists.");
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
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String path = DB_PATH + DB_NAME;
            File dbfile = new File(path);
            //checkdb = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copyDatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    private void openDatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> links = new ArrayList<>();
        String[] columns = new String[]{"link"};
        Cursor cursor = mDatabase.query(TABLE_IMAGE, columns, null, null, null, null, null);
        String s;
        int c = cursor.getColumnIndex("link");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            s = cursor.getString(c);
            links.add(s);
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
