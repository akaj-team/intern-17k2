package vn.asiantech.internship.note.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.model.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private EditText mEdtTitle;
    private EditText mEdtContent;
    private TextView mTvDate;
    private ImageView mImageNote;

    private NoteDatabase mNoteDatabase;
    private int mPosition;
    private boolean mIsEditNote;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_detail, container, false);
        initUI(layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNoteDatabase = new NoteDatabase(getContext());
        mNoteDatabase.open();
        Bundle bundle = getArguments();
        mPosition = bundle.getInt(NoteActivity.KEY_ID, -1);
        if (mPosition > -1) {
            showNoteDetail();
        }
    }

    private void showNoteDetail() {
        Note note = mNoteDatabase.getItem(mPosition);
        mEdtTitle.setText(note.getTitle());
        mEdtContent.setText(note.getContent());
        mTvDate.setText(note.getDatetime());
        if (note.getPath() != null) {
            mImageNote.setImageURI(Uri.parse(note.getPath()));
        }
    }

    private void initUI(View view) {
        Toolbar toolBar = (Toolbar) view.findViewById(R.id.toolBarDetailNote);
        mTvDate = (TextView) view.findViewById(R.id.tvDateTimeAdd);
        mEdtTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtTitle.setEnabled(false);
        mEdtContent = (EditText) view.findViewById(R.id.edtNoteContent);
        mEdtContent.setEnabled(false);
        mImageNote = (ImageView) view.findViewById(R.id.imgDetailNote);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
        toolBar.setTitle("Detail Note");
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnDelete:
                mNoteDatabase.deleteNote(mPosition);
                getActivity().onBackPressed();
                break;
            case R.id.mnEdit:
                if (mIsEditNote) {
                    if (!TextUtils.isEmpty(mEdtTitle.getText())) {
                        if (mNoteDatabase.updateNote(mPosition, mEdtTitle.getText().toString(), mEdtContent.getText().toString())) {
                            getActivity().onBackPressed();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please add title for this note!", Toast.LENGTH_SHORT).show();
                    }
                }
                mEdtTitle.setEnabled(true);
                mEdtTitle.setFocusable(true);
                mEdtContent.setEnabled(true);
                mIsEditNote = !mIsEditNote;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_detail_note, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoteDatabase.close();
    }
}
