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
import vn.asiantech.internship.fragment.DetailNoteFragment;
import vn.asiantech.internship.fragment.MainNoteFragment;
import vn.asiantech.internship.interfaces.OnReplaceFragmentListener;
import vn.asiantech.internship.ui.adapter.NoteAdapter;

/**
 * Created by ducle on 19/06/2017.
 */
public class NoteActivity extends AppCompatActivity implements OnReplaceFragmentListener, NoteAdapter.OnClickItemNoteListener {
    public static final String folder = "imagenote";
    private MainNoteFragment mMainNoteFragment;
    private AddNoteFragment mAddNoteFragment;
    private DetailNoteFragment mDetailNoteFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.activity_note);
        mMainNoteFragment = new MainNoteFragment();
        mAddNoteFragment = new AddNoteFragment();
        mDetailNoteFragment = new DetailNoteFragment();
        switchFragment(mMainNoteFragment, false, R.id.flContain);
    }

    /**
     * switch a fragment to replace
     *
     * @param fragment       is fragment need show
     * @param addToBackStack is add to back stack or no
     * @param id             is place replace to
     */
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
        switchFragment(mAddNoteFragment, false, R.id.flContain);
    }

    @Override
    public void OnReplaceFragmentMain() {
        mMainNoteFragment.updateData();
        switchFragment(mMainNoteFragment, false, R.id.flContain);
    }

    @Override
    public void onItemClick(int positon) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", positon);
        mDetailNoteFragment.setArguments(bundle);
        switchFragment(mDetailNoteFragment, false, R.id.flContain);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.flContain);
        if (!(fragment instanceof MainNoteFragment)) {
            switchFragment(mMainNoteFragment, false, R.id.flContain);
        }
    }
}
