package vn.asiantech.internship.note;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 19-06-2017.
 */
public class NoteFragment extends Fragment {
    private Context mContext;
    private AddNoteFragment mAddNoteFragment;
    List<ItemNote> mItemNote = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        Toolbar toolbarNote = (Toolbar) view.findViewById(R.id.toolbarNote);
        ImageView imgAdd = (ImageView) view.findViewById(R.id.imgAdd);
        mAddNoteFragment = new AddNoteFragment();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbarNote);
        ActionBar actionbar = activity.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }

        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        mItemNote = databaseHandler.getAllContacts();

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        recyclerView.setLayoutManager(manager);
        // Our classic custom Adapter.
        RecyclerViewNoteAdapter adapter = new RecyclerViewNoteAdapter(mContext, mItemNote);
        recyclerView.setAdapter(adapter);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(mAddNoteFragment, true);
            }
        });
        return view;
    }

    public void replaceFragment(Fragment fragment, boolean backStack) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentNote, fragment);
        if (backStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }
}
