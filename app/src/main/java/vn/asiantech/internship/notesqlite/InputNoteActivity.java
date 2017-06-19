package vn.asiantech.internship.notesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.sql.SQLException;
import java.util.Date;

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
    private Button mBtnAdd;
    private NoteSqlite mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);
        mImgNote = (ImageView) findViewById(R.id.imgImageNote);
        mEdtTitle = (EditText) findViewById(R.id.edtTitle);
        mEdtContent = (EditText) findViewById(R.id.edtContent);
        mImgChooseImage = (ImageView) findViewById(R.id.imgAddImage);
        mImgDelete = (ImageView) findViewById(R.id.imgDelete);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);
        mDatabase = new NoteSqlite(this);


        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDatabase.open();
                    Date date = new Date();
                    String datee = String.valueOf(date.getDate());
                    String day = String.valueOf(date.getDay());
                    String month = String.valueOf(date.getMonth());
                    String hour = String.valueOf(date.getHours());
                    Log.i("aaaaaaaaaa", "onClick: "+datee);
                    mDatabase.createData(new Note(datee,day,month,hour, mEdtTitle.getText().toString(), mEdtContent.getText().toString(), "aaa"));
                    mDatabase.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                setResult(NoteActivity.REQSULT_CODE,new Intent(InputNoteActivity.this, NoteActivity.class));
                finish();
            }
        });
    }
}
