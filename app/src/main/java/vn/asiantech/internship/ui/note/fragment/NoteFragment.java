package vn.asiantech.internship.ui.note.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.note.adapter.NoteAdapter;

/**
 * fragment show list note
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteFragment extends Fragment {
    public static final String KEY_DATA = "data";

    ImageView mImgNewNote;
    RecyclerView mRecyclerViewNote;
    private NoteAdapter mNoteAdapter;
    private List<Note> mNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        initAdapter();
        mImgNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new NewNoteFragment());
            }
        });
        return view;
    }

    private void getBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Note note = bundle.getParcelable(NewNoteFragment.KEY_SEND_NOTE);
            mNotes.add(note);
            mNoteAdapter.notifyDataSetChanged();
        }
    }

    private void replaceFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmContent, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    private void replaceFragment(Fragment fragment, Note note) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, note);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fmContent, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    private void initView(View view) {
        mImgNewNote = (ImageView) view.findViewById(R.id.imgNewNote);
        mRecyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewNote.setLayoutManager(linearLayoutManager);
        NoteDatabase noteDatabase = new NoteDatabase(getContext());
        noteDatabase.open();
        mNotes = noteDatabase.getAllNote();
        mNoteAdapter = new NoteAdapter(mNotes, new NoteAdapter.OnListener() {
            @Override
            public void onItemClick(int position) {
                replaceFragment(new NoteDetailFragment(), mNotes.get(position));
            }
        });
        getBundle();
        mRecyclerViewNote.setAdapter(mNoteAdapter);
    }
}
