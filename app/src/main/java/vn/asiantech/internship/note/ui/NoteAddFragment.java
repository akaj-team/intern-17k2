package vn.asiantech.internship.note.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.model.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteAddFragment extends Fragment {

    private EditText mEdtTitle;
    private EditText mEdtContent;
    private ImageView mImageView;

    private NoteDatabase mNoteDatabase;
    private Calendar mCalendar;
    private String mPathImage = "";

    public NoteAddFragment() {
        mNoteDatabase = new NoteDatabase(getContext());
        mCalendar = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_note, container, false);
        initView(layout);
        return layout;
    }

    private void initView(View view) {
        mEdtTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtContent = (EditText) view.findViewById(R.id.edtNoteContent);
        mImageView = (ImageView) view.findViewById(R.id.imgAddNote);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNoteDatabase.open();
        String date = String.valueOf(mCalendar.get(Calendar.DATE));
        String time = mCalendar.get(Calendar.HOUR) + " : " + mCalendar.get(Calendar.MINUTE);
        if (!TextUtils.isEmpty(mEdtTitle.getText().toString())) {
            mNoteDatabase.createData(new Note(
                    mEdtTitle.getText().toString(),
                    mEdtContent.getText().toString(),
                    mPathImage,
                    date,
                    time
            ));
        } else {
            Log.e("NoteAddFragment", "insert error!");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }
}
