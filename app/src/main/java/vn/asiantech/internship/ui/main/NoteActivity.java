package vn.asiantech.internship.ui.main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragment.AddNoteFragment;
import vn.asiantech.internship.fragment.MainNoteFragment;
import vn.asiantech.internship.interfaces.OnReplaceFragmentListener;

/**
 * Created by ducle on 19/06/2017.
 */

public class NoteActivity extends AppCompatActivity implements OnReplaceFragmentListener{
    public static final String folder="imagenote";
    private MainNoteFragment mMainNoteFragment;
    private AddNoteFragment mAddNoteFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.activity_note);
        mMainNoteFragment=new MainNoteFragment();
        mAddNoteFragment=new AddNoteFragment();
        switchFragment(mMainNoteFragment,false,R.id.flContain);
    }

    public void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        if (fragment.getTag() == null) {
            fragmentTransaction.replace(id, fragment, fragment.toString());
        } else {
            fragmentTransaction.replace(id, fragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onReplaceFragmentAdd() {
        switchFragment(mAddNoteFragment,false,R.id.flContain);
    }

    @Override
    public void OnReplaceFragmentMain() {
        mMainNoteFragment.updateData();
        switchFragment(mMainNoteFragment,false,R.id.flContain);
    }

}
