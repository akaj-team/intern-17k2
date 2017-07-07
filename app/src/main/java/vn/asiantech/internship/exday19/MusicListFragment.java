package vn.asiantech.internship.exday19;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 02-07-2017.
 */
public class MusicListFragment extends Fragment {
    public static final String[] URL = {"http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQQVEXVVTLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNVEGEADETLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQLGJLVQTLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNXDDGVVATLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQAXQGGETLDJTDGLG"};
    public static final String[] SONG_NAME = {"Butterfly - BEAST",
            "On Rainy Days - BEAST",
            "12:30' - BEAST",
            "Attention - Charlie Puth",
            "We Don't Talk Anymore - Charlie Puth, Selena Gomez"};
    public static String[] IMAGE = {"http://goodmomusic.net/wp-content/uploads/2016/06/beast-butterfly-1.png",
            "http://zmp3-photo-fbcrawler-td.zadn.vn/thumb/600_600/covers/1/0/107f98d149a0f3e406a92b349773749b_1305132154.jpg",
            "http://data.whicdn.com/images/146896977/superthumb.jpg",
            "https://images.genius.com/bc29e1ff20b4931dd9919f2ab5252b0e.1000x1000x1.jpg",
            "https://images.genius.com/3bfb705b70df7ba7f0b19ff356fea3b5.1000x1000x1.jpg"};
    private MusicListAdapter.OnClickMusicItemListener mOnClickMusicItemListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_list, container, false);
        ArrayList<MusicItem> musicItems = new ArrayList<>();
        for (int i = 0; i < URL.length; i++) {
            musicItems.add(new MusicItem(URL[i], SONG_NAME[i], IMAGE[i]));
        }

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvSong);
        recyclerView.setLayoutManager(manager);
        // Our classic custom Adapter.
        MusicListAdapter adapter = new MusicListAdapter(getContext(), musicItems, mOnClickMusicItemListener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnClickMusicItemListener = (MusicListAdapter.OnClickMusicItemListener) activity;
    }
}
