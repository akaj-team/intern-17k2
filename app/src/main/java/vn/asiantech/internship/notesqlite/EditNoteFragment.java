package vn.asiantech.internship.notesqlite;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.R.id.imgSave;

/**
 * Used to enter data for item of recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
public class EditNoteFragment extends Fragment {
    private ImageView mImgNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private TextView mTvTime;
    private TextView mTvTitle;
    private TextView mTvError;
    private ImageView mImgEdit;
    private ImageView mImgDelete;
    private ImageView mImgSave;
    private NoteSqlite mDatabase;
    private Note mNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        initViews(view);
        setData();
        setListeners();
        return view;
    }

    private void initViews(View view) {
        mImgNote = (ImageView) view.findViewById(R.id.imgNoteEdit);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitleEdit);
        mEdtContent = (EditText) view.findViewById(R.id.edtContentEdit);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        mTvTitle = (TextView) view.findViewById(R.id.tvEditTitle);
        mTvError = (TextView) view.findViewById(R.id.tvEditNoteError);
        mImgEdit = (ImageView) view.findViewById(R.id.imgEditNote);
        mImgDelete = (ImageView) view.findViewById(R.id.imgDeleteNote);
        mImgSave = (ImageView) view.findViewById(imgSave);
    }

    private void setData() {
        mNote = getArguments().getParcelable("Note");
        setData(mNote);
        mDatabase = new NoteSqlite(getActivity());
    }

    private void setListeners() {
        mEdtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputAfterTest(editable, R.string.note_text_error_title);
            }
        });

        mEdtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputAfterTest(editable, R.string.note_text_error_content);
            }
        });

        mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvTitle.setText(R.string.textView_edit_title);
                mEdtTitle.setEnabled(true);
                mEdtContent.setEnabled(true);
            }
        });

        mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.open();
                mDatabase.delete(mNote.getId());
                mDatabase.close();
                ((NoteActivity) (getActivity())).replaceNoteFragment();
                Toast.makeText(getActivity(), "Delete success", Toast.LENGTH_SHORT).show();
            }
        });

        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.equals(mEdtTitle.getText().toString(), "") && !TextUtils.equals(mEdtContent.getText().toString(), "")) {
                    mTvError.setVisibility(View.GONE);
                    mDatabase.open();
                    mDatabase.update(mEdtTitle.getText().toString(), mEdtContent.getText().toString(), mNote.getId());
                    mDatabase.close();
                    Toast.makeText(getActivity(), "Edit success", Toast.LENGTH_SHORT).show();
                    ((NoteActivity) (getActivity())).replaceNoteFragment();
                } else if (TextUtils.equals(mEdtTitle.getText().toString(), "")) {
                    mTvError.setText(R.string.note_text_error_title);
                    mTvError.setVisibility(View.VISIBLE);
                } else if (TextUtils.equals(mEdtContent.getText().toString(), "")) {
                    mTvError.setText(R.string.note_text_error_content);
                    mTvError.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setData(Note note) {
        if (note != null) {
            if (!TextUtils.equals(note.getPathImage(), "")) {
                mImgNote.setVisibility(View.VISIBLE);
                mImgNote.setImageBitmap(BitmapFactory.decodeFile(note.getPathImage()));
            }
            mEdtTitle.setText(note.getTitle());
            mEdtContent.setText(note.getContent());
            mTvTime.setText(note.getDay() + "\n" + note.getDayOfWeek() + " " + note.getMonth() + "\n" + note.getHour());
        }
    }

    private void inputAfterTest(Editable data, int s) {
        if (data.length() == 0) {
            mTvError.setText(s);
            mTvError.setVisibility(View.VISIBLE);
        } else {
            mTvError.setVisibility(View.GONE);
        }
    }
}
