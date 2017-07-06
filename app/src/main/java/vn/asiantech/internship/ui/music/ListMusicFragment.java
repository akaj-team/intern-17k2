package vn.asiantech.internship.ui.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class ListMusicFragment extends Fragment {
    private static final String KEY_RESULT = "KEY_RESULT";
    private List<Music> mMusics;

    public static ListMusicFragment newInstance(ArrayList<Music> musics) {
        ListMusicFragment fragment = new ListMusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_RESULT, musics);
        fragment.setArguments(args);
        return new ListMusicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusics = getArguments().getParcelableArrayList(KEY_RESULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_music, container, false);

        return v;
    }
}
