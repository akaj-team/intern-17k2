package vn.asiantech.internship.exday15;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 26-06-2017.
 */
public class PointActivity extends AppCompatActivity {
    private ArrayList<ItemQuestion> mItemQuestions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        TextView tvPoint = (TextView) findViewById(R.id.tvResult);

        mItemQuestions = getIntent().getParcelableArrayListExtra(ResultDialog.QUESTION_SET_KEY);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewResult);
        recyclerView.setLayoutManager(manager);
        PointAdapter pointAdapter = new PointAdapter(this, mItemQuestions);
        recyclerView.setAdapter(pointAdapter);
        tvPoint.setText(getString(R.string.result_format, getCorrectAnswer(), mItemQuestions.size()));
    }

    @Override
    public void onBackPressed() {
        showConfirmDialog();
    }

    private int getCorrectAnswer() {
        int correctNumber = 0;
        for (ItemQuestion itemQuestion : mItemQuestions) {
            if (itemQuestion.isRight()) {
                correctNumber++;
            }
        }
        return correctNumber;
    }

    public void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_result);
        builder.setMessage(R.string.dialog_message_result);
        builder.setPositiveButton(R.string.dialog_button_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        }).setNegativeButton(R.string.dialog_button_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PointActivity.this, JsonActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
