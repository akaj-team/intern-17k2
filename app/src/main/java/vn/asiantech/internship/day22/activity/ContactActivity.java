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

    private static final String TYPE_CONTACTS = "contacts";
    private static final String TYPE_NAME = "name";
    private static final String TYPE_ID = "id";
    private static final String TYPE_EMAIL = "email";
    private static final String TYPE_ADDRESS = "address";
    private static final String TYPE_GENDER = "gender";
    private static final String TYPE_PHONE = "phone";
    private static final String TYPE_MOBILE = "mobile";

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
        String url = "https://api.androidhive.info/contacts/";
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                String id = " ", name = " ", email = " ", address = " ", gender = " ", mobile = " ";
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                if (jsonObj.has(TYPE_CONTACTS) && jsonObj.optJSONArray(TYPE_CONTACTS) != null) {
                    JSONArray contacts = jsonObj.getJSONArray(TYPE_CONTACTS);

                    // Looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        if (c.has(TYPE_ID) && c.optString(TYPE_ID) != null) {
                            id = c.getString(TYPE_ID);
                        }
                        if (c.has(TYPE_NAME) && c.optString(TYPE_NAME) != null) {
                            name = c.getString(TYPE_NAME);
                        }
                        if (c.has(TYPE_ADDRESS) && c.optString(TYPE_ADDRESS) != null) {
                            address = c.getString(TYPE_ADDRESS);
                        }
                        if (c.has(TYPE_EMAIL) && c.optString(TYPE_EMAIL) != null) {
                            email = c.getString(TYPE_EMAIL);
                        }

                        if (c.has(TYPE_GENDER) && c.optString(TYPE_GENDER) != null) {
                            gender = c.getString(TYPE_GENDER);
                        }

                        // Phone node is JSON Object
                        if (c.has(TYPE_PHONE) && c.optJSONObject(TYPE_PHONE) != null) {
                            JSONObject phone = c.getJSONObject(TYPE_PHONE);
                            if (phone.has(TYPE_MOBILE) && phone.optString(TYPE_MOBILE) != null) {
                                mobile = phone.getString(TYPE_MOBILE);
                            }
                        }

                        //  Adding to Contact
                        Contact contact = new Contact(id, name, email, address, gender, mobile);

                        // Adding contact to contact list
                        mContacts.add(contact);
                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                makeToastUI(getString(R.string.json_passing_error, e.getMessage()));
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            makeToastUI(getString(R.string.error_http_json));
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
