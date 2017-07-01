package vn.asiantech.internship.music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to get music from your phone.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
class MusicManager {
    private Context mContext;

    MusicManager(Context context) {
        mContext = context;
    }

    List<Song> getSong() {
        List<Song> songs = new ArrayList<>();
        ContentResolver contentProvider = mContext.getContentResolver();
        Cursor cursor = contentProvider.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int path = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                int name = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
                songs.add(new Song(cursor.getString(name), cursor.getString(path)));
            }
            cursor.close();
        }
        return songs;
    }
}
