package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.R.id.imgBtnNext;
import static vn.asiantech.internship.R.id.imgBtnPrev;
import static vn.asiantech.internship.R.id.seekBar;

/**
 * Used to display screen play music.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SongFragment.OnGetSongListener {
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnShuffle;
    private ImageButton mImgBtnAuto;
    private TextView mTvCurrentTime;
    private TextView mTvTotalTime;
    private SeekBar mSeekBar;
    private final List<Song> mSongs = new ArrayList<>();
    private final MusicTime mTime = new MusicTime();
    private int mLength;
    private int mPosition;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case "START":
                    mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                    break;
                case "SEEK":
                    processTime(intent);
                    break;
                case "PAUSE":
                    mImgBtnPlay.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
                    mPosition = Integer.parseInt(intent.getStringExtra("second"));
                    upDateTime();
                    break;
                case "PLAYNEXT":
                    mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                    mPosition = Integer.parseInt(intent.getStringExtra("next"));
                    upDateTime();
                    break;
                case "NEXT":
                    mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                    break;
                case "PREVIOUS":
                    mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                    break;
                case "NOTSHUFFEL":
                    mImgBtnShuffle.setImageResource(R.drawable.ic_shuffle_white_24dp);
                    break;
                case "SHUFFEL":
                    mImgBtnShuffle.setImageResource(R.drawable.ic_shuffle_red_400_24dp);
                    break;
                case "AUTONEXT":
                    mImgBtnAuto.setImageResource(R.drawable.ic_autorenew_red_400_24dp);
                    break;
                case "NOTAUTONEXT":
                    mImgBtnAuto.setImageResource(R.drawable.ic_autorenew_white_24dp);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initSongs();
        initView();
        registerBroadcastReceiver();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.putExtra("chooseTime", seekBar.getProgress());
                playIntent.setAction("seekto");
                startService(playIntent);
                mPosition = seekBar.getProgress();
                upDateTime();
            }
        });
    }

    private void initSongs() {
//        mSongs.add(new Song("Ghen", "Min", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNXDXGNQETLDJTDGLG", R.drawable.img_ghen));
//        mSongs.add(new Song("Shape Of You", "Ed Sheeran", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJGJLNDTLDJTDGLG", R.drawable.img_shape));
//        mSongs.add(new Song("Mask Off", "Future", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJAQXNNTLDJTDGLG", R.drawable.img_future));
//        mSongs.add(new Song("Stay", "Zedd, Alessia Cara", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJQJLQDTLDJTDGLG", R.drawable.img_stay));
//        mSongs.add(new Song("Believer", "Imagine Dragons", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJDQQLVTLDJTDGLG", R.drawable.img_bliever));
//        mSongs.add(new Song("Issues", "Julia Michaels", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJLDXGDTLDJTDGLG", R.drawable.img_issue));
        SongManager songManager = new SongManager();
        mSongs.addAll(songManager.getListSongOffline(this));
    }

    private void initView() {
        mImgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        ImageButton imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
        ImageButton imgBtnPrev = (ImageButton) findViewById(R.id.imgBtnPrev);
        mImgBtnShuffle = (ImageButton) findViewById(R.id.imgBtnShuffle);
        mImgBtnAuto = (ImageButton) findViewById(R.id.imgBtnAuto);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        mSeekBar = (SeekBar) findViewById(seekBar);
        ViewPager musicViewPager = (ViewPager) findViewById(R.id.musicViewPager);
        MusicViewPagerAdapter adapter = new MusicViewPagerAdapter(getSupportFragmentManager());
        musicViewPager.setAdapter(adapter);

        mImgBtnPlay.setOnClickListener(this);
        imgBtnNext.setOnClickListener(this);
        imgBtnPrev.setOnClickListener(this);
        mImgBtnShuffle.setOnClickListener(this);
        mImgBtnAuto.setOnClickListener(this);
    }

    private void registerBroadcastReceiver() {
        IntentFilter mStartFilter = new IntentFilter();
        mStartFilter.addAction(Action.START.getValue());
        mStartFilter.addAction(Action.SEEK.getValue());
        mStartFilter.addAction(Action.PAUSE.getValue());
        mStartFilter.addAction(Action.PLAYNEXT.getValue());
        mStartFilter.addAction(Action.PREVIOUS.getValue());
        mStartFilter.addAction(Action.NEXT.getValue());
        mStartFilter.addAction(Action.AUTONEXT.getValue());
        mStartFilter.addAction(Action.NOTAUTONEXT.getValue());
        mStartFilter.addAction(Action.SHUFFEL.getValue());
        mStartFilter.addAction(Action.NOTSHUFFEL.getValue());
        registerReceiver(mBroadcastReceiver, mStartFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnPlay:
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.setAction(Action.PLAY.getValue());
                playIntent.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) mSongs);
                startService(playIntent);
                break;
            case imgBtnNext:
                Intent nextIntent = new Intent(MusicActivity.this, MusicService.class);
                nextIntent.setAction(Action.NEXT.getValue());
                startService(nextIntent);
                break;
            case imgBtnPrev:
                Intent prevIntent = new Intent(MusicActivity.this, MusicService.class);
                prevIntent.setAction(Action.PREVIOUS.getValue());
                startService(prevIntent);
                break;
            case R.id.imgBtnShuffle:
                Intent shuffleIntent = new Intent(MusicActivity.this, MusicService.class);
                shuffleIntent.setAction(Action.SHUFFEL.getValue());
                startService(shuffleIntent);
                break;
            case R.id.imgBtnAuto:
                Intent autoIntent = new Intent(MusicActivity.this, MusicService.class);
                autoIntent.setAction(Action.AUTONEXT.getValue());
                startService(autoIntent);
                break;
        }
    }

    @Override
    public void onGetSong(Song song, int position) {
        Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
        playIntent.putExtra("currentSong", position);
        playIntent.setAction("chooseSong");
        startService(playIntent);
    }

    private void processTime(Intent intent) {
        mLength = Integer.parseInt(intent.getStringExtra("time"));
        mSeekBar.setMax(mLength);
        mPosition = Integer.parseInt(intent.getStringExtra("second"));
        upDateTime();
    }

    private void upDateTime() {
        mTvTotalTime.setText(mTime.milliSecondsToTimer(mLength));
        mTvCurrentTime.setText(mTime.milliSecondsToTimer(mPosition));
        mSeekBar.setProgress(mPosition);
        if (mTvTotalTime.getText().toString().equals(mTvCurrentTime.getText().toString())) {
            Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
            playIntent.setAction(Action.PLAYNEXT.getValue());
            startService(playIntent);
        }
    }

    public List<Song> getSongs() {
        return mSongs;
    }
}
