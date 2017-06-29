package vn.asiantech.internship.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.adapter.NoteAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/20/2017
 */
public class NoteFragment extends Fragment {

    private NoteAdapter.OnItemClickListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        Log.i("tag11", "note");
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        List<NoteItem> noteList;
        NoteDatabase noteDatabase = new NoteDatabase(getContext());
        noteDatabase.open();
        noteList = noteDatabase.getNoteList();
        NoteAdapter adapter = new NoteAdapter(noteList, mListener);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNote.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (NoteAdapter.OnItemClickListener) activity;
    }
}
