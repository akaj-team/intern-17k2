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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_INTENT = "package";
    public static final String KEY_BUNDLE_ARRAYLIST = "arraylist";
    public static final String KEY_BUNDLE_POSITION = "position";

    private LinearLayout mLlSong;
    private RecyclerView mRecyclerViewSong;
    private ImageView mImgThumnail;
    private ImageView mImgPrevious;
    private ImageView mImgPause;
    private ImageView mImgNext;
    private TextView mTvSongName;
    private TextView mTvSinger;

    private int mCurrentPosition;
    private boolean mIsPlaying;

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
                if (mCurrentPosition > 0) {
                    mCurrentPosition--;
                    setViewAction();
                    intentStartService(this, mCurrentPosition);
                }
                break;
            case R.id.imgPause:
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                if (mIsPlaying) {
                    mImgPause.setImageResource(R.drawable.ic_pause_black_24dp);
                    intent.setAction(Action.PAUSE.getValue());
                    startService(intent);
                    mIsPlaying = false;
                    return;
                }
                mImgPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                intent.setAction(Action.RESUME.getValue());
                startService(intent);
                mIsPlaying = true;
                break;
            case R.id.imgNext:
                if (mCurrentPosition < getAllSong(this).size() - 1) {
                    mCurrentPosition++;
                    setViewAction();
                    intentStartService(this, mCurrentPosition);
                }
                break;
            case R.id.llSong:
                Intent intentDetail = new Intent(MusicActivity.this, SongPlayingActivity.class);
                intentDetail.putExtra("position", mCurrentPosition);
                startActivity(intentDetail);
                break;
        }
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
                long duration = cursor.getInt(timeColumn);
                songs.add(new Song(id, title, artist, duration));
            }
            cursor.close();
        }
        return songs;
    }

    private void initView() {
        mLlSong = (LinearLayout) findViewById(R.id.llSong);
        mImgThumnail = (ImageView) findViewById(R.id.imgThumnail);
        mImgPrevious = (ImageView) findViewById(R.id.imgPrevious);
        mImgPause = (ImageView) findViewById(R.id.imgPause);
        mImgNext = (ImageView) findViewById(R.id.imgNext);
        mTvSongName = (TextView) findViewById(R.id.tvSongName);
        mTvSinger = (TextView) findViewById(R.id.tvSinger);
        mRecyclerViewSong = (RecyclerView) findViewById(R.id.recyclerViewSong);

        mLlSong.setOnClickListener(this);
        mImgPrevious.setOnClickListener(this);
        mImgPause.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SongAdapter adapter = new SongAdapter(getAllSong(this), new SongAdapter.OnListener() {
            @Override
            public void onItemClick(int position) {
                mCurrentPosition = position;
                intentStartService(MusicActivity.this, position);
                setViewAction();
            }
        });
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mRecyclerViewSong.setAdapter(adapter);
    }

    public void intentStartService(Context context, int position) {
        Intent intent = new Intent(context, MusicService.class);
        intent.setAction(Action.START.getValue());
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_BUNDLE_ARRAYLIST, (ArrayList<? extends Parcelable>) getAllSong(context));
        bundle.putInt(KEY_BUNDLE_POSITION, position);
        intent.putExtra(KEY_INTENT, bundle);
        context.startService(intent);
    }

    private void setViewAction() {
        mLlSong.setVisibility(View.VISIBLE);
        mImgPrevious.setVisibility(View.VISIBLE);
        mImgPause.setVisibility(View.VISIBLE);
        mImgNext.setVisibility(View.VISIBLE);

        Song song = getAllSong(this).get(mCurrentPosition);
        mTvSongName.setText(song.getTitle());
        mTvSinger.setText(song.getArtist());
    }

    public String convertDuration(int duration) {
        String out = null;
        long hours;
        try {
            hours = (duration / 3600000);
        } catch (Exception e) {
            e.printStackTrace();
            return out;
        }
        long remaining_minutes = (duration - (hours * 3600000)) / 60000;
        String minutes = String.valueOf(remaining_minutes);
        if (minutes.equals(0)) {
            minutes = "00";
        }
        long remaining_seconds = (duration - (hours * 3600000) - (remaining_minutes * 60000));
        String seconds = String.valueOf(remaining_seconds);
        if (seconds.length() < 2) {
            seconds = "00";
        } else {
            seconds = seconds.substring(0, 2);
        }
        if (hours > 0) {
            out = hours + ":" + minutes + ":" + seconds;
        } else {
            out = minutes + ":" + seconds;
        }
        return out;
    }
}
