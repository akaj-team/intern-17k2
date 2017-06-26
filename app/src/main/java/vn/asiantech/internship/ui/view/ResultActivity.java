package vn.asiantech.internship.ui.view;

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
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.adapter.ResultAdapter;
import vn.asiantech.internship.ui.dialog.SubmitDialog;

/**
 * Created by AnhHuy on 26-Jun-17.
 */

public class ResultActivity extends AppCompatActivity {
    private TextView mTvPoint;
    private RecyclerView mRecyclerViewResult;
    private ArrayList<Question> mQuestionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();
        setResultAdapter();
    }

    private void initViews() {
        mTvPoint = (TextView) findViewById(R.id.tvPoint);
        mRecyclerViewResult = (RecyclerView) findViewById(R.id.recyclerViewResult);
    }

    private void setResultAdapter() {
        mQuestionList = getIntent().getParcelableArrayListExtra(SubmitDialog.QUESTION_SET);
        ResultAdapter mResultAdapter = new ResultAdapter(this, mQuestionList);
        mRecyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewResult.setAdapter(mResultAdapter);
        mTvPoint.setText(getString(R.string.point, getCorrect(), mQuestionList.size()));
    }

    private int getCorrect() {
        int correct = 0;
        for (Question question : mQuestionList) {
            if (question.isCorrect()){
                correct++;
            }
        }
        return correct;
    }

    public void showSubmitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title));
        builder.setMessage(R.string.message_submit);
        builder.setPositiveButton(getString(R.string.button_dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        }).setNegativeButton(getString(R.string.button_dialog_submit), new DialogInterface.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        showSubmitDialog();
    }
}
