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
    private String mImagePath = null;
    private EditText mEdtTitle;
    private EditText mEdtNote;
    private boolean mIsBitmap = false;
    private ImageView mImgSave;
    private ImageView mImgPick;
    private ImageView mImgPhoto;
    private Bitmap mBitmap;
    private Toolbar mToolbarAddNote;
    private NoteFragment mNoteFragment;
    private NoteDatabase mNoteDatabase;

    private ItemNote mItemNote;
    private List<ItemNote> mItemNotes = new ArrayList<>();

    public static final String FOLDER = "imagenote";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_addnote, container, false);
        mNoteDatabase = new NoteDatabase(getContext());
        initViews(view);
        mImgPick.setOnClickListener(this);
        mImgSave.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mImgPick = (ImageView) view.findViewById(R.id.imgPick);
        mImgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        mImgSave = (ImageView) view.findViewById(R.id.imgSave);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mEdtNote = (EditText) view.findViewById(R.id.edtNote);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPick:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_GALLERY);
                break;
            case R.id.imgSave:
                addNote(v);
                break;
        }
    }

    public void addNote(View v) {
        if (TextUtils.isEmpty(mEdtNote.getText()) || TextUtils.isEmpty(mEdtTitle.getText())) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        } else {
            mItemNote = new ItemNote();
            String time = getDate();
            mItemNote.setTime(time);
            if (mBitmap != null) {
                saveImage(mBitmap);
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
                ((OnReplaceFragmentListener) v.getContext()).onReplaceFragmentMain();
                Toast.makeText(getContext(), "sussces", Toast.LENGTH_SHORT).show();
                mEdtNote.setText("");
                mEdtTitle.setText("");
            } else {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_GALLERY) {
                try {
                    mBitmap = getBitmap(data.getData());
                    mImgPhoto.setImageBitmap(mBitmap);
                } catch (Exception e) {
                    Log.i("tag111", "ERROR2");
                }
            }
        }
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMM dd HH:mm:ss", Locale.ENGLISH);
        String[] arr = simpleDateFormat.format(Calendar.getInstance().getTime()).split(" ");
        return arr[0] + "\n" + arr[1] + " " + arr[2] + "\n" + arr[3];
    }

    public String getName(String date) {
        return date.replace("\n", "").replace(" ", "");
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
