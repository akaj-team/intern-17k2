package vn.asiantech.internship.note;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 19-06-2017.
 */
public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private ImageView mImgAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mToolbar = (Toolbar) findViewById(R.id.toolbarNote);
        mImgAdd = (ImageView) findViewById(R.id.imgAdd);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        NoteFragment noteFragment = new NoteFragment();
        transaction.replace(R.id.fragmentNote, noteFragment).commit();

        mImgAdd.setOnClickListener(this);
        // Toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAdd:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                NoteFragment noteFragment = new NoteFragment();
                transaction.replace(R.id.fragmentNote, noteFragment).commit();
                break;
        }
    }
}
