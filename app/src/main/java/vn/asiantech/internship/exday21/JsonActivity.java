package vn.asiantech.internship.exday21;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 03-07-2017.
 */
public class JsonActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private GetContactsAsyncTask mGetContactsAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvJson);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(manager);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progressdialog));
        mProgressDialog.setCancelable(false);

        mGetContactsAsyncTask = new GetContactsAsyncTask(new GetContactsAsyncTask.OnCallBack() {
            @Override
            public void onCall() {
                mProgressDialog.show();
            }
            @Override
            public void onCallBack(ArrayList<JsonItem> jsonItems) {
                JsonAdapter adapter = new JsonAdapter(jsonItems);
                mRecyclerView.setAdapter(adapter);
                mProgressDialog.dismiss();
            }
        });
        mGetContactsAsyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetContactsAsyncTask != null) {
            mGetContactsAsyncTask.cancel(true);
        }
    }
}
