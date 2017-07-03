package vn.asiantech.internship.day21.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day21.another.GetContact;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 03/07/2017.
 */
public class ContactActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private GetContact mGetContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewContact);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mGetContact = new GetContact(this);
        mGetContact.execute();
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetContact != null && !mGetContact.isCancelled()) {
            mGetContact.cancel(true);
        }
    }

    public void dismiss() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

