package vn.asiantech.internship.note.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.model.Note;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteAddFragment extends Fragment {

    private static final int REQUEST_GALLERY = 1234;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private ImageView mImageView;

    private Bitmap mBmpAttach;
    private NoteDatabase mNoteDatabase;

    public NoteAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_note, container, false);
        initView(layout);
        return layout;
    }

    private void initView(View view) {
        mEdtTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtContent = (EditText) view.findViewById(R.id.edtNoteContent);
        mImageView = (ImageView) view.findViewById(R.id.imgAddNote);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolBarAddNote);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolBar_title_screenadd);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAttachFile:
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_GALLERY);
                break;
            case R.id.mnSave:
                String dateTime = getCurrentDatetime();
                String imageName = convertStringDatetimeToFileName(dateTime) + ".png";
                if (!TextUtils.isEmpty(mEdtTitle.getText().toString())) {
                    if (mBmpAttach != null) {
                        mNoteDatabase.createData(new Note(
                                mEdtTitle.getText().toString(),
                                mEdtContent.getText().toString(),
                                saveImageToSDCard(mBmpAttach, "at-dinhvo"),
                                dateTime
                        ));
                    } else {
                        mNoteDatabase.createData(new Note(
                                mEdtTitle.getText().toString(),
                                mEdtContent.getText().toString(),
                                null,
                                dateTime
                        ));
                    }
                    getActivity().onBackPressed();
                } else {
                    Log.e("NoteAddFragment", "insert error!");
                }
                break;
            default:
                //todo something
        }
        return super.onOptionsItemSelected(item);
    }

    public static String getCurrentDatetime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            decreaseSizeImage(data.getData());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_create_note, menu);
    }

    public static String convertStringDatetimeToFileName(String date) {
        return date.replace(":", "").replace(" ", "").replace("-", "");
    }

    private void decreaseSizeImage(Uri imageFileUri) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dw = size.x;
        int dh = size.y;
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            mBmpAttach = BitmapFactory.decodeStream(
                    getContext().getContentResolver().openInputStream(imageFileUri),
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
            mBmpAttach = BitmapFactory.decodeStream(getContext().getContentResolver()
                            .openInputStream(imageFileUri), null,
                    bmpFactoryOptions);
            mImageView.setImageBitmap(mBmpAttach);
        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
        }
    }

    public static String saveImageToSDCard(Bitmap image, String folder) {
        String fullPath = Environment.getExternalStorageDirectory().getPath() + "/" + folder + "/";
        try {
            File folders = new File(fullPath);
            if (!folders.exists()) {
                folders.mkdirs();
            }
            OutputStream fOut = null;
            File file = File.createTempFile("img", ".png", folders);
            fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return file.getPath();
        } catch (IOException e) {
            Log.e("Error", "IOException");
            return null;
        }
    }
}
