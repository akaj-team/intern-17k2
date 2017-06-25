package vn.asiantech.internship.day11.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.adapter.NoteAdapter;
import vn.asiantech.internship.day11.database.DatabaseHelper;
import vn.asiantech.internship.day11.database.NoteModify;
import vn.asiantech.internship.day11.model.Note;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 19/06/2017.
 */
public class NoteFragment extends Fragment {
    private List<Note> mNotes = new ArrayList<>();
    private NoteActivity mNoteActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNoteActivity = (NoteActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotes=getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        RecyclerView recyclerViewNote = (RecyclerView) v.findViewById(R.id.recyclerViewNote);
        ImageView imgAdd = (ImageView) v.findViewById(R.id.imgAdd);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof NoteActivity) {
                    ((NoteActivity) getActivity()).changeFragment(new InformationEditFragment());
                }
            }
        });
        getData();
        NoteAdapter noteAdapter = new NoteAdapter(mNotes, mNoteActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNote.setLayoutManager(linearLayoutManager);
        recyclerViewNote.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
        return v;
    }

    private List<Note> getData() {
         List<Note> notes = new ArrayList<>();
        NoteModify noteModify = new NoteModify(getContext());
        Cursor cursor = noteModify.getNoteList();
        while (!cursor.isAfterLast()) {
            Note note = new Note(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TITLE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DESCRIPTION)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_IMAGE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DAY)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DATE)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_TIME)));
            notes.add(note);
            cursor.moveToNext();
        }
        return notes;
    }
}
