package vn.asiantech.internship.day21.another;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.day21.activity.ContactActivity;
import vn.asiantech.internship.day21.adapter.ContactAdapter;
import vn.asiantech.internship.day21.model.Contact;

import static android.content.ContentValues.TAG;

/**
 * Create AsyncTask to get JSON by making HTTP call
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 03/07/2017.
 */
public class GetContact extends AsyncTask<Void, Void, List<Contact>> {
    private static final String TYPE_CONTACTS = "contacts";
    private static final String TYPE_NAME = "name";
    private static final String TYPE_ID = "id";
    private static final String TYPE_EMAIL = "email";
    private static final String TYPE_ADDRESS = "address";
    private static final String TYPE_GENDER = "gender";
    private static final String TYPE_PHONE = "phone";
    private static final String TYPE_MOBILE = "mobile";


    private ContactActivity mContactActivity;
    private List<Contact> mContacts = new ArrayList<>();

    public GetContact(ContactActivity contactActivity) {
        mContactActivity = contactActivity;
    }

    @Override
    protected List<Contact> doInBackground(Void... params) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String url = "http://api.androidhive.info/contacts/";
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
                mContactActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContactActivity.getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
            mContactActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContactActivity.getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }
        return mContacts;
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        mContactActivity.dismiss();
        ContactAdapter contactAdapter = new ContactAdapter(contacts);
        RecyclerView recyclerView = mContactActivity.getRecyclerView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContactActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(contactAdapter);
        mContactActivity.setRecyclerView(recyclerView);
    }
}
