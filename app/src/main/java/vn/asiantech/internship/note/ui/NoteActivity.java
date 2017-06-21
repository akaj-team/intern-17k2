package vn.asiantech.internship.note.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;


public class NoteActivity extends AppCompatActivity {

    public static final String KEY_ID = "keykey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frContainer, new NoteFragment(new NoteFragment.OnChangeFragment() {
            @Override
            public void onChange(int key, int id) {
                switch (key) {
                    case 1:
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frContainer, new NoteAddFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        FragmentTransaction frtransaction = getSupportFragmentManager().beginTransaction();
                        DetailFragment detailFragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(KEY_ID, id);
                        detailFragment.setArguments(bundle);
                        frtransaction.replace(R.id.frContainer, detailFragment);
                        frtransaction.addToBackStack(null);
                        frtransaction.commit();
                }
            }
        }));
        transaction.addToBackStack(null);
        transaction.commit();
        if (ContextCompat.checkSelfPermission(NoteActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NoteActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }
}
