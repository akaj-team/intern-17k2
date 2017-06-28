package vn.asiantech.internship.note;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 20-06-2017.
 */
public class AddNoteFragment extends Fragment {
    public static final int REQUEST_CODE_GALLERY = 22;
    private EditText mEdtTitle;
    private EditText mEdtNote;
    private boolean mIsBitmap = false;
    private ImageView mImgPhoto;
    private NoteDatabase mNoteDatabase;
    private ItemNote mItemNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);
        initViews(view);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
        return view;
    }

    private void initViews(View view) {
        mImgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mEdtNote = (EditText) view.findViewById(R.id.edtNote);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNoteDatabase.close();
    }

    public void addNote() {
        if (TextUtils.isEmpty(mEdtNote.getText()) || TextUtils.isEmpty(mEdtTitle.getText())) {
            Toast.makeText(getContext(), "Please text something", Toast.LENGTH_SHORT).show();
        } else {
            mItemNote = new ItemNote();
            String time = getDate();
            mItemNote.setTime(time);
            String savePath = null;
            if (mIsBitmap) {
                BitmapDrawable drawable = (BitmapDrawable) mImgPhoto.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                savePath = saveImage(bitmap);
            }
            if (savePath != null) {
                mItemNote = new ItemNote(mItemNote.getTime(), mEdtTitle.getText().toString(), mEdtNote.getText().toString(), savePath);
            } else {
                mItemNote = new ItemNote(mItemNote.getTime(), mEdtTitle.getText().toString(), mEdtNote.getText().toString());
            }
        }
        if (mNoteDatabase.insertNote(mItemNote) > 0) {
            Toast.makeText(getContext(), "Success !!!", Toast.LENGTH_SHORT).show();
            mEdtNote.setText("");
            mEdtTitle.setText("");
        } else {
            Toast.makeText(getContext(), "Fail !!!", Toast.LENGTH_SHORT).show();
        }
        mEdtNote.setFocusable(false);
        mEdtTitle.setFocusable(false);
    }


    public static String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMM dd HH:mm:ss", Locale.ENGLISH);
        String[] arr = simpleDateFormat.format(Calendar.getInstance().getTime()).split(" ");
        return arr[0] + "\n" + arr[1] + " " + arr[2] + "\n" + arr[3];
    }

    public void addImage(Bitmap bitmap) {
        if (bitmap != null) {
            mImgPhoto.setVisibility(View.VISIBLE);
            mImgPhoto.setImageBitmap(bitmap);
            mIsBitmap = true;
        } else {
            mImgPhoto.setVisibility(View.GONE);
        }
    }

    public static String saveImage(Bitmap bitmap) {
        String targetFolderPath = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "ImageSource";
        OutputStream outputStream;
        try {
            File targetFolder = new File(targetFolderPath);
            targetFolder.mkdirs();
            File targetFile = File.createTempFile("Image", ".png", targetFolder);
            outputStream = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return targetFile.getPath();
        } catch (IOException x) {
            Log.i("tag11", x.getMessage());
        }
        return null;
    }
}
