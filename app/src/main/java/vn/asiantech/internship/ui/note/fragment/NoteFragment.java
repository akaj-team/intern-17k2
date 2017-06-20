package vn.asiantech.internship.ui.note.fragment;

import android.app.Fragment;
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
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.note.adapter.NoteAdapter;
import vn.asiantech.internship.note.databases.DatabaseHelper;

/**
 * Created by Hai on 6/19/2017.
 */

public class NoteFragment extends Fragment {
    private NoteAdapter mNoteAdapter;
    private List<Note> mNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewNote.setLayoutManager(linearLayoutManager);
        mNotes = new ArrayList<>();
        mNoteAdapter = new NoteAdapter(mNotes);
        recyclerViewNote.setAdapter(mNoteAdapter);

        DatabaseHelper dataBaseHelper = new DatabaseHelper(getContext());
        return view;
    }

}
