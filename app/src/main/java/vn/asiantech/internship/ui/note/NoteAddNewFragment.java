package vn.asiantech.internship.ui.note;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
    private NoteFragment mNoteFragment;

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
        ImageView imgOpenImages = (ImageView) v.findViewById(R.id.imgOpenImage);
        ImageView imgSave = (ImageView) v.findViewById(R.id.imgSave);
        mEdtTitle = (EditText) v.findViewById(R.id.edtTitle);
        mEdtDescription = (EditText) v.findViewById(R.id.edtDescription);
        mImgAvatar = (ImageView) v.findViewById(R.id.imgAvatar);
        mNoteFragment = new NoteFragment();

        if (getActivity() instanceof NoteActivity) {
            ((NoteActivity) getActivity()).setToolbar(toolbar);
        }

        imgOpenImages.setOnClickListener(this);
        imgSave.setOnClickListener(this);
    }

    private String saveImageToSdCard(Bitmap bitmap) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        String fileName = new Date().toString().replaceAll(" ", "");
        File file = new File(extStorageDirectory, fileName + ".thg");
        try {
            // Using when show full HD images
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (IOException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        }
        return file.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgOpenImage:
                openLibrary();
                break;

            case R.id.imgSave:
                saveData();
        }
    }

    private void saveData() {
        Note note = new Note();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM HH:mm", Locale.US);
        String writeDay = dateFormat.format(date);
        note.setTitle(mEdtTitle.getText().toString());
        note.setDescription(mEdtDescription.getText().toString());
        note.setDate(writeDay);
        if (mFilePath == null) {
            note.setImagesThumb("");
        } else {
            note.setImagesThumb(mFilePath);
        }
        mDatabaseHelper.createNote(note);
        mNoteFragment.replaceFragmentAddContent(getActivity(), new NoteShowListFragment(), false);
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
        if (data != null) {
            switch (requestCode) {
                case MainActivity.KEY_LIBRARY:
                    Uri uriSelectedImage = data.getData();

                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int dw = size.x;
                    int dh = size.y;
                    try {
                        // Load up the image's dimensions not the image itself
                        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                        bmpFactoryOptions.inJustDecodeBounds = true;
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
                        Bitmap bmp = BitmapFactory.decodeStream(getActivity().getContentResolver()
                                        .openInputStream(uriSelectedImage), null,
                                bmpFactoryOptions);

                        setImage(bmp);
                        mFilePath = saveImageToSdCard(bmp);
                    } catch (FileNotFoundException e) {
                        Log.v("ERROR", e.toString());
                    }
                    break;
            }
        }
    }

    private void setImage(Bitmap bitmap) {
        mImgAvatar.setVisibility(View.VISIBLE);
        mImgAvatar.setImageBitmap(bitmap);
    }
}
