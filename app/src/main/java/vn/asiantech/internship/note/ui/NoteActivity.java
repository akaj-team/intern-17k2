package vn.asiantech.internship.note.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.ui.NoteFragment.OnChangeViewListener;

/**
 * NoteActivity
 */
public class NoteActivity extends AppCompatActivity {

    public static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NoteFragment noteFragment = new NoteFragment();
        OnChangeViewListener onChangeViewListener = new OnChangeViewListener() {
            @Override
            public void onChangeFragment(int key, int id) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (key) {
                    case 1:
                        fragmentTransaction.replace(R.id.frContainer, new NoteAddFragment());
                        break;
                    case 2:
                        DetailFragment detailFragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(KEY_ID, id);
                        detailFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.frContainer, detailFragment);
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
        noteFragment.setOnChangeFragment(onChangeViewListener);
        transaction.replace(R.id.frContainer, noteFragment);
        transaction.commit();
        if (ContextCompat.checkSelfPermission(NoteActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NoteActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }
}
