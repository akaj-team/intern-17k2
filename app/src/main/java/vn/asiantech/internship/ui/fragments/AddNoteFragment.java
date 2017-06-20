package vn.asiantech.internship.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.NoteDatabase;
import vn.asiantech.internship.models.NoteItem;

/**
 * Created by PC on 6/20/2017.
 */

public class AddNoteFragment extends Fragment {
    private ImageView mImagePicture;
    private EditText mEdtNoteTitle;
    private EditText mEdtNoteContent;
    private String mPicturePath;
    private NoteDatabase mNoteDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mImagePicture = (ImageView) view.findViewById(R.id.imgNotePhoto);
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
        NoteItem noteItem = new NoteItem(mEdtNoteTitle.getText().toString(), mEdtNoteContent.getText().toString());
        if (TextUtils.isEmpty(mEdtNoteContent.getText()) || TextUtils.isEmpty(mEdtNoteTitle.getText())) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        } else {
            if (mNoteDatabase.insertNote(noteItem) > 0) {
                Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
                mEdtNoteContent.setText("");
                mEdtNoteTitle.setText("");
            } else {
                Toast.makeText(getContext(), "Thất bại.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
