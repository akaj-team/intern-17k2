package vn.asiantech.internship.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Result;

/**
 * Activity contain result test
 * <p>
 * Created by Hai on 6/25/2017.
 */

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(QuestionDialogFragment.KEY_INTENT);
        List<Result> results = bundle.getParcelableArrayList(QuestionDialogFragment.KEY_BUNDLE);
        RecyclerView recyclerViewResult = (RecyclerView) findViewById(R.id.recyclerViewResult);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewResult.setLayoutManager(layoutManager);
        ResultAdapter adapter = new ResultAdapter(results);
        recyclerViewResult.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        ResultDialogFragment dialog = new ResultDialogFragment();
        dialog.show(fm, null);
    }
}
