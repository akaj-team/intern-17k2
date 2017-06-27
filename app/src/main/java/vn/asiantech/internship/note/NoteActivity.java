package vn.asiantech.internship.note;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 19-06-2017.
 */
public class NoteActivity extends AppCompatActivity implements OnReplaceFragmentListener, RecyclerViewNoteAdapter.OnClickItemNoteListener {
    public static final String FOLDER = "imagenote";
    private NoteFragment mMainNoteFragment;
    private DetailNoteFragment mDetailNoteFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 0);
        setContentView(R.layout.activity_note);
        mMainNoteFragment = new NoteFragment();
        mDetailNoteFragment = new DetailNoteFragment();
        switchFragment(mMainNoteFragment, false, R.id.fragmentNote);

    }

    public void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
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
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        switchFragment(addNoteFragment, false, R.id.fragmentNote);
    }

    @Override
    public void onReplaceFragmentMain() {
        mMainNoteFragment.updateData();
        switchFragment(mMainNoteFragment, false, R.id.fragmentNote);
    }

    @Override
    public void onItemClick(int positon) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", positon);
        mDetailNoteFragment.setArguments(bundle);
        switchFragment(mDetailNoteFragment, false, R.id.fragmentNote);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentNote);
        if (!(fragment instanceof NoteFragment)) {
            switchFragment(mMainNoteFragment, false, R.id.fragmentNote);
        }
    }
}
