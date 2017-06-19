package vn.asiantech.internship.notesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by sony on 19/06/2017.
 */

public class NoteActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    public static final int REQSULT_CODE = 2;
    private ImageView mImgAdd;

    private RecyclerView mRecyclerViewNote;
    private NoteAdapter mAdapter;
    private List<Note> mNotes = new ArrayList<>();
    private NoteSqlite mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mData = new NoteSqlite(this);

        mImgAdd = (ImageView) findViewById(R.id.imgAddNote);
        mRecyclerViewNote = (RecyclerView) findViewById(R.id.recyclerViewNote);

        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(NoteActivity.this, InputNoteActivity.class), REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == REQSULT_CODE) {
            try {
                mData.open();
                mNotes = mData.getNotes();
                mData.close();
                mAdapter = new NoteAdapter(mNotes);
                mRecyclerViewNote.setAdapter(mAdapter);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
