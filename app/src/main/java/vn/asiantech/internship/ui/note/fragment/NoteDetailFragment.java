package vn.asiantech.internship.ui.note.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.Note;

/**
 * Note detail
 * <p>
 * Created by Hai on 6/26/2017.
 */

public class NoteDetailFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtEditTitle;
    private EditText mEdtEditContent;
    private ImageView mImgNote;
    private ImageView mImgSave;

    private NewNoteFragment mNewNoteFragment;
    private Uri mUri;
    private Bitmap mBmpAttach;
    private String mFileName;
    private Note mNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        initView(view);
        setToolbar();
        mNewNoteFragment = new NewNoteFragment();
        Bundle bundle = getArguments();
        mNote = bundle.getParcelable(NoteFragment.KEY_DATA);
        setNote(mNote);
        return view;
    }

    private void initView(View view) {
        mEdtEditTitle = (EditText) view.findViewById(R.id.edtEditNoteTitle);
        mEdtEditContent = (EditText) view.findViewById(R.id.edtEditInputContent);
        mImgNote = (ImageView) view.findViewById(R.id.imgNote);
        ImageView mImgAttach = (ImageView) view.findViewById(R.id.imgAttachImage);
        ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEditNote);
        ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDeleteNote);
        mImgSave = (ImageView) view.findViewById(R.id.imgSaveNote);
        mImgAttach.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
    }

    private void setToolbar() {
        mImgSave.setVisibility(View.GONE);
    }

    private void setNote(Note note) {
        mEdtEditTitle.setText(note.getTitle());
        mEdtEditContent.setText(note.getContent());
        if (note.getImage() != null) {
            mImgNote.setVisibility(View.VISIBLE);
            mImgNote.setImageURI(Uri.parse(note.getImage()));
        }
    }

    private void decreaseSizeImage(Uri imageFileUri) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dw = size.x;
        int dh = size.y;
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            mBmpAttach = BitmapFactory.decodeStream(
                    getContext().getContentResolver().openInputStream(imageFileUri),
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
            mBmpAttach = BitmapFactory.decodeStream(getContext().getContentResolver()
                            .openInputStream(imageFileUri), null,
                    bmpFactoryOptions);
            mImgNote.setImageBitmap(mBmpAttach);
        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAttachImage:
                intentGallery();
                break;
            case R.id.imgEditNote:
                editNote();
                break;
            case R.id.imgDeleteNote:
                deleteNote();
                getActivity().onBackPressed();
                break;
        }
    }

    private void editNote() {
        String title = mEdtEditTitle.getText().toString();
        String content = mEdtEditContent.getText().toString();
        if (!title.isEmpty()) {
            mNote.setTitle(title);
            mNote.setContent(content);
            if (mUri != null) {
                mNewNoteFragment.copyImageToSDCard(mBmpAttach, NewNoteFragment.PATH, mFileName);
                mNote.setImage(NewNoteFragment.PATH.concat(mFileName));
            } else {
                mNote.setImage(null);
            }
            NoteDatabase noteDatabase = new NoteDatabase(getContext());
            noteDatabase.open();
            noteDatabase.updateNote(mNote);
            getActivity().onBackPressed();
        }
    }

    private void deleteNote() {
        NoteDatabase noteDatabase = new NoteDatabase(getContext());
        noteDatabase.open();
        noteDatabase.deleteNote(mNote);
    }

    private void intentGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, NewNoteFragment.REQUEST_CODE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == NewNoteFragment.REQUEST_CODE_GALLERY) {
            mUri = data.getData();
            decreaseSizeImage(mUri);
            mFileName = mUri.toString().substring(mUri.toString().lastIndexOf("/"));
            mImgNote.setVisibility(View.VISIBLE);
            mImgNote.setImageBitmap(mBmpAttach);
        }
    }
}
