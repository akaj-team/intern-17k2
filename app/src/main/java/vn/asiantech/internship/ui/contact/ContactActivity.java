package vn.asiantech.internship.ui.contact;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerViewContact = (RecyclerView) findViewById(R.id.recyclerViewContact);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewContact.setLayoutManager(linearLayoutManager);
        GetContacts getContacts = new GetContacts(this, new GetContacts.OnListener() {
            @Override
            public void onUpdateRecyclerView(ArrayList<Contact> contacts) {
                ContactAdapter contactAdapter = new ContactAdapter(contacts);
                mRecyclerViewContact.setAdapter(contactAdapter);
            }
        });
        getContacts.execute(URL);
    }
}
