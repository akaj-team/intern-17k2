package vn.asiantech.internship.ui.note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import vn.asiantech.internship.R;

/**
 * activity contain NoteFragment
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }
}
