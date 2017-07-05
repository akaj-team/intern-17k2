package vn.asiantech.internship.ui.fragments;

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

    public static MainFragment getNewInstance(ArrayList<Song> songs, SongListAdapter.OnItemClickListener listener) {
        MainFragment mainFragment = new MainFragment();
        mainFragment.mListener = listener;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MusicActivity.KEY_SONGS, songs);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Song> songs = getArguments().getParcelableArrayList(MusicActivity.KEY_SONGS);
        mAdapter = new SongListAdapter(getContext(), songs, mListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerViewMainList = (RecyclerView) inflater.inflate(R.layout.fragment_music_main, container, false);
        recyclerViewMainList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMainList.setAdapter(mAdapter);
        return recyclerViewMainList;
    }
}
