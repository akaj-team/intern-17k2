package vn.asiantech.internship.ui.fragments;

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
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.NoteItem;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/20/2017
 */
public class AddNoteFragment extends Fragment {
    private ImageView mImgNotePicture;
    private EditText mEdtNoteTitle;
    private EditText mEdtNoteContent;
    private NoteDatabase mNoteDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mImgNotePicture = (ImageView) view.findViewById(R.id.imgNotePicture);
        mEdtNoteTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtNoteContent = (EditText) view.findViewById(R.id.edtNoteContent);

        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNoteDatabase.close();
    }

    public void addNote() {

        if (TextUtils.isEmpty(mEdtNoteContent.getText()) || TextUtils.isEmpty(mEdtNoteTitle.getText())) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        } else {
            NoteItem noteItem;
            String savePath = saveImage(((BitmapDrawable) mImgNotePicture.getDrawable()).getBitmap());
            if (savePath != null) {
                noteItem = new NoteItem(mEdtNoteTitle.getText().toString(), mEdtNoteContent.getText().toString(), savePath);
            } else {
                noteItem = new NoteItem(mEdtNoteTitle.getText().toString(), mEdtNoteContent.getText().toString());
            }
            if (mNoteDatabase.insertNote(noteItem) > 0) {
                Toast.makeText(getContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                mEdtNoteContent.setText("");
                mEdtNoteTitle.setText("");
            } else {
                Toast.makeText(getContext(), getString(R.string.fail), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addImage(Bitmap bitmap) {
        if (bitmap != null) {
            mImgNotePicture.setVisibility(View.VISIBLE);
            mImgNotePicture.setImageBitmap(bitmap);
        } else {
            mImgNotePicture.setVisibility(View.GONE);
        }
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
}
