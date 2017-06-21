package vn.asiantech.internship.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.adapter.NoteAdapter;
import vn.asiantech.internship.ui.fragments.AddNoteFragment;
import vn.asiantech.internship.ui.fragments.DetailNoteFragment;
import vn.asiantech.internship.ui.fragments.NoteFragment;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/20/2017
 */
public class NoteActivity extends AppCompatActivity implements View.OnClickListener, NoteAdapter.OnItemClickListener {

    public static final String KEY_NOTE = "note";

    private static final int REQUEST_CODE_GALLERY = 222;

    private TextView mTvTitle;
    private ImageView mImgAddNote;
    private ImageView mImgAddImage;
    private ImageView mImgSave;
    private ImageView mImgEdit;
    private ImageView mImgDelete;

    private NoteFragment mNoteFragment;
    private AddNoteFragment mAddNoteFragment;
    private NoteDatabase mNoteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        initToolbar();
        initView();

        mNoteDatabase = new NoteDatabase(this);
        mNoteDatabase.open();

        mNoteFragment = new NoteFragment();
        mAddNoteFragment = new AddNoteFragment();
        replaceFragment(mNoteFragment, false);
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
            mTvTitle.setText(getString(R.string.title_add));
            mImgAddNote.setVisibility(View.GONE);
            mImgSave.setVisibility(View.VISIBLE);
            mImgAddImage.setVisibility(View.VISIBLE);
            mImgDelete.setVisibility(View.GONE);
            mImgEdit.setVisibility(View.GONE);
            return;
        }
        if (fragment instanceof NoteFragment) {
            mTvTitle.setText(getString(R.string.title_note));
            mImgAddNote.setVisibility(View.VISIBLE);
            mImgSave.setVisibility(View.GONE);
            mImgAddImage.setVisibility(View.GONE);
            mImgDelete.setVisibility(View.GONE);
            mImgEdit.setVisibility(View.GONE);
            return;
        }
        if (fragment instanceof DetailNoteFragment) {
            mTvTitle.setText(getString(R.string.title_detail));
            mImgEdit.setVisibility(View.VISIBLE);
            mImgDelete.setVisibility(View.VISIBLE);
            mImgAddImage.setVisibility(View.GONE);
            mImgAddNote.setVisibility(View.GONE);
            mImgSave.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mImgAddImage = (ImageView) findViewById(R.id.imgAddImage);
        mImgAddNote = (ImageView) findViewById(R.id.imgAddNote);
        mImgSave = (ImageView) findViewById(R.id.imgSave);
        mImgEdit = (ImageView) findViewById(R.id.imgEdit);
        mImgDelete = (ImageView) findViewById(R.id.imgDelete);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);

        mImgAddNote.setOnClickListener(this);
        mImgAddImage.setOnClickListener(this);
        mImgSave.setOnClickListener(this);
        mImgEdit.setOnClickListener(this);
        mImgDelete.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateToolbar(getCurrentFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddNote:
                replaceFragment(mAddNoteFragment, true);
                break;
            case R.id.imgSave:
                Fragment fragment = getCurrentFragment();
                if (fragment instanceof AddNoteFragment) {
                    mAddNoteFragment.addNote();
                    replaceFragment(mNoteFragment, true);
                    break;
                }
                if (fragment instanceof DetailNoteFragment) {
                    if (((DetailNoteFragment) fragment).editNote() > -1) {
                        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
                        replaceFragment(mNoteFragment, true);
                    } else {
                        Toast.makeText(this, getString(R.string.fail), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            case R.id.imgAddImage:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;
            case R.id.imgDelete:
                DetailNoteFragment detailNoteFragment = (DetailNoteFragment) getCurrentFragment();
                mNoteDatabase.deleteById(detailNoteFragment.getNoteId());
                replaceFragment(mNoteFragment, true);
                break;
            case R.id.imgEdit:
                DetailNoteFragment detail = (DetailNoteFragment) getCurrentFragment();
                detail.prepareEditNote();
                mImgAddImage.setVisibility(View.VISIBLE);
                mImgSave.setVisibility(View.VISIBLE);
                mImgEdit.setVisibility(View.GONE);
                mTvTitle.setText(getString(R.string.title_edit));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String filePath = saveImage(uri);
            Fragment fragment = getCurrentFragment();
            if (fragment instanceof AddNoteFragment) {
                mAddNoteFragment.addImage(filePath);
                return;
            }
            if (fragment instanceof DetailNoteFragment) {
                ((DetailNoteFragment) fragment).addImage(filePath);
            }
        }
    }

    @Override
    public void OnItemClick(NoteItem noteItem) {
        DetailNoteFragment detailNoteFragment = new DetailNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_NOTE, noteItem);
        detailNoteFragment.setArguments(bundle);
        replaceFragment(detailNoteFragment, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flFragmentContent);
    }

    public String saveImage(Uri uri) {
        String targetFolderPath = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "MyImage";
        OutputStream outputStream;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            File targetFolder = new File(targetFolderPath);
            if (!targetFolder.mkdirs()) {
                return "";
            }
            File targetFile = File.createTempFile("img", ".png", targetFolder);
            outputStream = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            return targetFile.getPath();
        } catch (IOException x) {
            Log.i("tag11", x.getMessage());
        }
        return "";
    }
}
