package vn.asiantech.internship.ui.music;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.SongAdapter;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class MusicActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mRecyclerViewSong = (RecyclerView) findViewById(R.id.recyclerViewSong);
        initAdapter();
    }

    public List<Song> getAllSong(Context context) {
        List<Song> songs = new ArrayList<>();
        ContentResolver musicResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = musicResolver.query(musicUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int timeColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumn);
                String title = cursor.getString(titleColumn);
                String artist = cursor.getString(artistColumn);
                int duration = cursor.getInt(timeColumn);
                songs.add(new Song(id, title, artist, duration));
            }
            cursor.close();
        }
        return songs;
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SongAdapter adapter = new SongAdapter(getAllSong(this), new SongAdapter.OnListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MusicActivity.this, SongPlayingActivity.class);
                intent.putExtra(Action.KEY_BUNDLE_POSITION.getValue(), position);
                startActivity(intent);
                intentStartService(MusicActivity.this, position, Action.START.getValue());
            }
        });
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mRecyclerViewSong.setAdapter(adapter);
    }

    public void intentStartService(Context context, int position, String action) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(action);
        intent.putParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), (ArrayList<? extends Parcelable>) getAllSong(context));
        intent.putExtra(Action.KEY_BUNDLE_POSITION.getValue(), position);
        context.startService(intent);
    }
}
