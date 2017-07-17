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
public class EditNoteFragment extends Fragment implements TextWatcher, View.OnFocusChangeListener {
    private ImageView mImgNoteEdit;
    private EditText mEdtTitleEdit;
    private EditText mEdtContentEdit;
    private TextView mTvTimeEdit;
    private TextView mTvTitleEdit;
    private TextView mTvErrorEdit;
    private ImageView mImgEdit;
    private ImageView mImgDelete;
    private ImageView mImgSave;
    private NoteSqlite mDatabase;
    private Note mNote;
    private int mFocusEditTextEdit;

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
        mImgNoteEdit = (ImageView) view.findViewById(R.id.imgNoteEdit);
        mEdtTitleEdit = (EditText) view.findViewById(R.id.edtTitleEdit);
        mEdtContentEdit = (EditText) view.findViewById(R.id.edtContentEdit);
        mTvTimeEdit = (TextView) view.findViewById(R.id.tvTime);
        mTvTitleEdit = (TextView) view.findViewById(R.id.tvEditTitle);
        mTvErrorEdit = (TextView) view.findViewById(R.id.tvEditNoteError);
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
        mEdtTitleEdit.addTextChangedListener(this);
        mEdtContentEdit.addTextChangedListener(this);
        mEdtTitleEdit.setOnFocusChangeListener(this);
        mEdtContentEdit.setOnFocusChangeListener(this);

        mImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvTitleEdit.setText(R.string.textView_edit_title);
                mEdtTitleEdit.setEnabled(true);
                mEdtContentEdit.setEnabled(true);
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
                if (!TextUtils.equals(mEdtTitleEdit.getText().toString(), "") && !TextUtils.equals(mEdtContentEdit.getText().toString(), "")) {
                    mTvErrorEdit.setVisibility(View.GONE);
                    mDatabase.open();
                    mDatabase.update(mEdtTitleEdit.getText().toString(), mEdtContentEdit.getText().toString(), mNote.getId());
                    mDatabase.close();
                    Toast.makeText(getActivity(), "Edit success", Toast.LENGTH_SHORT).show();
                    ((NoteActivity) (getActivity())).replaceNoteFragment();
                } else if (TextUtils.equals(mEdtTitleEdit.getText().toString(), "")) {
                    mTvErrorEdit.setText(R.string.note_text_error_title);
                    mTvErrorEdit.setVisibility(View.VISIBLE);
                } else if (TextUtils.equals(mEdtContentEdit.getText().toString(), "")) {
                    mTvErrorEdit.setText(R.string.note_text_error_content);
                    mTvErrorEdit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setData(Note note) {
        if (note != null) {
            if (!TextUtils.equals(note.getPathImage(), "")) {
                mImgNoteEdit.setVisibility(View.VISIBLE);
                mImgNoteEdit.setImageBitmap(BitmapFactory.decodeFile(note.getPathImage()));
            }
            mEdtTitleEdit.setText(note.getTitle());
            mEdtContentEdit.setText(note.getContent());
            String time = note.getDay() + "\n" + note.getDayOfWeek() + " " + note.getMonth() + "\n" + note.getHour();
            mTvTimeEdit.setText(time);
        }
    }

    private void inputAfterTest(Editable data, int s) {
        if (data.length() == 0) {
            mTvErrorEdit.setText(s);
            mTvErrorEdit.setVisibility(View.VISIBLE);
        } else {
            mTvErrorEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.edtTitleEdit) {
            mFocusEditTextEdit = 1;
        } else if (view.getId() == R.id.edtContentEdit) {
            mFocusEditTextEdit = 2;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mFocusEditTextEdit == 1) {
            inputAfterTest(editable, R.string.note_text_error_title);
        } else if (mFocusEditTextEdit == 2) {
            inputAfterTest(editable, R.string.note_text_error_content);
        }
    }
}
