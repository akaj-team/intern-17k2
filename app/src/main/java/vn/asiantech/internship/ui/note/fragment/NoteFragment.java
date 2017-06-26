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
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.note.adapter.NoteAdapter;
import vn.asiantech.internship.databases.DatabaseHelper;

/**
 * fragment show list note
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteFragment extends Fragment {
    public static final String KEY_BUNDLE = "bundle";
    public static final int KEY_NEW_NOTE = 1;
    public static final int KEY_EDIT_NOTE = 2;

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
                replaceFragment(new NewNoteFragment(), KEY_NEW_NOTE);
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

    private void replaceFragment(Fragment fragment, int key) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_BUNDLE, key);
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
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        mNotes = databaseHelper.getAllNote();
        mNoteAdapter = new NoteAdapter(mNotes, new NoteAdapter.OnListener() {
            @Override
            public void onItemClick(int position) {
                replaceFragment(new NewNoteFragment(), KEY_EDIT_NOTE);
            }
        });
        getBundle();
        mRecyclerViewNote.setAdapter(mNoteAdapter);
    }
}
