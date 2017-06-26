package vn.asiantech.internship.ui.note;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.NoteDatabase;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.adapters.NoteAdapter;

/**
 * Activity for Note
 * Created by huypham on 20/06/2017.
 */
public class NoteActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {
    public static final String NOTE_KEY = "noteKey";
    private static final int REQUEST_CODE_GALLERY = 1;

    private Toolbar mToolbar;
    private TextView mTvToolbarTitle;
    private Menu mMenu;
    private NoteDatabase mDatabase;
    private AddNoteFragment mAddNoteFragment = new AddNoteFragment();
    private HomeFragment mHomeFragment = new HomeFragment();
    private DetailNoteFragment mDetailNoteFragment = new DetailNoteFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mDatabase = new NoteDatabase(this);
        mDatabase.open();

        initViews();
        initToolbar();
        replaceFragment(mHomeFragment, true);
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mTvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flContainer, fragment);
        if (addToBackStack){
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            mTvToolbarTitle.setText(R.string.textview_toolbar_home);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_home, menu);
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                replaceFragment(mAddNoteFragment, true);
                mTvToolbarTitle.setText(R.string.textview_toolbar_add);
                showMenu(0);
                break;

            case R.id.menuAttach:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;

            case R.id.menuAddNote:
                if (TextUtils.isEmpty(mAddNoteFragment.getEditTextContent()) || TextUtils.isEmpty(mAddNoteFragment.getEditTextTitle())) {
                    Toast.makeText(this, "Input Content and Title please", Toast.LENGTH_SHORT).show();
                } else {
                    onBackPressed();
                    mTvToolbarTitle.setText(R.string.textview_toolbar_home);
                    mAddNoteFragment.addNote();
                    showMenu(1);
                }
                break;

            case R.id.menuDelete:
                mTvToolbarTitle.setText(R.string.textview_toolbar_home);
                mDetailNoteFragment = (DetailNoteFragment) getCurrentFragment();
                mDatabase.deleteItem(mDetailNoteFragment.getIdNote());
                showMenu(1);
                break;

            case R.id.menuEdit:
                mDetailNoteFragment = (DetailNoteFragment) getCurrentFragment();
                if (mDetailNoteFragment.editNote() > -1) {
                    mDetailNoteFragment.editNote();
                    mTvToolbarTitle.setText(R.string.textview_toolbar_home);
                    showMenu(1);
                } else {
                    Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMenu(int menuOptionId) {
        if (mMenu == null) {
            return;
        }
        if (menuOptionId == 0) {
            mMenu.findItem(R.id.menuAdd).setVisible(false);
            mMenu.findItem(R.id.menuAddNote).setVisible(true);
            mMenu.findItem(R.id.menuAttach).setVisible(true);
            mMenu.findItem(R.id.menuEdit).setVisible(false);
            mMenu.findItem(R.id.menuDelete).setVisible(false);
        } else if (menuOptionId == 1) {
            mMenu.findItem(R.id.menuAdd).setVisible(true);
            mMenu.findItem(R.id.menuAddNote).setVisible(false);
            mMenu.findItem(R.id.menuAttach).setVisible(false);
            mMenu.findItem(R.id.menuEdit).setVisible(false);
            mMenu.findItem(R.id.menuDelete).setVisible(false);
        } else if (menuOptionId == 2) {
            mMenu.findItem(R.id.menuAdd).setVisible(false);
            mMenu.findItem(R.id.menuAddNote).setVisible(false);
            mMenu.findItem(R.id.menuAttach).setVisible(true);
            mMenu.findItem(R.id.menuEdit).setVisible(true);
            mMenu.findItem(R.id.menuDelete).setVisible(true);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemAddNew = menu.findItem(R.id.menuAddNote);
        MenuItem menuItemAttach = menu.findItem(R.id.menuAttach);
        MenuItem menuItemEdit = menu.findItem(R.id.menuEdit);
        MenuItem menuItemDelete = menu.findItem(R.id.menuDelete);

        menuItemAddNew.setVisible(false);
        menuItemAttach.setVisible(false);
        menuItemEdit.setVisible(false);
        menuItemDelete.setVisible(false);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Bitmap bitmap = getBitmap(uri);
            Fragment fragment = getCurrentFragment();
            if (fragment instanceof AddNoteFragment) {
                mAddNoteFragment.addImage(bitmap);
            }
            if (fragment instanceof DetailNoteFragment) {
                mDetailNoteFragment.addImage(bitmap);
            }
        }
    }

    @Override
    public void onItemClick(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(NOTE_KEY, note);
        mDetailNoteFragment.setArguments(bundle);
        onBackPressed();
        showMenu(2);
        mTvToolbarTitle.setText(R.string.textview_toolbar_edit);
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flContainer);
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
            bmp = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri), null, bmpFactoryOptions);
            int heightRatio = (int) Math
                    .ceil(bmpFactoryOptions.outHeight / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) dw);
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri), null, bmpFactoryOptions);
            return bmp;
        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

}
