package vn.asiantech.internship.note.ui;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

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

    private NoteDatabase mNoteDatabase;
    private Calendar mCalendar;
    private String mPathImage = "";

    public NoteAddFragment() {
        mCalendar = Calendar.getInstance();
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
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolBarAddNote);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("AddNote");
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
                String date = String.valueOf(mCalendar.get(Calendar.DATE) + " " + mCalendar.get(Calendar.MONTH));
                String time = mCalendar.get(Calendar.HOUR) + " : " + mCalendar.get(Calendar.MINUTE);
                if (!TextUtils.isEmpty(mEdtTitle.getText().toString())) {
                    mNoteDatabase.createData(new Note(
                            mEdtTitle.getText().toString(),
                            mEdtContent.getText().toString(),
                            mPathImage,
                            date,
                            time
                    ));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mImageView.setImageBitmap(selectedImage);
                Uri uri = data.getData();
                mPathImage = getRealPathFromURI(getContext(), uri);
//                mPathImage = Environment.getExternalStorageDirectory().toString();
                Log.e("Grzzzzzzzzzz",  mPathImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        /*Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }*/
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
}
