package vn.asiantech.internship.note.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;


public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new NoteFragment()).commit();
    }
}
