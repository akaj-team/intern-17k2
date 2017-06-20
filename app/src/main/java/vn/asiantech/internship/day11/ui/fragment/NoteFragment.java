package vn.asiantech.internship.day11.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Created by rimoka on 19/06/2017.
 */
public class NoteFragment extends Fragment {
    private ImageView mImgAdd;
    private RecyclerView mRecyclerViewNote;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_note,container,false);
        mImgAdd= (ImageView) v.findViewById(R.id.imgAdd);
        mRecyclerViewNote= (RecyclerView) v.findViewById(R.id.recyclerViewNote);
        return v;
    }
}
