package vn.asiantech.internship.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.ResultAdapter;
import vn.asiantech.internship.fragment.ResultDialogFragment;
import vn.asiantech.internship.models.Question;

/**
 * Created by ducle on 26/06/2017.
 */
public class ResultActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewResult;
    private ResultAdapter mResultAdapter;
    private List<Question> mQuestions;
    private TextView mTvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mQuestions = getIntent().getParcelableArrayListExtra(ResultDialogFragment.KEY_QUESTIONS);
        mRecyclerViewResult = (RecyclerView) findViewById(R.id.recyclerViewResult);
        mTvResult = (TextView) findViewById(R.id.tvResult);
        int rightQuestionNumber = 0;
        for (int i = 0; i < mQuestions.size(); i++) {
            if (mQuestions.get(i).isCheckedRight()) {
                rightQuestionNumber++;
            }
        }
        mTvResult.setText(getString(R.string.result_right, rightQuestionNumber, mQuestions.size()));
        mResultAdapter = new ResultAdapter(mQuestions);
        mRecyclerViewResult.setAdapter(mResultAdapter);
        mRecyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
    }
}
