package vn.asiantech.internship.notesqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import java.sql.SQLException;

import vn.asiantech.internship.R;

/**
 * Created by sony on 19/06/2017.
 */

public class InputNoteActivity extends AppCompatActivity {

    private ImageView mImgNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private ImageView mImgChooseImage;
    private ImageView mImgDelete;
    private NoteSqlite mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);
        mImgNote = (ImageView) findViewById(R.id.imgImageNote);
        mEdtTitle = (EditText) findViewById(R.id.edtTitle);
        mEdtContent = (EditText) findViewById(R.id.edtContent);
        mImgChooseImage =(ImageView) findViewById(R.id.imgAddImage);
        mImgDelete =(ImageView) findViewById(R.id.imgDelete);
        mDatabase = new NoteSqlite(this);

        try {
            mDatabase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
