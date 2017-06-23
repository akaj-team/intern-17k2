package vn.asiantech.internship.ui.main;

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
import vn.asiantech.internship.adapters.ResultAdapter;
import vn.asiantech.internship.dialogs.ConfirmDialog;
import vn.asiantech.internship.models.Question;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/23/2017
 */
public class ResultActivity extends AppCompatActivity {
    private TextView mTvTitle;
    private TextView mTvPoint;
    private RecyclerView mRecyclerView;
    private ArrayList<Question> mQuestionSet;
    private ResultAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mTvPoint = (TextView) findViewById(R.id.tvPoint);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewResult);
        mTvTitle.setText(getString(R.string.your_result));

        mQuestionSet = getIntent().getParcelableArrayListExtra(ConfirmDialog.QUESTION_SET_KEY);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ResultAdapter(this, mQuestionSet);
        mRecyclerView.setAdapter(mAdapter);
        mTvPoint.setText(getString(R.string.result_format, getCorrectAnswer(), mQuestionSet.size()));
    }

    @Override
    public void onBackPressed() {
        showConfirmDialog();
    }

    private int getCorrectAnswer() {
        int correctNumber = 0;
        for (Question q : mQuestionSet) {
            if (q.isRight()) {
                correctNumber++;
            }
        }
        return correctNumber;
    }

    public void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.re_test_title));
        builder.setMessage(R.string.re_test_message);
        builder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        }).setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ResultActivity.this, TestActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
