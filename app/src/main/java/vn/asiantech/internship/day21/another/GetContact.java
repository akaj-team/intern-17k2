package vn.asiantech.internship.day21.another;

import android.app.ProgressDialog;
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
    private ContactActivity mContactActivity;
    private List<Contact> mContacts = new ArrayList<>();
    private static final String sUrl = "http://api.androidhive.info/contacts/";

    public GetContact(ContactActivity contactActivity) {
        mContactActivity = contactActivity;
    }

    @Override
    protected List<Contact> doInBackground(Void... params) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(sUrl);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
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
                    String home = phone.getString("home");
                    String office = phone.getString("office");

                    //  Adding to Contact
                    Contact contact = new Contact(id, name, email, address, gender, mobile);

                    // Adding contact to contact list
                    mContacts.add(contact);
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
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressDialog progressDialog = new ProgressDialog(mContactActivity);
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCancelable(false);
        mContactActivity.setProgressDialog(progressDialog);
        mContactActivity.getProgressDialog().show();
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        // Dismiss the progress dialog
        if (mContactActivity.getProgressDialog().isShowing())
            mContactActivity.getProgressDialog().dismiss();
        ContactAdapter contactAdapter = new ContactAdapter(contacts);
        RecyclerView recyclerView = mContactActivity.getRecyclerView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContactActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(contactAdapter);
        mContactActivity.setRecyclerView(recyclerView);
    }
}
