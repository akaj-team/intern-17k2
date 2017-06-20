package vn.asiantech.internship.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.NoteDatabase;
import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.adapter.NoteAdapter;

/**
 * Created by PC on 6/19/2017.
 */

public class NoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        Log.i("tag11","note");
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        List<NoteItem> noteList = new ArrayList<>();
        NoteDatabase noteDatabase = new NoteDatabase(getContext());
        noteDatabase.open();
        noteList = noteDatabase.getNoteList();
        NoteAdapter adapter = new NoteAdapter(noteList);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNote.setAdapter(adapter);
        return view;
    }
}
