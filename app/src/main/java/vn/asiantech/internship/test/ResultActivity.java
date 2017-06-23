package vn.asiantech.internship.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display your result after complete the test
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        RecyclerView resultRecyclerView = (RecyclerView) findViewById(R.id.resultRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        resultRecyclerView.setLayoutManager(linearLayoutManager);
        List<Result> results = new ArrayList<>();
        List<Question> questions = getIntent().getParcelableArrayListExtra(ResultDialog.TEST_LIST);
        for (int i = 0; i < questions.size(); i++) {
            results.add(new Result(getString(R.string.textView_question) + " " + i + 1, questions.get(i).isTrue()));
        }
        ResultAdapter adapter = new ResultAdapter(results);
        resultRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    private void showDialog() {
        android.app.FragmentManager fm = this.getFragmentManager();
        ExitDialog exitDialog = ExitDialog.newInstance();
        exitDialog.show(fm, null);
    }
}
