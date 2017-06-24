package vn.asiantech.internship.ui.note;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import vn.asiantech.internship.database.NoteDatabase;
import vn.asiantech.internship.models.Note;

/**
 * Fragment Detail Note
 * Created by huypham on 22/06/2017.
 */
public class DetailNoteFragment extends Fragment {
    private ImageView mImgSelectedDetail;
    private EditText mEdtTitleDetail;
    private EditText mEdtContentDetail;
    private TextView mTvTimeDetail;

    private Note mNote;
    private boolean isBitmapExists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        mImgSelectedDetail = (ImageView) view.findViewById(R.id.imgSelectedDetail);
        mEdtTitleDetail = (EditText) view.findViewById(R.id.edtTitleDetail);
        mEdtContentDetail = (EditText) view.findViewById(R.id.edtContentDetail);
        mTvTimeDetail = (TextView) view.findViewById(R.id.tvTimeDetail);

        getDataDetail();
        return view;
    }

    private void getDataDetail() {
        mNote = (Note) getArguments().getSerializable(NoteActivity.NOTE_KEY);

        if (mNote != null) {
            if (mNote.getImage() != null) {
                mImgSelectedDetail.setVisibility(View.VISIBLE);
                mImgSelectedDetail.setImageURI(Uri.parse(mNote.getImage()));
            } else {
                mImgSelectedDetail.setVisibility(View.GONE);
            }
        }

        if (mNote != null) {
            mEdtTitleDetail.setText(mNote.getTitle());
        }
        if (mNote != null) {
            mEdtContentDetail.setText(mNote.getContent());
        }
        if (mNote != null) {
            mTvTimeDetail.setText(mNote.getStringTime());
        }
    }

    public long editNote() {
        if (TextUtils.isEmpty(mEdtTitleDetail.getText()) || TextUtils.isEmpty(mEdtContentDetail.getText())) {
            Toast.makeText(getContext(), "Check again", Toast.LENGTH_SHORT).show();
            return -1;
        } else {
            mNote.setTime();
            mNote.setTitle(mEdtTitleDetail.getText().toString().trim());
            mNote.setContent(mEdtContentDetail.getText().toString().trim());
            String savePath = null;
            if (isBitmapExists) {
                savePath = AddNoteFragment.saveImage(((BitmapDrawable) mImgSelectedDetail.getDrawable()).getBitmap());
            }
            if (savePath != null) {
                mNote.setImage(savePath);
            }
            NoteDatabase database = new NoteDatabase(getContext());
            database.open();
            long result = database.updateNote(mNote);
            database.close();
            return result;
        }
    }

    public void addImage(Bitmap bitmap) {
        if (bitmap != null) {
            mImgSelectedDetail.setVisibility(View.VISIBLE);
            mImgSelectedDetail.setImageBitmap(bitmap);
            isBitmapExists = true;
        } else {
            mImgSelectedDetail.setVisibility(View.GONE);
        }
    }

    public int getIdNote() {
        return mNote.getId();
    }
}
