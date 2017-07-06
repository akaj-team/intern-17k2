package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 05/07/2017.
 */

public class PlayMusicFragment extends Fragment {
    private static final String KEY_SONG = "song";
    private static final String KEY_POSITION = "position";
    private TextView mTvCurrentTime;
    private TextView mTvTime;
    private SeekBar mSeekBar;
    private ImageView mImgShuffle;
    private ImageView mImgPrevious;
    private ImageView mImgPlay;
    private ImageView mImgNext;
    private ImageView mImgLoop;
    private Song mSong;
    private int mPosition;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        initViews(view);
        mSong=getArguments().getParcelable(KEY_SONG);
        mPosition=getArguments().getInt(KEY_POSITION);
        return view;
    }

    private void initViews(View view) {
        mTvCurrentTime = (TextView) view.findViewById(R.id.tvCurrentTime);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        mImgShuffle = (ImageView) view.findViewById(R.id.imgShuffle);
        mImgPrevious = (ImageView) view.findViewById(R.id.imgPrevious);
        mImgPlay = (ImageView) view.findViewById(R.id.imgPlay);
        mImgNext = (ImageView) view.findViewById(R.id.imgNext);
        mImgLoop = (ImageView) view.findViewById(R.id.imgLoop);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
    }

    public static PlayMusicFragment newInstance(List<Song> songs,int position) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_SONG,songs.get(position));
        args.putInt(KEY_POSITION,position);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
