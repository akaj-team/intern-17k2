package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.fragments.AddNoteFragment;
import vn.asiantech.internship.ui.fragments.NoteFragment;

/**
 * Created by PC on 6/19/2017.
 */

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mImgAddNote;
    private ImageView mImgAddImage;
    private ImageView mImgSave;
    private NoteFragment mNoteFragment;
    private AddNoteFragment mAddNoteFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initToolbar();
        initView();
        mNoteFragment = new NoteFragment();
        mAddNoteFragment = new AddNoteFragment();
        replaceFragment(mNoteFragment, true);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        updateToolbar(fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragmentContent, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarNote);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void updateToolbar(Fragment fragment) {
        if (fragment instanceof AddNoteFragment) {
            mImgAddNote.setVisibility(View.GONE);
            mImgSave.setVisibility(View.VISIBLE);
            mImgAddImage.setVisibility(View.VISIBLE);
            return;
        }
        if (fragment instanceof NoteFragment) {
            mImgAddNote.setVisibility(View.VISIBLE);
            mImgSave.setVisibility(View.GONE);
            mImgAddImage.setVisibility(View.GONE);
            return;
        }
    }

    private void initView() {
        mImgAddImage = (ImageView) findViewById(R.id.imgAddImage);
        mImgAddNote = (ImageView) findViewById(R.id.imgAddNote);
        mImgSave = (ImageView) findViewById(R.id.imgSave);

        mImgAddNote.setOnClickListener(this);
        mImgAddImage.setOnClickListener(this);
        mImgSave.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateToolbar(getSupportFragmentManager().findFragmentById(R.id.flFragmentContent));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddNote:
                replaceFragment(mAddNoteFragment, true);
                break;
            case R.id.imgSave:
                mAddNoteFragment.addNote();
                break;
        }
    }
}
