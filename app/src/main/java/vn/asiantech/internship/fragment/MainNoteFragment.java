package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDataBase;
import vn.asiantech.internship.interfaces.OnReplaceFragmentListener;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.adapter.NoteAdapter;

/**
 * Created by ducle on 20/06/2017.
 */
public class MainNoteFragment extends Fragment {
    private NoteDataBase mNoteDataBase;
    private RecyclerView mRecyclerViewNote;
    private List<Note> mNotes;
    private NoteAdapter mNoteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_main_note, container, false);
        mRecyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        mNoteDataBase = new NoteDataBase(container.getContext());
        try {
            mNoteDataBase.open();
            mNotes = mNoteDataBase.getList();
            Log.i("TAG111", mNotes.size() + "");
            mNoteDataBase.close();
        } catch (IOException e) {
            Log.d("tag", "ERROR");
        }
        mNoteAdapter = new NoteAdapter(mNotes);
        mRecyclerViewNote.setAdapter(mNoteAdapter);
        mRecyclerViewNote.setLayoutManager(new LinearLayoutManager(container.getContext()));
        ImageView imgAdd = (ImageView) view.findViewById(R.id.imgAdd);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnReplaceFragmentListener) getActivity()).onReplaceFragmentAdd();
            }
        });
        return view;
    }

    /**
     * update list note after add note or delete note
     */
    public void updateData() {
        try {
            mNoteDataBase.open();
            mNotes.clear();
            mNotes.addAll(mNoteDataBase.getList());
            Log.i("TAG11", mNotes.size() + "");
            mNoteAdapter.notifyDataSetChanged();
            mNoteDataBase.close();
        } catch (IOException e) {
            Log.d("tag1", "ERROR");
        }
    }
}
