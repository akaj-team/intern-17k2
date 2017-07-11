package vn.asiantech.internship.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.SongListAdapter;
import vn.asiantech.internship.models.Action;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.main.MusicActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/01/2017
 */
public class MainFragment extends Fragment {

    private SongListAdapter mAdapter;
    private SongListAdapter.OnItemClickListener mListener;
    private List<Song> mSongs;
    private int mSongPosition = -1;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (Action.SONG_CHANGE.getValue().equals(action)) {
                    if (mSongPosition > -1) {
                        mSongs.get(mSongPosition).setPlaying();
                        mAdapter.notifyItemChanged(mSongPosition);
                    }
                    mSongPosition = intent.getIntExtra(MusicActivity.KEY_POSITION, -1);
                    if (mSongPosition > -1) {
                        mSongs.get(mSongPosition).setPlaying();
                    }
                    mAdapter.notifyItemChanged(mSongPosition);
                }
                if (Action.STOP_SERVICE.getValue().equals(action)) {
                    mSongs.get(mSongPosition).setPlaying();
                    mAdapter.notifyItemChanged(mSongPosition);
                }
            }

        }
    };

    public static MainFragment getNewInstance(ArrayList<Song> songs, int songPosition, SongListAdapter.OnItemClickListener listener) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.mListener = listener;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MusicActivity.KEY_SONGS, songs);
        bundle.putInt(MusicActivity.KEY_POSITION, songPosition);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSongs = getArguments().getParcelableArrayList(MusicActivity.KEY_SONGS);
        mSongPosition = getArguments().getInt(MusicActivity.KEY_POSITION);
        if (mSongPosition > -1) {
            mSongs.get(mSongPosition).setPlaying();
        }
        mAdapter = new SongListAdapter(getContext(), mSongs, mListener);
        IntentFilter intentFilter = new IntentFilter(Action.SONG_CHANGE.getValue());
        intentFilter.addAction(Action.STOP_SERVICE.getValue());
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerViewMainList = (RecyclerView) inflater.inflate(R.layout.fragment_music_main, container, false);
        recyclerViewMainList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMainList.setAdapter(mAdapter);
        return recyclerViewMainList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    public void setSongPosition(int position) {
        if (position > -1) {
            if (mSongPosition > -1) {
                mSongs.get(mSongPosition).setPlaying();
                mAdapter.notifyItemChanged(mSongPosition);
            }
            mSongPosition = position;
            mSongs.get(mSongPosition).setPlaying();
            mAdapter.notifyItemChanged(mSongPosition);
        }
    }
}
