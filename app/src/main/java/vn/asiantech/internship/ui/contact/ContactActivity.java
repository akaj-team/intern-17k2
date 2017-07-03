package vn.asiantech.internship.ui.contact;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.ContactAdapter;
import vn.asiantech.internship.asynctask.GetContacts;
import vn.asiantech.internship.models.Contact;

/**
 *
 * Created by quanghai on 03/07/2017.
 */
public class ContactActivity extends AppCompatActivity {
    private static final String URL = "http://api.androidhive.info/contacts/";
    private RecyclerView mRecyclerViewContact;
    private ProgressDialog mProgressDialog;
    private GetContacts mGetContacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setProgressDialog();
        mRecyclerViewContact = (RecyclerView) findViewById(R.id.recyclerViewContact);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewContact.setLayoutManager(linearLayoutManager);
        mGetContacts = new GetContacts(new GetContacts.OnListener() {
            @Override
            public void onUpdateRecyclerView(ArrayList<Contact> contacts) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                ContactAdapter contactAdapter = new ContactAdapter(contacts);
                mRecyclerViewContact.setAdapter(contactAdapter);
            }
        });
        mGetContacts.execute(URL);
    }

    private void setProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.dialog_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetContacts != null && !mGetContacts.isCancelled()) {
            mGetContacts.cancel(true);
        }
    }
}
