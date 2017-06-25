package vn.asiantech.internship.note;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.R.id.imgSave;

/**
 * Created by datbu on 19-06-2017.
 */
public class NoteActivity extends AppCompatActivity {
    private Uri mUri;
    private ImageView mImgAdd;
    private ImageView mImgSave;
    private NoteFragment mNoteFragment;
    private AddNoteFragment mAddNoteFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mImgAdd = (ImageView) findViewById(R.id.imgAdd);
        mImgSave = (ImageView) findViewById(imgSave);

        mNoteFragment = new NoteFragment();
        mAddNoteFragment = new AddNoteFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NoteFragment noteFragment = new NoteFragment();
        transaction.replace(R.id.fragmentNote, noteFragment).commit();
        replaceFragment(mNoteFragment, false);
    }

    public void replaceFragment(Fragment fragment, boolean backStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentNote, fragment);
        if (backStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }
}
