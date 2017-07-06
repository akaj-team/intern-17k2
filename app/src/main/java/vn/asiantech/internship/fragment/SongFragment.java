package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.SongAdapter;
import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 05/07/2017.
 * SongFragment is fragment contain songs list
 */
public class SongFragment extends Fragment {
    private static final String KEY_LIST_SONG = "list_song";
    private List<Song> mSongs;
    private RecyclerView mRecyclerViewSong;
    private SongAdapter mSongAdapter;
    private ProgressDialog mProgressDialog;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_song, container, false);
        mContext=view.getContext();
        mRecyclerViewSong = (RecyclerView) view.findViewById(R.id.recyclerViewSong);
        mSongs = getArguments().getParcelableArrayList(KEY_LIST_SONG);
        mSongAdapter = new SongAdapter(mSongs);
        mRecyclerViewSong.setAdapter(mSongAdapter);
        mRecyclerViewSong.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public static SongFragment newInstance(ArrayList<Song> songs) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_LIST_SONG, songs);
        SongFragment fragment = new SongFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void updateListSong(Song song){
        mSongs.add(song);
        mSongAdapter.notifyItemInserted(mSongs.size()-1);
    }
    public void showProgressDialog(){
        mProgressDialog=new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    public void closeProgressDialog(){
        if (mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
