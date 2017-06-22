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
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.DatabaseHelper;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Note Add new Fragment
 * Create by Thanh Thien
 */
public class NoteAddNewFragment extends Fragment implements View.OnClickListener {

    private ImageView mImgAvatar;
    private EditText mEdtTitle;
    private EditText mEdtDescription;

    private DatabaseHelper mDatabaseHelper;
    private String mFilePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_note, container, false);
        initView(v);
        mDatabaseHelper = new DatabaseHelper(getContext());
        return v;
    }

    private void initView(View v) {
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ImageButton imgBtnOpenImages = (ImageButton) v.findViewById(R.id.imgBtnOpenImage);
        ImageButton imgBtnSave = (ImageButton) v.findViewById(R.id.imgBtnSave);
        mEdtTitle = (EditText) v.findViewById(R.id.edtTitle);
        mEdtDescription = (EditText) v.findViewById(R.id.edtDescription);
        mImgAvatar = (ImageView) v.findViewById(R.id.imgAvatar);

        if (getActivity() instanceof NoteActivity) {
            ((NoteActivity) getActivity()).setToolbar(toolbar);
        }

        imgBtnOpenImages.setOnClickListener(this);
        imgBtnSave.setOnClickListener(this);
    }

    private String saveImageToSdCard(Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        String fileName = new Date().toString().replaceAll(" ", "");
        File file = new File(extStorageDirectory, fileName + ".thg");
        File fileThumb = new File(extStorageDirectory, fileName + ".thg.thumb");
        try {
            // Using when show full HD images
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

            // Save thumb using to set image thumb
            FileOutputStream outStreamThumb = new FileOutputStream(fileThumb);
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, outStreamThumb);
            outStreamThumb.flush();
            outStreamThumb.close();
        } catch (IOException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        }
        return fileThumb.toString();
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM HH:mm", Locale.US);
        String writeDay = dateFormat.format(date);
        note.setNoteTile(mEdtTitle.getText().toString());
        note.setNoteDescription(mEdtDescription.getText().toString());
        note.setNoteDate(writeDay);
        if (mFilePath == null) {
            note.setNoteImagesThumb("");
        } else {
            note.setNoteImagesThumb(mFilePath);
        }
        mDatabaseHelper.createNote(note);
        NoteFragment.replaceFragmentAddContent(getActivity(), new NoteShowListFragment());
        Toast.makeText(getContext(), getString(R.string.complete), Toast.LENGTH_SHORT).show();
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
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(uriSelectedImage,
                            filePathColumns, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
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
