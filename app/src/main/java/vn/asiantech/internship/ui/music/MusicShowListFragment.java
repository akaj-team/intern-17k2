package vn.asiantech.internship.ui.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MusicShowListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicShowListFragment extends Fragment implements MusicAdapter.OnMusicListener {

    private static final String KEY = "KEY_MusicShowListFragment";
    private ArrayList<Music> mMusics;
    private MusicAdapter mMusicAdapter;

    public static MusicShowListFragment newInstance(ArrayList<Music> musics) {
        MusicShowListFragment musicShowListFragment = new MusicShowListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY, musics);
        musicShowListFragment.setArguments(bundle);
        return musicShowListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMusics = getArguments().getParcelableArrayList(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_music_show_list, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mMusicAdapter = new MusicAdapter(mMusics, this);
        recyclerView.setAdapter(mMusicAdapter);
        return v;
    }

    public void setHighLight(int position) {
        mMusicAdapter.setHighLight(position);
    }

    @Override
    public void onItemClick(int position) {
        if (getActivity() instanceof MusicActivity) {
            ((MusicActivity) getActivity()).playSong(position);
        }
    }
}
