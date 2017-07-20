package vn.asiantech.internship.note;

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

import vn.asiantech.internship.R;

import static vn.asiantech.internship.note.AddNoteFragment.saveImage;

/**
 * Created by datbu on 25-06-2017.
 */
public class DetailNoteFragment extends Fragment {
    private EditText mEdtTitle;
    private EditText mEdtNote;
    private ImageView mImgPhoto;
    private TextView mTvTime;
    private boolean mIsBitmap = false;
    private ItemNote mItemNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);
        mItemNote = (ItemNote) getArguments().getSerializable(NoteActivity.KEY_NOTE);

        if ((mItemNote.getImage() != null)) {
            mImgPhoto.setVisibility(View.VISIBLE);
            mImgPhoto.setImageURI(Uri.parse(mItemNote.getImage()));
        }
        if (mItemNote.getTitle().equals("(No title)")) {
            mEdtTitle.setHint(R.string.edt_title);
            mEdtTitle.setText(R.string.edt_no_title);
        } else {
            mEdtTitle.setText(mItemNote.getTitle());
        }
        if (mItemNote.getNote().equals("(No content)")) {
            mEdtNote.setHint(R.string.edt_note);
            mEdtNote.setText(R.string.edt_no_content);
        } else {
            mEdtNote.setText(mItemNote.getNote());
        }
        mTvTime.setText(mItemNote.getTime());
        return view;
    }

    public void prepareEditNote() {
        mEdtTitle.setEnabled(true);
        mEdtNote.setEnabled(true);
    }

    private void initView(View view) {
        mImgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        mEdtNote = (EditText) view.findViewById(R.id.edtNote);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
    }

    public long editNote() {
        String time = AddNoteFragment.getDate();
        mItemNote.setTime(time);
        String savePath = null;

        mEdtNote.setFocusable(true);
        mEdtTitle.setFocusable(true);

        if (TextUtils.isEmpty(mEdtNote.getText()) || TextUtils.isEmpty(mEdtTitle.getText())) {
            String note = getString(R.string.edt_no_content);
            String title = getString(R.string.edt_no_title);
            if (TextUtils.isEmpty(mEdtNote.getText()) && !TextUtils.isEmpty(mEdtTitle.getText())) {
                mEdtNote.setText("");
                mItemNote.setNote(note);
            } else if (TextUtils.isEmpty(mEdtTitle.getText()) && !TextUtils.isEmpty(mEdtNote.getText())) {
                mEdtTitle.setText("");
                mItemNote.setTitle(title);
            } else {
                mEdtNote.setText("");
                mEdtTitle.setText("");
                mItemNote.setTitle(title);
                mItemNote.setNote(note);
            }
            NoteDatabase noteDatabase = new NoteDatabase(getContext());
            noteDatabase.open();
            long result = noteDatabase.editNote(mItemNote);
            noteDatabase.close();
            return result;
        } else {
            if (mIsBitmap) {
                savePath = saveImage(((BitmapDrawable) mImgPhoto.getDrawable()).getBitmap());
            }
            if (savePath != null) {
                mItemNote.setImage(savePath);
            }
            mItemNote.setNote(mEdtNote.getText().toString());
            mItemNote.setTitle(mEdtTitle.getText().toString());
            NoteDatabase noteDatabase = new NoteDatabase(getContext());
            noteDatabase.open();
            long result = noteDatabase.editNote(mItemNote);
            noteDatabase.close();
            return result;
        }
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

    public int getNoteId() {
        return mItemNote.getId();
    }
}
