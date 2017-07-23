package vn.asiantech.internship.ui.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.SongAdapter;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class MusicActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewSong;
    private List<Song> mSongs;
    private SongAdapter mSongAdapter;
    private int mCurrentPosition = -1;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Action.UPDATE_ISPLAYING.getValue())) {
                updateView(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mRecyclerViewSong = (RecyclerView) findViewById(R.id.recyclerViewSong);
        mSongs = new ArrayList<>();
        initAdapter();
        initIntentFilter();
        updateView(getIntent());
        Log.d("xxx", "onCreate: " + mCurrentPosition);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSongAdapter = new SongAdapter(createSongs(), new SongAdapter.OnSelectSongListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MusicActivity.this, SongPlayingActivity.class);
                intent.putParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), (ArrayList<? extends Parcelable>) mSongs);
                intent.putExtra(Action.KEY_BUNDLE_POSITION.getValue(), position);
                startActivity(intent);
                intentStartService(position);
            }
        });
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mRecyclerViewSong.setAdapter(mSongAdapter);
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.UPDATE_ISPLAYING.getValue());
        registerReceiver(mBroadcastReceiver, filter);
    }

    private void updateView(Intent intent) {
        if (intent.getIntExtra(Action.KEY_BUNDLE_POSITION.getValue(), -1) != -1) {
            int position = intent.getIntExtra(Action.KEY_BUNDLE_POSITION.getValue(), -1);
            Log.d("xxx", "updateView: " + position);
            resetSongPlaying();
            mSongs.get(position).setPlaying(true);
            mCurrentPosition = position;
            mSongAdapter.notifyDataSetChanged();
        }
    }

    public List<Song> createSongs() {
        mSongs.add(new Song(1073930186, "Trong Lòng Muốn Hát Thì Hát", "Đồng Lệ",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNVEVGLJXTLDJTDGLG", "http://i.imgur.com/t5oc1NR.jpg", 287));
        mSongs.add(new Song(1075832913, "Yêu 5", "Rhymastic", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJVDELVTLDJTDGLG",
                "http://avatar.nct.nixcdn.com/singer/avatar/2016/03/10/5/2/7/a/1457576967042_600.jpg", 328));
        mSongs.add(new Song(5935306, "Một Nhà", "Da Lab", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTQEVQVGXTLDJTDGLG",
                "http://avatar.nct.nixcdn.com/singer/avatar/2016/04/29/e/f/0/9/1461897320118_600.jpg", 184));
        mSongs.add(new Song(1075806956, "Đời Là Đi", "Da Lab", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJGXEQXTLDJTDGLG",
                "http://avatar.nct.nixcdn.com/singer/avatar/2016/04/29/e/f/0/9/1461897320118_600.jpg", 194));
        return mSongs;
    }

    private void intentStartService(int position) {
        resetSongPlaying();
        Intent intent = new Intent(this, MusicService.class);
        intent.putParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), (ArrayList<? extends Parcelable>) mSongs);
        intent.putExtra(Action.KEY_BUNDLE_POSITION.getValue(), position);
        startService(intent);
        mSongs.get(position).setPlaying(true);
        mCurrentPosition = position;
        mSongAdapter.notifyDataSetChanged();
    }

    private void resetSongPlaying() {
        if (mCurrentPosition != -1) {
            mSongs.get(mCurrentPosition).setPlaying(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
