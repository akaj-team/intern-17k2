package vn.asiantech.internship.notesqlite;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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
public class NoteFragment extends Fragment implements View.OnKeyListener {
    private final List<Note> mNotes = new ArrayList<>();
    private OnDataPass mDataPasser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteSqlite database = new NoteSqlite(getActivity());
        database.open();
        mNotes.addAll(database.getNotes());
        database.close();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        mDataPasser = (OnDataPass) getActivity();
        ImageView imgAdd = (ImageView) view.findViewById(R.id.imgAddNote);
        RecyclerView recyclerViewNote = (RecyclerView) view.findViewById(R.id.recyclerViewNote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewNote.setLayoutManager(linearLayoutManager);
        mDataPasser = (OnDataPass) getActivity();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NoteActivity) getActivity()).replaceAddNoteFragment();
            }
        });

        recyclerViewNote.setAdapter(new NoteAdapter(mNotes, new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                passData(note);
            }

        }));
        return view;
    }

    public void passData(Note note) {
        mDataPasser.onDataPass(note);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if( i == KeyEvent.KEYCODE_BACK ) {
//           getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            android.os.Process.killProcess(android.os.Process.myPid());
            return true;
        } else {
            return false;
        }
    }

    interface OnDataPass {
        void onDataPass(Note note);
    }
}
