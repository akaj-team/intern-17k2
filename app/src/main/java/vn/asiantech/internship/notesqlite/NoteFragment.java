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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ImageView imgAdd = (ImageView) view.findViewById(R.id.imgAddNote);
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNote.setLayoutManager(linearLayoutManager);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NoteActivity) getActivity()).showAdd();
            }
        });

        NoteSqlite database = new NoteSqlite(getActivity());
        database.open();
        mNotes.addAll(database.getNotes());
        database.close();
        NoteAdapter adapter = new NoteAdapter(mNotes);
        recyclerViewNote.setAdapter(adapter);
        return view;
    }
}
