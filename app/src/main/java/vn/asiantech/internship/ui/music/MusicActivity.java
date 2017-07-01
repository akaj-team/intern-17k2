package vn.asiantech.internship.ui.music;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.SongAdapter;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;

/**
 * Created by quanghai on 30/06/2017.
 */

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_INTENT = "package";
    public static final String KEY_BUNDLE_ARRAYLIST = "arraylist";
    public static final String KEY_BUNDLE_POSITION = "position";

    private RecyclerView mRecyclerViewSong;
    private ImageView mImgThumnail;
    private ImageView mImgPrevious;
    private ImageView mImgPause;
    private ImageView mImgNext;

    private SongAdapter mAdapter;
    private int mCurrentPosition;
   // private boolean mIsPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initAdapter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPrevious:
                mCurrentPosition--;
                Log.d("xxx", "onClick: " + mCurrentPosition);
                intentService(mCurrentPosition);
                break;
            case R.id.imgPause:
               // mImgPause.setImageResource(mIsPlaying ? R.drawable.ic_pause_black_24dp : R.drawable.ic_play_arrow_black_24dp);
                break;
            case R.id.imgNext:
                mCurrentPosition++;
                intentService(mCurrentPosition);
                break;
        }
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        ContentResolver musicResolver = getContentResolver();
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
                String time = cursor.getString(timeColumn);
                songs.add(new Song(id, title, artist, time));
            }
            cursor.close();
        }
        return songs;
    }

    private void initView() {
        mImgThumnail = (ImageView) findViewById(R.id.imgThumnail);
        mImgPrevious = (ImageView) findViewById(R.id.imgPrevious);
        mImgPause = (ImageView) findViewById(R.id.imgPause);
        mImgNext = (ImageView) findViewById(R.id.imgNext);
        mRecyclerViewSong = (RecyclerView) findViewById(R.id.recyclerViewSong);

        mImgPrevious.setOnClickListener(this);
        mImgPause.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SongAdapter(getSongs(), new SongAdapter.OnListener() {
            @Override
            public void onItemClick(int position) {
                intentService(position);
                mCurrentPosition = position;
            }
        });
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mRecyclerViewSong.setAdapter(mAdapter);
    }

    private void intentService(int position) {
        Intent intent = new Intent(MusicActivity.this, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_BUNDLE_ARRAYLIST, (ArrayList<? extends Parcelable>) getSongs());
        bundle.putInt(KEY_BUNDLE_POSITION, position);
        intent.putExtra(KEY_INTENT, bundle);
        startService(intent);
    }
}
