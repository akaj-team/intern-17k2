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
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerViewContacts;
    private ContactAdapter mAdapter;
    private List<Contact> mContacts;
    private GetContactsAsyncTask mGetContactsAsyncTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.contact));
        mRecyclerViewContacts = (RecyclerView) findViewById(R.id.recyclerViewContact);
        mRecyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mGetContactsAsyncTask = new GetContactsAsyncTask(new GetContactsAsyncTask.CallBackListener() {
            @Override
            public void onTaskPreExecute() {
                mProgressDialog.show();
            }

            @Override
            public void onComplete(ArrayList<Contact> contacts) {
                mContacts = contacts;
                mAdapter = new ContactAdapter(mContacts);
                mRecyclerViewContacts.setAdapter(mAdapter);
                mProgressDialog.dismiss();
            }
        });
        mGetContactsAsyncTask.execute(URL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetContactsAsyncTask != null) {
            mGetContactsAsyncTask.cancel(true);
        }
    }
}
