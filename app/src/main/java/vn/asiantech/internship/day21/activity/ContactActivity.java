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
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewContact);
        new GetContact(this).execute();
    }

    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }
}

