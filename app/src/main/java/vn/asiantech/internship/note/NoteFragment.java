package vn.asiantech.internship.note;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 19-06-2017.
 */
public class NoteFragment extends Fragment {
    private NoteDatabase mNoteDatabase;
    private RecyclerViewNoteAdapter mRecyclerViewNoteAdapter;
    private List<ItemNote> mItemNotse;
    private RecyclerViewNoteAdapter.OnClickItemNoteListener mOnClickItemNoteListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
        mItemNotse = mNoteDatabase.getList();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        recyclerView.setLayoutManager(manager);
        // Our classic custom Adapter.
        mRecyclerViewNoteAdapter = new RecyclerViewNoteAdapter(mItemNotse, mOnClickItemNoteListener);
        recyclerView.setAdapter(mRecyclerViewNoteAdapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnClickItemNoteListener = (RecyclerViewNoteAdapter.OnClickItemNoteListener) activity;
    }
}
