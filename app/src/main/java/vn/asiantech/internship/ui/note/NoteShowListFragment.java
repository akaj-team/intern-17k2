package vn.asiantech.internship.ui.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.NoteAdapter;
import vn.asiantech.internship.database.DatabaseHelper;

public class NoteShowListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_show_list, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(new NoteAdapter(databaseHelper.getAllNotes()));
        return v;
    }
}
