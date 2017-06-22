package vn.asiantech.internship.day9.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 20/06/2017.
 */
public class ImageModify {
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "decription";
    public static final String KEY_LINK = "link";

    private static final String DATABASE_TABLE = "images";

    private DatabaseHelperImage mDatabaseHelperImage;

    public ImageModify(Context context) {
        mDatabaseHelperImage = new DatabaseHelperImage(context);
        mDatabaseHelperImage.open();
    }

    public Cursor getInformation() {
        SQLiteDatabase sqliteDatabase = mDatabaseHelperImage.getMyDatabase();
        String sql = "select * from " + ImageModify.DATABASE_TABLE;
        Cursor cursor = sqliteDatabase.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        sqliteDatabase.close();
        return cursor;
    }
}
