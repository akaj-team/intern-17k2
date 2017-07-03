package vn.asiantech.internship.contact;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 03/07/2017.
 */
public class MainActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private ArrayList<Contact> mContacts = new ArrayList<>();
    public static String URL = "http://api.androidhive.info/contacts/";
    private GetContacts mGetContacts = new GetContacts(new GetContacts.OnAsyncResponseListener() {
        @Override
        public void onProcessFinish(ArrayList<Contact> contacts) {
            mContacts.addAll(contacts);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            ContactAdapter adapter = new ContactAdapter(mContacts);
            mRecyclerView.setAdapter(adapter);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewContact);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mGetContacts.execute(URL);
    }
}
