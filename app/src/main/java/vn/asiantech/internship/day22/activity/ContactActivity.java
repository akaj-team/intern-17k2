package vn.asiantech.internship.day22.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day22.adapter.ContactAdapter;
import vn.asiantech.internship.day22.another.HttpHandler;
import vn.asiantech.internship.day22.model.Contact;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 03/07/2017.
 */
@EActivity(R.layout.activity_contact)
public class ContactActivity extends AppCompatActivity {
    private static final String TAG = ContactActivity.class.getName();
    private ProgressDialog mProgressDialog;
    private List<Contact> mContacts = new ArrayList<>();

    @ViewById(R.id.recyclerViewContact)
    RecyclerView mRecyclerView;

    @AfterViews
    public void initData() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        getDataApi();
    }

    @Background
    void getDataApi() {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String url = "http://api.androidhive.info/contacts/";
        String jsonStr = sh.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // Looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");

                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");

                    //  Adding to Contact
                    Contact contact = new Contact(id, name, email, address, gender, mobile);

                    // Adding contact to contact list
                    mContacts.add(contact);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                makeToastUI("Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            makeToastUI("Couldn't get json from server. Check LogCat for possible errors!");
        }
        finishGetApi();
    }

    @UiThread
    void makeToastUI(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG)
                .show();
    }

    @UiThread
    void finishGetApi() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new ContactAdapter(mContacts));
    }
}
