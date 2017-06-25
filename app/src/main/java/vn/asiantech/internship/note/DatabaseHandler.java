package vn.asiantech.internship.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sqlite";
    private static final String TABLE_CONTACTS = "tablenote";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String NOTE = "note";
    private static final String TIME = "time";
    private static final String IMAGE = "image";

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TITLE + " TEXT," + NOTE + " TEXT,"
                + TIME + " TEXT," + IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

//    /**
//     * All CRUD(Create, Read, Update, Delete) Operations
//     */
//    // Adding new contact
//    public long addContact(ItemNote contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(TITLE, contact.getTitle());
//        values.put(NOTE, contact.getNote());
//        values.put(TIME, contact.getTime());
//        values.put(IMAGE, contact.getImage());
//        return db.insert(TABLE_CONTACTS, null, values);
//    }
//
//    // Getting single contact
//    ItemNote getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{ID,
//                        TITLE, NOTE, TIME, IMAGE}, ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            itemNote = new ItemNote((cursor.getInt(0)),
//                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
//            // Return contact
//            cursor.moveToNext();
//        }
//        return itemNote;
//    }
//
//    // Getting All Contacts
//    List<ItemNote> getAllContacts() {
//        List<ItemNote> itemNotes = new ArrayList<ItemNote>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
////        cursor.moveToFirst();
//        // Looping through all rows and adding to list
//        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//            ItemNote itemNote = new ItemNote();
//            itemNote.setTitle(cursor.getString(1));
//            itemNote.setNote(cursor.getString(1));
//            itemNote.setTime(cursor.getString(1));
//            itemNote.setImage(cursor.getString(1));
//            itemNotes.add(itemNote);
////            itemNotes.add(new ItemNote(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
////                    cursor.getString(3), cursor.getString(4)));
//            // Adding contact to list
////            cursor.moveToNext();
//        }
//        cursor.close();
//        // Return contact list
//        return itemNotes;
//    }
//
//    // Updating single contact
//
//    public int updateContact(ItemNote itemNote) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(TITLE, itemNote.getTitle());
//        values.put(NOTE, itemNote.getNote());
//        values.put(IMAGE, itemNote.getImage());
//        values.put(TIME, itemNote.getTime());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, ID + " = " + itemNote, null);
//    }
//
//    // Deleting single contact
//    public void deleteContact(ItemNote itemNote) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, ID + " = ?",
//                new String[]{String.valueOf(itemNote.getId())});
//        db.close();
//    }
//
//
//    // Getting contacts Count
//    public int getContactsCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        // return count
//        return cursor.getCount();
//    }
}
