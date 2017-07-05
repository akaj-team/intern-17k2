package vn.asiantech.internship.ui.note;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.NoteDatabase;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.adapters.NoteAdapter;

/**
 * Home Fragment
 * Created by anhhuy on 20/06/2017.
 */
public class HomeFragment extends Fragment {
    private NoteAdapter.OnItemClickListener mOnClickListener;
    private RecyclerView mRecyclerViewAllNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerViewAllNote = (RecyclerView) view.findViewById(R.id.recyclerViewHome);

        initAdapter();
        return view;
    }

    private void initAdapter() {
        NoteDatabase database = new NoteDatabase(getContext());
        database.open();
        List<Note> mNoteList = database.getNoteList();

        NoteAdapter mNoteAdapter = new NoteAdapter(mNoteList, mOnClickListener);
        mRecyclerViewAllNote.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewAllNote.setAdapter(mNoteAdapter);
        mRecyclerViewAllNote.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                outRect.set(20, position == 0 ? 15 : 10, 20, 10);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnClickListener = (NoteAdapter.OnItemClickListener) activity;
    }
}
