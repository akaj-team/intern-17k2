package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDataBase;
import vn.asiantech.internship.interfaces.OnReplaceFragmentListener;
import vn.asiantech.internship.models.Note;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ducle on 20/06/2017.
 */

public class AddNoteFragment extends Fragment implements View.OnClickListener {
    private static final int REQUESTCODE_GALLERY = 1;
    private ImageView mImageViewNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private ImageView mImgPicPhoto;
    private ImageView mImgSave;
    private String mUriImage;
    private NoteDataBase mNoteDataBase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_add_note, container, false);
        mNoteDataBase = new NoteDataBase(container.getContext());
        initViews(view);
        mImgPicPhoto.setOnClickListener(this);
        mImgSave.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mImageViewNote = (ImageView) view.findViewById(R.id.imgNote);
        mImgPicPhoto = (ImageView) view.findViewById(R.id.imgPicPhoto);
        mImgSave = (ImageView) view.findViewById(R.id.imgSave);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mEdtContent = (EditText) view.findViewById(R.id.edtContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPicPhoto:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUESTCODE_GALLERY);
                break;
            case R.id.imgSave:
                Note note = new Note();
                note.setDate(getDate());
                note.setTitle(mEdtTitle.getText().toString());
                note.setContent(mEdtContent.getText().toString());
                try {
                    mNoteDataBase.open();
                    mNoteDataBase.addNote(note);
                    mNoteDataBase.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((OnReplaceFragmentListener) v.getContext()).OnReplaceFragmentMain();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_GALLERY) {
                mUriImage = data.getData().toString();
            }
        }
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MM dd HH:mm:ss");
        String arr[] = simpleDateFormat.format(Calendar.getInstance().getTime()).split(" ");
        return arr[0] + "\n" + arr[1] + arr[2] + "\n" + arr[3];
    }

    private void saveToSDCard() {

    }
}
