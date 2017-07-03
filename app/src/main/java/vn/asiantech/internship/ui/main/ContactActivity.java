package vn.asiantech.internship.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.ContactAdapter;
import vn.asiantech.internship.background.GetContactsAsyncTask;
import vn.asiantech.internship.models.Contact;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/3/2017.
 */
public class ContactActivity extends AppCompatActivity {

    private static final String URL = "http://api.androidhive.info/contacts/";
    private RecyclerView mRecyclerViewContacts;
    private ContactAdapter mAdapter;
    private List<Contact> mContacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.contact));
        mRecyclerViewContacts = (RecyclerView) findViewById(R.id.recyclerViewContact);
        mRecyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        GetContactsAsyncTask getContactsAsyncTask = new GetContactsAsyncTask(progressDialog, new GetContactsAsyncTask.CallBackListener() {
            @Override
            public void onComplete(ArrayList<Contact> contacts) {
                mContacts = contacts;
                mAdapter = new ContactAdapter(mContacts);
                mRecyclerViewContacts.setAdapter(mAdapter);
            }
        });
        getContactsAsyncTask.execute(URL);
    }
}
