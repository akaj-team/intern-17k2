package vn.asiantech.internship.day21.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day21.handlerjson.JSONAsyncTask;

/**
 * JSONActivity show list contact
 */
public class JSONActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initUI();
    }

    private void initUI() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewJSON);
        recyclerView.setLayoutManager(new LinearLayoutManager(JSONActivity.this));
        recyclerView.setHasFixedSize(true);
        JSONAsyncTask asyncTask = new JSONAsyncTask(JSONActivity.this, recyclerView);
        asyncTask.execute();
    }
}
