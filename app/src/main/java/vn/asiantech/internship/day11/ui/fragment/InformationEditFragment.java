package vn.asiantech.internship.day11.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.database.NoteModify;
import vn.asiantech.internship.day11.model.Note;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 19/06/2017.
 */
public class InformationEditFragment extends Fragment {
    private static final int TYPE_GALLERY = 0;

    private EditText mEdtTitle;
    private EditText mEdtDescription;
    private ImageView mImgNote;
    private ImageView mImgSave;
    private ImageView mImgPhoto;
    private Uri mSaveUriImage;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_information, container, false);
        initView(v);
        mImgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, TYPE_GALLERY);
            }
        });
        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note;
                if (TextUtils.isEmpty(mEdtTitle.getText().toString())) {
                    Toast.makeText(getContext(), "input title", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(mEdtDescription.getText().toString())) {
                    Toast.makeText(getContext(), "input description", Toast.LENGTH_LONG).show();
                } else {
                    NoteModify noteModify = new NoteModify(getContext());
                    Date date = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd hh:mm:ss ", Locale.getDefault());
                    if (mSaveUriImage == null) {
                        note = new Note(0, mEdtTitle.getText().toString(), mEdtDescription.getText().toString(), "", ft.format(date));
                    } else {
                        note = new Note(0, mEdtTitle.getText().toString(), mEdtDescription.getText().toString(), mSaveUriImage.toString(), ft.format(date));
                    }
                    noteModify.insert(note);
                    if (getActivity() instanceof NoteActivity) {
                        ((NoteActivity) getActivity()).changeFragment(new NoteFragment());
                    }
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == TYPE_GALLERY) {
            Uri selectedImage = data.getData();
            convertBitmapToFile(designImage(selectedImage));
            mImgNote.setImageURI(mSaveUriImage);
            mImgNote.setVisibility(View.VISIBLE);

        }
    }

    public void initView(View v) {
        mEdtTitle = (EditText) v.findViewById(R.id.edtTitle);
        mEdtDescription = (EditText) v.findViewById(R.id.edtDescription);
        mImgNote = (ImageView) v.findViewById(R.id.imgEditInformation);
        mImgSave = (ImageView) v.findViewById(R.id.imgSave);
        mImgPhoto = (ImageView) v.findViewById(R.id.imgPhoto);
    }

    public void convertBitmapToFile(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// Check if device mount with externalStorage
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path, "ImageNote"); // Create folder with absolute dir and filename
            boolean isDirectoryCreated = file.exists();
            if (!isDirectoryCreated) {
                isDirectoryCreated = file.mkdir();
            }
            if (isDirectoryCreated) {
                File f = new File(file, "Image" + setFileName() + ".png");
                boolean isfileCreated = f.exists();
                if (!isfileCreated) {
                    try {
                        isfileCreated = f.createNewFile();
                    } catch (IOException e) {
                        Log.d("Exception", "IoException");
                    }
                }
                if (isfileCreated) {
                    try {
                        OutputStream os = new FileOutputStream(f);  // Create outputstream to write file
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os); // Compress bitmap to PNG
                        os.flush();
                        os.close();
                    } catch (IOException e) {
                        Log.v("Exception", "IOException");
                    } catch (NullPointerException e) {
                        Log.v("Error", "NullFileException");
                    }
                    mSaveUriImage = Uri.parse(f.getAbsolutePath());
                    Toast.makeText(getContext(), "uri:  " + mSaveUriImage, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private String setFileName() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss ", Locale.getDefault());
        String name = simpleDateFormat.format(date).trim();
        return name.replace(":", "").replace(" ", "").replace(".", "");
    }

    private Bitmap designImage(Uri uri) {
        if (getActivity() instanceof NoteActivity) {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int dw = size.x;
            int dh = size.y;
            try {
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                Bitmap bmp;
                int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) dh);
                int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) dw);
                if (heightRatio > 1 && widthRatio > 1) {
                    if (heightRatio > widthRatio) {
                        bmpFactoryOptions.inSampleSize = heightRatio;
                    } else {
                        bmpFactoryOptions.inSampleSize = widthRatio;
                    }
                }
                bmpFactoryOptions.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri), null, bmpFactoryOptions);
                return bmp;
            } catch (FileNotFoundException e) {
                Log.v("ERROR", e.toString());
            }
        }
        return null;
    }
}
