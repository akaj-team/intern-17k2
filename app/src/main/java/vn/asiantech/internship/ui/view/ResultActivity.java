package vn.asiantech.internship.ui.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.adapter.ResultAdapter;
import vn.asiantech.internship.ui.dialog.SubmitDialog;

/**
 * Activity for Result
 * Created by huypham on 26-Jun-17.
 */
public class ResultActivity extends AppCompatActivity {
    private TextView mTvPoint;
    private RecyclerView mRecyclerViewResult;
    private ArrayList<Question> mQuestionList;
    private Button mBtnReTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();
        setResultAdapter();

        mBtnReTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubmitDialog();
            }
        });
    }

    private void initViews() {
        mTvPoint = (TextView) findViewById(R.id.tvPoint);
        mRecyclerViewResult = (RecyclerView) findViewById(R.id.recyclerViewResult);
        mBtnReTest = (Button) findViewById(R.id.btnReTest);
    }

    private void setResultAdapter() {
        mQuestionList = getIntent().getParcelableArrayListExtra(SubmitDialog.QUESTION_SET);
        mTvPoint.setText(getString(R.string.text_view_result_point, getCorrect(), mQuestionList.size()));
        ResultAdapter mResultAdapter = new ResultAdapter(this, mQuestionList);
        mRecyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewResult.setAdapter(mResultAdapter);
        mRecyclerViewResult.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                outRect.set(70, position == 0 ? 20 : 15, 10, 25);
            }
        });
    }

    private int getCorrect() {
        int correct = 0;
        for (Question question : mQuestionList) {
            if (question.isCorrect()) {
                correct++;
            }
        }
        return correct;
    }

    public void showSubmitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog_title));
        builder.setMessage(R.string.dialog_result_retest);
        builder.setPositiveButton(getString(R.string.button_dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        }).setNegativeButton(getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
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
