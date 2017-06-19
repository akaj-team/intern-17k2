package vn.asiantech.internship.notesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by sony on 19/06/2017.
 */

public class NoteActivity extends AppCompatActivity {
   private ImageView mBtnAdd;

   private RecyclerView mRecyclerViewNote;
   private NoteAdapter mAdapter;
   private List<Note> mNotes = new ArrayList<>();
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_note);

      mBtnAdd = (ImageView) findViewById(R.id.imgAddNote);
      mRecyclerViewNote = (RecyclerView) findViewById(R.id.recyclerViewNote);

      mAdapter = new NoteAdapter(mNotes);
      mRecyclerViewNote.setAdapter(mAdapter);

      mBtnAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(new Intent(NoteActivity.this,InputNoteActivity.class));
         }
      });
   }
}
