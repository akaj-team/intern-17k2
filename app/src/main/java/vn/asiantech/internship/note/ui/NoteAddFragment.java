package vn.asiantech.internship.note.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Toolbar mToolbar;

    private NoteDatabase mNoteDatabase;
    private Calendar mCalendar;
    private String mPathImage = "";

    public NoteAddFragment() {
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
        mToolbar = (Toolbar) view.findViewById(R.id.toolBarAddNote);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("AddNote");
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAttachFile:


                break;
            case R.id.mnSave:
                String date = String.valueOf(mCalendar.get(Calendar.DATE) + " " + mCalendar.get(Calendar.MONTH));
                String time = mCalendar.get(Calendar.HOUR) + " : " + mCalendar.get(Calendar.MINUTE);
                if (!TextUtils.isEmpty(mEdtTitle.getText().toString())) {
                    mNoteDatabase.createData(new Note(
                            mEdtTitle.getText().toString(),
                            mEdtContent.getText().toString(),
                            mPathImage,
                            date,
                            time
                    ));
                    getActivity().onBackPressed();
                } else {
                    Log.e("NoteAddFragment", "insert error!");
                }
                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_create_note, menu);
    }
}
