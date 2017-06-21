package vn.asiantech.internship.note.ui;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private String mPathImage = "";

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
        mNoteDatabase = NoteDatabase.getInstantDatabase(getContext());
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
                                saveImageToSDCard(mBmpAttach, "at-dinhvo", imageName),
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
            /*Uri imageUri = data.getData();
            try {
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mImageView.setImageBitmap(selectedImage);
                Uri uri = data.getData();
                mPathImage = getRealPathFromURI(getContext(), uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                BufferedInputStream bufferedInputStream = null;
                if (inputStream != null) {
                    bufferedInputStream = new BufferedInputStream(inputStream);
                }
                mBmpAttach = BitmapFactory.decodeStream(bufferedInputStream);
                mImageView.setImageBitmap(mBmpAttach);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_create_note, menu);
    }

    public static Bitmap readImage(String folder, String filename, Context context) {
        Bitmap img = null;
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/" + filename;
        try {
            img = BitmapFactory.decodeFile(fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File myFile = context.getFileStreamPath(filename);
            FileInputStream fIn = new FileInputStream(myFile);
            img = BitmapFactory.decodeStream(fIn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String convertStringDatetimeToFileName(String date) {
        return date.toString().replace(":", "").replace(" ", "").replace("-", "");
    }

    public static String saveImageToSDCard(Bitmap image, String folder, String name) {
        String fullPath = Environment.getExternalStorageDirectory().getPath() + "/" + folder + "/";
        try {
            File folders = new File(fullPath);
            if (!folders.exists()) {
                folders.mkdirs();
            }

            OutputStream fOut = null;
            File file = File.createTempFile("img",".png",folders);
            fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Log.e("grrrrrrrrrrr", fullPath);
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
