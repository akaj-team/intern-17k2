package vn.asiantech.internship.ui.note;

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

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.NoteDatabase;
import vn.asiantech.internship.models.Note;

/**
 * Fragment Add New Note
 * Created by huypham on 20/06/2017.
 */
public class AddNoteFragment extends Fragment {
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private ImageView mImgImageSelected;
    private NoteDatabase mNoteDatabase;
    private boolean isBitmapExists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mEdtContent = (EditText) view.findViewById(R.id.edtContent);
        mImgImageSelected = (ImageView) view.findViewById(R.id.imgSelected);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();

        return view;
    }

    public void addNote() {
        if (!TextUtils.isEmpty(mEdtTitle.getText().toString().trim()) || !TextUtils.isEmpty(mEdtContent.getText().toString().trim())) {
            Note note;
            String savePath = null;
            if (isBitmapExists) {
                savePath = saveImage(((BitmapDrawable) mImgImageSelected.getDrawable()).getBitmap());
            }
            if (savePath != null) {
                note = new Note(mEdtTitle.getText().toString().trim(), mEdtContent.getText().toString().trim(), savePath);
            } else {
                note = new Note(mEdtTitle.getText().toString().trim(), mEdtContent.getText().toString().trim());
            }
            if (mNoteDatabase.insertNote(note) > 0) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                mEdtTitle.setText("");
                mEdtContent.setText("");
            } else {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addImage(Bitmap bitmap) {
        if (bitmap != null) {
            mImgImageSelected.setVisibility(View.VISIBLE);
            mImgImageSelected.setImageBitmap(bitmap);
            isBitmapExists = true;
        } else {
            mImgImageSelected.setVisibility(View.GONE);
        }
    }

    public static String saveImage(Bitmap bitmap) {
        String targetFolderPath = Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "AANoteImage";
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNoteDatabase.close();
    }

    public String getEditTextTitle() {
        return mEdtTitle.getText().toString().trim();
    }

    public String getEditTextContent() {
        return mEdtContent.getText().toString().trim();
    }
}
