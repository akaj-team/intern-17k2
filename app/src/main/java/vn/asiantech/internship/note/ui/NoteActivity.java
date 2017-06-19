package vn.asiantech.internship.note.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import vn.asiantech.internship.R;


public class NoteActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("AppNote");
        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new NoteFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnAdd){
            FrameLayout frameAddNote = (FrameLayout) findViewById(R.id.frAddNote);
            FrameLayout frameContainer = (FrameLayout) findViewById(R.id.frContainer);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frAddNote, new AddNoteFragment());
            transaction.addToBackStack(null);
            transaction.commit();
            frameAddNote.setVisibility(View.VISIBLE);
            frameContainer.setVisibility(View.GONE);
            mToolbar.setTitle("Add Note");
//            getMenuInflater().inflate(R.menu.menu_create_note, null);
        }
        return super.onOptionsItemSelected(item);
    }
}
