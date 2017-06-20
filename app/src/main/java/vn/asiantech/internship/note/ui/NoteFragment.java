package vn.asiantech.internship.note.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.model.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private List<Note> mNotes;
    private NoteAdapter mNoteAdapter;
    private NoteDatabase mNoteDatabase;

    public NoteFragment() {
        // Required empty public constructor
        mNoteDatabase = new NoteDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_note, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycleViewNote);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
        mNoteDatabase.open();
    }

    private void initUI() {
        mNotes = new ArrayList<>();
        initFriendData(mNotes);
        mNoteAdapter = new NoteAdapter(mNotes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mNoteAdapter);
    }

    private void initFriendData(List<Note> notes) {
        /*notes.add(new Note("Note01", "DinhDepTrai1"));
        notes.add(new Note("Note02", "DinhDepTrai2"));
        notes.add(new Note("Note03", "DinhDepTrai3"));
        notes.add(new Note("Note04", "DinhDepTrai4"));
        notes.add(new Note("Note05", "DinhDepTrai5"));
        notes.add(new Note("Note06", "DinhDepTrai6"));
        notes.add(new Note("Note07", "DinhDepTrai7"));*/
        notes = mNoteDatabase.getAllData();
        if(notes.size() > 0){
            Toast.makeText(getContext(), "getdata is ok", Toast.LENGTH_SHORT);
        }else{
            Toast.makeText(getContext(), "getdata is not ok", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }
}
