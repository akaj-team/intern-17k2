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
public class NoteActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mFragmentManager = getFragmentManager();
        showList();
    }

    public void showAdd() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        FragmentTransaction addNoteFragmentTransaction = mFragmentManager.beginTransaction();
        addNoteFragmentTransaction.replace(R.id.flContainer, addNoteFragment);
        addNoteFragmentTransaction.commit();
    }

    public void showList() {
        NoteFragment noteFragment = new NoteFragment();
        FragmentTransaction noteFragmentTransaction = mFragmentManager.beginTransaction();
        noteFragmentTransaction.replace(R.id.flContainer, noteFragment);
        noteFragmentTransaction.commit();
    }
}
