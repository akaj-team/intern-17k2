package vn.asiantech.internship.notesqlite;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Use to contain and show note fragment and add fragment
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
class NoteActivity extends AppCompatActivity implements NoteFragment.OnDataPass {
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mFragmentManager = getFragmentManager();
        replaceNoteFragment();
    }

    public void replaceAddNoteFragment() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        FragmentTransaction addNoteFragmentTransaction = mFragmentManager.beginTransaction();
        addNoteFragmentTransaction.replace(R.id.flContainer, addNoteFragment);
        addNoteFragmentTransaction.commit();
    }

    public void replaceNoteFragment() {
        NoteFragment noteFragment = new NoteFragment();
        FragmentTransaction noteFragmentTransaction = mFragmentManager.beginTransaction();
        noteFragmentTransaction.replace(R.id.flContainer, noteFragment);
        noteFragmentTransaction.commit();
    }

    @Override
    public void onDataPass(Note note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        FragmentTransaction noteFragmentTransaction = mFragmentManager.beginTransaction();
        noteFragmentTransaction.replace(R.id.flContainer, editNoteFragment);
        noteFragmentTransaction.commit();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Note", note);
        editNoteFragment.setArguments(bundle);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentById(R.id.flContainer) instanceof NoteFragment) {
            finish();
        } else {
            replaceNoteFragment();
        }
    }
}
