package vn.asiantech.internship.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.main.NoteActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/20/2017
 */
public class DetailNoteFragment extends Fragment {

    private ImageView mImgNotePicture;
    private EditText mEdtNoteTitle;
    private EditText mEdtNoteContent;

    private String mImagePath;
    private NoteItem mNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);
        mImgNotePicture = (ImageView) view.findViewById(R.id.imgNotePicture);
        mEdtNoteTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtNoteContent = (EditText) view.findViewById(R.id.edtNoteContent);
        TextView tvNoteTime = (TextView) view.findViewById(R.id.tvNoteTime);

        mNote = (NoteItem) getArguments().getSerializable(NoteActivity.KEY_NOTE);

        if (mNote.getImage() != null) {
            mImgNotePicture.setVisibility(View.VISIBLE);
            mImgNotePicture.setImageURI(Uri.parse(mNote.getImage()));
        }
        mEdtNoteTitle.setText(mNote.getTitle());
        mEdtNoteContent.setText(mNote.getContent());
        tvNoteTime.setText(mNote.getStringTime());

        return view;
    }

    public void prepareEditNote() {
        mEdtNoteTitle.setEnabled(true);
        mEdtNoteContent.setEnabled(true);
    }

    public long editNote() {
        if (TextUtils.isEmpty(mEdtNoteContent.getText()) || TextUtils.isEmpty(mEdtNoteTitle.getText())) {
            Toast.makeText(getContext(), getString(R.string.validate), Toast.LENGTH_SHORT).show();
            return -1;
        } else {
            mNote.setTime();
            mNote.setTitle(mEdtNoteTitle.getText().toString());
            mNote.setContent(mEdtNoteContent.getText().toString());
            if (mImagePath != null) {
                mNote.setImage(mImagePath);
            }
            NoteDatabase noteDatabase = new NoteDatabase(getContext());
            noteDatabase.open();
            long result = noteDatabase.editNote(mNote);
            noteDatabase.close();
            return result;
        }
    }

    public void addImage(String filePath) {
        mImagePath = filePath;
        if (filePath != null) {
            mImgNotePicture.setVisibility(View.VISIBLE);
            mImgNotePicture.setImageURI(Uri.parse(filePath));
        } else {
            mImgNotePicture.setVisibility(View.GONE);
        }
    }

    public int getNoteId() {
        return mNote.getId();
    }
}
