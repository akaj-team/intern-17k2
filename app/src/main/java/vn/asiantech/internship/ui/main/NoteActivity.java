package vn.asiantech.internship.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.List;

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
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.flFragmentContent, fragment);
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
                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Fragment fragment = getCurrentFragment();
                if (fragment instanceof AddNoteFragment) {
                    if (mAddNoteFragment.addNote() > -1) {
                        onBackPressed();
                    }
                    break;
                }
                if (fragment instanceof DetailNoteFragment) {
                    if (((DetailNoteFragment) fragment).editNote() > -1) {
                        onBackPressed();
                        replaceFragment(mNoteFragment, false);
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
                if (mNoteDatabase.deleteById(detailNoteFragment.getNoteId()) > -1) {
                    Toast.makeText(NoteActivity.this, getString(R.string.success), Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Toast.makeText(NoteActivity.this, getString(R.string.fail), Toast.LENGTH_LONG).show();
                }
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
            Bitmap bitmap = getBitmap(uri);
            Fragment fragment = getCurrentFragment();
            if (bitmap != null) {
                if (fragment instanceof AddNoteFragment) {
                    mAddNoteFragment.addImage(bitmap);
                    return;
                }
                if (fragment instanceof DetailNoteFragment) {
                    ((DetailNoteFragment) fragment).addImage(bitmap);
                }
            }
        }
    }

    @Override
    public void onItemClick(NoteItem noteItem) {
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

    public List<NoteItem> getNotes() {
        return mNoteDatabase.getNoteList();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flFragmentContent);
    }

    private Bitmap getBitmap(Uri uri) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dw = size.x;
        int dh = size.y;
        try {
            // Load up the image's dimensions not the image itself
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            Bitmap bmp;
            bmp = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(uri),
                    null, bmpFactoryOptions);
            int heightRatio = (int) Math
                    .ceil(bmpFactoryOptions.outHeight / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                    / (float) dw);
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(getContentResolver()
                            .openInputStream(uri), null,
                    bmpFactoryOptions);
            return bmp;
        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
        }
        return null;
    }
}
