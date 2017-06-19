package vn.asiantech.internship.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.adapter.NoteAdapter;

/**
 * Created by PC on 6/19/2017.
 */

public class NoteFragment extends Fragment {
    private RecyclerView mRecyclerViewNote;
    private NoteAdapter mAdapter;
    private List<NoteItem> mNoteList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
