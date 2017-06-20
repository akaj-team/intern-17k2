package vn.asiantech.internship.ui.note;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.DatabaseHelper;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * A simple Note class.
 * Create by Thanh Thien
 */
public class NoteFragment extends Fragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView mImgAvatar;
    private EditText mEdtTitle;
    private EditText mEdtDescription;
    private DatabaseHelper mDatabaseHelper;
    private String mFilePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        initView(v);
        List<Note> notes = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(getContext());
        return v;
    }

    private void initView(View v) {
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ImageButton imgBtnOpenImages = (ImageButton) v.findViewById(R.id.imgBtnOpenImage);
        ImageButton imgBtnSave = (ImageButton) v.findViewById(R.id.imgBtnSave);
        mEdtTitle = (EditText) v.findViewById(R.id.edtTitle);
        mEdtDescription = (EditText) v.findViewById(R.id.edtDescription);
        mImgAvatar = (ImageView) v.findViewById(R.id.imgAvatar);

        if (getActivity() instanceof NoteActivity) {
            ((NoteActivity) getActivity()).setToolbar(mToolbar);
        }

        imgBtnOpenImages.setOnClickListener(this);
        imgBtnSave.setOnClickListener(this);
    }

    //TODO for next ex
    private String saveImageToSdCard(Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, new Date().toString() + ".thg");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        } catch (IOException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        }
        return file.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnOpenImage:
                openLibrary();
                break;

            case R.id.imgBtnSave:
                saveData();
        }
    }

    private void saveData() {
        Note note = new Note();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String writeDay = dateFormat.format(date) + " " + timeFormat.format(date);

        note.setNoteTile(mEdtTitle.getText().toString());
        note.setNoteDescription(mEdtDescription.getText().toString());
        note.setNoteDate(writeDay);
        note.setNoteImagesThumb(mFilePath);
        mDatabaseHelper.createNote(note);
        Toast.makeText(getContext(), writeDay, Toast.LENGTH_SHORT).show();
    }

    private void openLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MainActivity.KEY_LIBRARY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (data != null) {
            switch (requestCode) {
                case MainActivity.KEY_LIBRARY:
                    Uri uriSelectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(uriSelectedImage,
                            filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    bitmap = BitmapFactory.decodeFile(picturePath);
                    setImage(bitmap);
                    mFilePath = saveImageToSdCard(bitmap);
                    cursor.close();
                    break;
            }
        }
    }

    private void setImage(Bitmap bitmap) {
        mImgAvatar.setVisibility(View.VISIBLE);
        mImgAvatar.setImageBitmap(bitmap);
    }
}

