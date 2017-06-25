package vn.asiantech.internship.note;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.asiantech.internship.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by datbu on 20-06-2017.
 */
public class AddNoteFragment extends Fragment implements View.OnClickListener {
    public static final int REQUEST_CODE_GALLERY = 22;
    public static final String FOLDER = "imagenote";
    private String mImagePath = null;
    private EditText mEdtTitle;
    private EditText mEdtNote;
    private boolean mIsBitmap = false;
    private ImageView mImgSave;
    private ImageView mImgEdit;
    private ImageView mImgPhoto;
    private Bitmap mBitmap;
    private Toolbar mToolbarAddNote;
    private NoteFragment mNoteFragment;
    private NoteDatabase mNoteDatabase;

    private ItemNote mItemNote;
    private List<ItemNote> mItemNotes = new ArrayList<>();

    public AddNoteFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);
        initView(view);
        initToolbar();
//        mNoteFragment = new NoteFragment();
//        mDatabaseHandler = new DatabaseHandler(getContext());
//        mItemNotes = mDatabaseHandler.getAllContacts();

//        mImgSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        mImgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_CODE_GALLERY);
//            }
//        });
        mImgSave.setOnClickListener(this);
        mImgEdit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSave:
                addNote();
                break;
            case R.id.imgEdit:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                break;
        }

    }

    public void initView(View view) {
        mToolbarAddNote = (Toolbar) view.findViewById(R.id.toolbarAddNote);
        mImgSave = (ImageView) view.findViewById(R.id.imgSave);
        mImgEdit = (ImageView) view.findViewById(R.id.imgEdit);
        mImgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        mEdtNote = (EditText) view.findViewById(R.id.edtNote);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
    }

    public void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbarAddNote);
        ActionBar actionbar = activity.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mBitmap = getBitmap(uri);
            if (mBitmap != null) {
                mImgPhoto.setImageBitmap(mBitmap);
                Log.d("tag", "onActivityResult: " + mBitmap);
            }
//                if (fragment instanceof DetailNoteFragment) {
//                    ((DetailNoteFragment) fragment).addImage(bitmap);
//                }
        }
    }

    String getStringTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMM dd HH:mm:ss", Locale.ENGLISH);
        String[] arr = simpleDateFormat.format(Calendar.getInstance().getTime()).split(" ");
        Log.i("tag11", simpleDateFormat.format(Calendar.getInstance().getTime()));
        return arr[0] + "\n" + arr[1] + " " + arr[2] + "\n" + arr[3];
    }

    public void replaceFragment(Fragment fragment, boolean backStack) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentNote, fragment);
        if (backStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    public void addNote() {
        if (TextUtils.isEmpty(mEdtNote.getText()) || TextUtils.isEmpty(mEdtTitle.getText())) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        } else {
            String time = getStringTime();
            mItemNote.setTime(time);
            if (mBitmap != null) {
                saveImageToSDCard(mBitmap, FOLDER, getName(time) + ".png");
                String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + FOLDER + "/";
                mItemNote.setImage(fullPath + getName(time) + ".png");
                Log.i("tag11", fullPath + getName(time) + ".png");
            }
            mItemNote.setTitle(mEdtTitle.getText().toString());
            mItemNote.setNote(mEdtNote.getText().toString());
            try {
                mNoteDatabase.open();
                mNoteDatabase.addNote(mItemNote);
                mNoteDatabase.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mNoteDatabase.addNote(mItemNote) > 0) {
                replaceFragment(mNoteFragment, false);
                Toast.makeText(getContext(), "sussces", Toast.LENGTH_SHORT).show();
                mEdtNote.setText("");
                mEdtTitle.setText("");
            } else {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getName(String date) {
        return date.replace("\n", "").replace(" ", "");
    }

    private static boolean saveImageToSDCard(Bitmap image, String folder, String name) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";
        Log.i("tag11", name);
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            OutputStream fOut = null;
            File file = new File(fullPath, name);
            if (!file.exists()) {
                file.createNewFile();
            }

            fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            Log.d("tag1111", "ERROR3");
            return false;
        }
    }

    public static String saveImage(Bitmap bitmap) {
        String targetFolderPath = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "MyImage";
        OutputStream outputStream;
        try {
            File targetFolder = new File(targetFolderPath);
            targetFolder.mkdirs();
            File targetFile = File.createTempFile("img", ".png", targetFolder);
            outputStream = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            return targetFile.getPath();
        } catch (IOException x) {
            Log.i("tag11", x.getMessage());
        }
        return null;
    }

    private Bitmap getBitmap(Uri uri) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
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
                    getActivity().getContentResolver().openInputStream(uri),
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
            bmp = BitmapFactory.decodeStream(getActivity().getContentResolver()
                            .openInputStream(uri), null,
                    bmpFactoryOptions);
            return bmp;
        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
        }
        return null;
    }
}
