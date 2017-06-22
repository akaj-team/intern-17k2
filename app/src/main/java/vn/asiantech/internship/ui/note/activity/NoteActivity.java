package vn.asiantech.internship.ui.note.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.note.fragment.NoteFragment;

/**
 * activity contain NoteFragment
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        callFragment(new NoteFragment());
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fmContent, fragment);
        transaction.commit();
    }
}
