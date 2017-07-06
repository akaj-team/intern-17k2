package vn.asiantech.internship.day21.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day21.handlerjson.AsyncTaskHandler;
import vn.asiantech.internship.day21.models.Contact;

/**
 * JsonActivity show list contact
 */
public class JsonActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initUI();
    }

    private void initUI() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewJson);
        recyclerView.setLayoutManager(new LinearLayoutManager(JsonActivity.this));
        recyclerView.setHasFixedSize(true);
        AsyncTaskHandler asyncTask = new AsyncTaskHandler(new AsyncTaskHandler.OnUpdateUiListener() {
            @Override
            public void onShowDialog() {
                mProgressDialog = new ProgressDialog(JsonActivity.this);
                mProgressDialog.setMessage(getResources().getString(R.string.dialog_wait));
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            public void onUpdateRecyclerView(ArrayList<Contact> contacts) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                JsonAdapter adapter = new JsonAdapter(contacts);
                recyclerView.setAdapter(adapter);
            }
        });
        asyncTask.execute();
    }
}
