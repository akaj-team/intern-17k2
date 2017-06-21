package vn.asiantech.internship.note.ui;


import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.database.NoteDatabase;
import vn.asiantech.internship.note.database.NoteOpenHelper;
import vn.asiantech.internship.note.model.Note;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private Toolbar mToolbar;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvDate;
    private TextView mTvTime;
    private ImageView mImageNote;

    private NoteDatabase mNoteDatabase;
    private int mPosition;

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

        mNoteDatabase = NoteDatabase.getInstantDatabase(getContext());
        mNoteDatabase.open();
        Bundle bundle = getArguments();
        mPosition = bundle.getInt("keykey", -1);
        if (mPosition > -1) {
            showNoteDetail();
        }
    }

    private void showNoteDetail() {
        Note note = mNoteDatabase.getItem(mPosition);
        mTvTitle.setText(note.getTitle());
        mTvContent.setText(note.getContent());
        mTvDate.setText(note.getDate());
        mTvTime.setText(note.getTime());
    }

    private void initUI(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolBarDetailNote);
        mTvDate = (TextView) view.findViewById(R.id.tvDateAdd);
        mTvTime = (TextView) view.findViewById(R.id.tvTimeAdd);
        mTvTitle = (TextView) view.findViewById(R.id.tvNoteTitle);
        mTvContent = (TextView) view.findViewById(R.id.tvNoteContent);
        mImageNote = (ImageView) view.findViewById(R.id.imgDetailNote);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Note");
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnDelete:
                if(mNoteDatabase.deleteNote(mPosition)) {
                    mNoteDatabase.mSqLiteDatabase.execSQL("DELETE FROM " + NoteOpenHelper.TABLE_NAME
                            + " WHERE " + NoteOpenHelper.COL_ID + " = " + mPosition);
                    long lastID = DatabaseUtils.longForQuery(mNoteDatabase.mSqLiteDatabase, "SELECT MAX(" + NoteOpenHelper.COL_ID + ") FROM "
                            + NoteOpenHelper.TABLE_NAME, null);
                    if (lastID > mPosition)
                        mNoteDatabase.mSqLiteDatabase.execSQL("UPDATE " + NoteOpenHelper.TABLE_NAME + " SET "
                                + NoteOpenHelper.COL_ID + " = " + mPosition + " WHERE " + NoteOpenHelper.COL_ID + " = " + lastID);
                    getActivity().onBackPressed();
                }else {
                    Log.e("Grzzzzzzzzzzzzz", "Delete that bai");
                }
                break;
            default:
                // TODO: 21/06/2017
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
