package vn.asiantech.internship.ui.note.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.note.activity.NewNoteActivity;
import vn.asiantech.internship.ui.note.adapter.NoteAdapter;
import vn.asiantech.internship.ui.note.databases.DatabaseHelper;

/**
 * fragment show list note
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteFragment extends Fragment {
    public static final int CODE_INTENT = 1111;
    private NoteAdapter mNoteAdapter;
    private List<Note> mNotes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        mNotes = databaseHelper.getAllNote();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ImageView imgNewNote = (ImageView) view.findViewById(R.id.imgNewNote);
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewNote.setLayoutManager(linearLayoutManager);
        mNoteAdapter = new NoteAdapter(mNotes);
        recyclerViewNote.setAdapter(mNoteAdapter);

        imgNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewNoteActivity.class);
                startActivityForResult(intent, CODE_INTENT);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_INTENT && resultCode == Activity.RESULT_OK) {
            Note note = data.getParcelableExtra(NewNoteFragment.KEY_SEND_NOTE);
            mNotes.add(note);
            mNoteAdapter.notifyDataSetChanged();
        }
    }
}
