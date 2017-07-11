package vn.asiantech.internship.ui.music;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
    private List<Song> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mRecyclerViewSong = (RecyclerView) findViewById(R.id.recyclerViewSong);
        mSongs = new ArrayList<>();
        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SongAdapter songAdapter = new SongAdapter(createSongs(), new SongAdapter.OnSelectSongListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MusicActivity.this, SongPlayingActivity.class);
                intent.putParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), (ArrayList<? extends Parcelable>) mSongs);
                intent.putExtra(Action.KEY_BUNDLE_POSITION.getValue(), position);
                startActivity(intent);
                intentStartService(position, Action.START.getValue());
            }
        });
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mRecyclerViewSong.setAdapter(songAdapter);
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

    public void intentStartService(int position, String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        intent.putParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), (ArrayList<? extends Parcelable>) mSongs);
        intent.putExtra(Action.KEY_BUNDLE_POSITION.getValue(), position);
        startService(intent);
    }
}
