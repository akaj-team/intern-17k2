package vn.asiantech.internship.note.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.model.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private NoteDatabase mNoteDatabase;
    private OnChangeFragment mOnChangeFragment;

    /**
     * interface to change fragment
     */
    interface OnChangeFragment {
        void onChange(int key, int id);
    }

    public void setOnChangeFragment(OnChangeFragment onChangeFragment) {
        mOnChangeFragment = onChangeFragment;
    }

    public NoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_note, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycleViewNote);
        mToolbar = (Toolbar) layout.findViewById(R.id.toolBarNote);
        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
        initUI();
    }

    private void initUI() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.toolBar_title_screenlist);
        List<Note> mNotes = mNoteDatabase.getAllData();
        Log.e("Data_Size", "Size: " + mNotes.size());
        NoteAdapter mNoteAdapter = new NoteAdapter(mNotes, new NoteAdapter.OnClickItemNote() {
            @Override
            public void onClick(int id) {
                mOnChangeFragment.onChange(2, id);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mNoteAdapter);
        mNoteAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnAdd) {
            mOnChangeFragment.onChange(1, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }
}
