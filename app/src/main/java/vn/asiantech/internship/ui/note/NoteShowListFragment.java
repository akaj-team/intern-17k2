package vn.asiantech.internship.ui.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.NoteAdapter;
import vn.asiantech.internship.database.DatabaseHelper;
import vn.asiantech.internship.models.Note;

/**
 * NoteShowListFragment Created by Thanh Thien
 */
public class NoteShowListFragment extends Fragment {
    private NoteFragment mNoteFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_show_list, container, false);
        ImageView imgAdd = (ImageView) v.findViewById(R.id.imgAdd);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        mNoteFragment = new NoteFragment();
        List<Note> notes = databaseHelper.getAllNotes();

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(new NoteAdapter(notes));
        if (getActivity() instanceof NoteActivity) {
            ((NoteActivity) getActivity()).setToolbar(toolbar);
        }
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteFragment.replaceFragmentAddContent(getActivity(), new NoteAddNewFragment());
            }
        });
        return v;
    }
}
