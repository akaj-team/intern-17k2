package vn.asiantech.internship.day22.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day22.handlerjson.JSONAsyncTask;
import vn.asiantech.internship.day22.models.Contact;

/**
 * JSONActivity show list contact
 */
public class JSONActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initUI();
    }

    private void initUI() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewJSON);
        recyclerView.setLayoutManager(new LinearLayoutManager(JSONActivity.this));
        recyclerView.setHasFixedSize(true);
        JSONAsyncTask asyncTask = new JSONAsyncTask(new JSONAsyncTask.OnUpdateUiListener() {
            @Override
            public void onShowDialog() {
                mProgressDialog = new ProgressDialog(JSONActivity.this);
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
