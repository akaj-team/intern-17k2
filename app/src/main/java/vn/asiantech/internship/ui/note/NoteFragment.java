package vn.asiantech.internship.ui.note;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.NoteAdapter;
import vn.asiantech.internship.models.Note;

/**
 * A simple Note class.
 * Create by Thanh Thien
 */
public class NoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        List<Note> notes = new ArrayList<>();
        recyclerView.setAdapter(new NoteAdapter(R.layout.item_note, notes));
        return v;
    }

}
