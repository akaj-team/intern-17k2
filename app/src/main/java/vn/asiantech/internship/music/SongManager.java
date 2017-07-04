package vn.asiantech.internship.music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by sony on 04/07/2017.
 */

public class SongManager {
    public SongManager() {
    }

    public ArrayList<Song> getListSongOffline(Context context) {
        ArrayList<Song> songs = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();
        Cursor musicCursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        int musicColumnIndex;

        if (null != musicCursor && musicCursor.getCount() > 0) {
            for (musicCursor.moveToFirst(); !musicCursor.isAfterLast(); musicCursor.moveToNext()) {
                musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
                String title = musicCursor.getString(musicColumnIndex);
                musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
                String url = musicCursor.getString(musicColumnIndex);
                musicColumnIndex = musicCursor
                        .getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
                String singer = musicCursor.getString(musicColumnIndex);
                songs.add(new Song(title, singer, url, R.drawable.img_future));
            }
            musicCursor.close();
        }
        return songs;
    }
}
