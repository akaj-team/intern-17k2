package vn.asiantech.internship.notesqlite;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display note recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
public class NoteFragment extends Fragment {
    private final List<Note> mNotes = new ArrayList<>();
    private OnGetDataListener mListener;
    private ImageView mImgAdd;
    private RecyclerView mRecyclerViewNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteSqlite database = new NoteSqlite(getActivity());
        database.open();
        mNotes.addAll(database.getNotes());
        database.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        mListener = (OnGetDataListener) getActivity();
        initViews(view);
        setListeners();
        return view;
    }

    private void initViews(View view) {
        mImgAdd = (ImageView) view.findViewById(R.id.imgAddNote);
        mRecyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerViewNote.setLayoutManager(linearLayoutManager);
    }

    private void setListeners() {
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NoteActivity) getActivity()).replaceAddNoteFragment();
            }
        });

        mRecyclerViewNote.setAdapter(new NoteAdapter(mNotes, new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                getData(note);
            }

        }));
    }

    private void getData(Note note) {
        mListener.onGetData(note);
    }

    /**
     * Used to get note from fragment to activity
     */
    interface OnGetDataListener {
        void onGetData(Note note);
    }
}
