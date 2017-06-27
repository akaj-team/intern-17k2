package vn.asiantech.internship.note;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 19-06-2017.
 */
public class NoteFragment extends Fragment {
    private Context mContext;
    private ImageView mImgAdd;
    private Toolbar mToolbarNote;
    private NoteDatabase mNoteDatabase;
    private AddNoteFragment mAddNoteFragment;
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
        initView(view);
        initToolbar();
        mAddNoteFragment = new AddNoteFragment();
        mNoteDatabase = new NoteDatabase(getContext());
        try {
            mNoteDatabase.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mItemNotse = mNoteDatabase.getList();
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        recyclerView.setLayoutManager(manager);
        // Our classic custom Adapter.
        mRecyclerViewNoteAdapter = new RecyclerViewNoteAdapter(mItemNotse);
        recyclerView.setAdapter(mRecyclerViewNoteAdapter);

        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnReplaceFragmentListener) getActivity()).onReplaceFragmentAdd();
            }
        });
        return view;
    }

    public void initView(View view) {
        mToolbarNote = (Toolbar) view.findViewById(R.id.toolbarNote);
        mImgAdd = (ImageView) view.findViewById(R.id.imgAdd);
    }

    public void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbarNote);
        ActionBar actionbar = activity.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }
    }

//    public void replaceFragment(Fragment fragment, boolean backStack) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragmentNote, fragment);
//        if (backStack) {
//            fragmentTransaction.addToBackStack(fragment.getTag());
//        }
//        fragmentTransaction.commit();
//    }

    public void updateData() {
        try {
            mNoteDatabase.open();
            mItemNotse.clear();
            mItemNotse.addAll(mNoteDatabase.getList());
            mRecyclerViewNoteAdapter.notifyDataSetChanged();
            mNoteDatabase.close();
        } catch (IOException e) {
            Log.d("tag1", "ERROR");
        }
    }
}
