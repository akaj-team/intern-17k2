package vn.asiantech.internship.ui.note.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.note.activity.NewNoteActivity;
import vn.asiantech.internship.ui.note.adapter.NoteAdapter;
import vn.asiantech.internship.ui.note.databases.DatabaseHelper;

/**
 * fragment show list note
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ImageView imgNewNote = (ImageView) view.findViewById(R.id.imgNewNote);
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        recyclerViewNote.setLayoutManager(linearLayoutManager);
        NoteAdapter noteAdapter = new NoteAdapter(databaseHelper.getAllNote());
        recyclerViewNote.setAdapter(noteAdapter);

        imgNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewNoteActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


}
