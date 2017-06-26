package vn.asiantech.internship.day11.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.ui.fragment.NoteFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 19/06/2017.
 */
public class NoteActivity extends AppCompatActivity {
    public static final String TYPE_NOTE="Note Fragment";
    private NoteFragment mNoteFragment = new NoteFragment();
    FragmentTransaction mTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        changeFragment(mNoteFragment);
    }

    public void changeFragment(Fragment fragment) {
        String tagFragment=fragment.getClass().getName();
        if(fragment instanceof NoteFragment){
            tagFragment=TYPE_NOTE;
        }
        FragmentManager manager = getSupportFragmentManager();
        mTransaction = manager.beginTransaction();
        mTransaction.replace(R.id.flNote, fragment,tagFragment);
        mTransaction.addToBackStack(tagFragment);
        mTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager=getSupportFragmentManager();
        if( fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).equals(fragmentManager.findFragmentByTag(TYPE_NOTE))){
            finish();
        }
    }
}
