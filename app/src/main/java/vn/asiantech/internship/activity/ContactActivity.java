package vn.asiantech.internship.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.ContactAdapter;
import vn.asiantech.internship.apis.ContactApi;
import vn.asiantech.internship.asynctasks.GetContacts;
import vn.asiantech.internship.models.Contact;

/**
 * Created by ducle on 03/07/2017.
 * ContactActivity contain a RecyclerView to show list contact
 */
public class ContactActivity extends AppCompatActivity implements GetContacts.OnUpdateListener {
    private static final String TAG = "tag111";
    private RecyclerView mRecyclerViewContact;
    private ContactAdapter mContactAdapter;
    private ProgressDialog mProgressDialog;
    private static String url = ContactApi.url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerViewContact = (RecyclerView) findViewById(R.id.recyclerViewContact);
        new GetContacts(this).execute(url);
    }

    @Override
    public void onShowProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ... ");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Log.d(TAG, "onShowProgressDialog: ");
    }

    @Override
    public void onCloseProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            Log.d(TAG, "onCloseProgressDialog: " + 1);
        }
        Log.d(TAG, "onCloseProgressDialog: " + 2);
    }

    @Override
    public void onUpdateData(ArrayList<Contact> contacts) {
        mContactAdapter = new ContactAdapter(contacts);
        mRecyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewContact.setAdapter(mContactAdapter);
        Log.d(TAG, "onUpdateData: ");
    }
}
