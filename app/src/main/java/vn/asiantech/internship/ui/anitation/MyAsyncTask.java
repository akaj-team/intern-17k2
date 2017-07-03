package vn.asiantech.internship.ui.anitation;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.models.Phone;

/**
 * Created by Thanh THien on 7/3/2017.
 * GetContacts
 */
class MyAsyncTask extends AsyncTask<String, Void, List<Contact>> {

    private static final String TAG = "MyAsyncTask";
    private OnGetContactListener mOnGetContactListener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnGetContactListener.openAsyncTask();
    }

    @Override
    protected List<Contact> doInBackground(String... arg0) {
        List<Contact> thisContacts = new ArrayList<>();
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(arg0[0]);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts;
                if (jsonObj.getJSONArray("contacts") != null) {
                    contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        if (c.getString("name") != null && c.getString("email") != null && c.getString("address") != null
                                && c.getString("gender") != null && c.getJSONObject("phone") != null) {
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String address = c.getString("address");
                            String gender = c.getString("gender");

                            // Phone node is JSON Object
                            JSONObject phoneObject = c.getJSONObject("phone");
                            String mobile = phoneObject.getString("mobile");
                            String home = phoneObject.getString("home");
                            String office = phoneObject.getString("office");

                            Phone phone = new Phone(home, mobile, office);
                            Contact contact = new Contact(name, email, address, gender, phone);
                            thisContacts.add(contact);
                        }
                    }
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return thisContacts;
    }

    void setOnGetContactListener(OnGetContactListener onGetContactListener) {
        mOnGetContactListener = onGetContactListener;
    }

    @Override
    protected void onPostExecute(List result) {
        mOnGetContactListener.finishAsyncTask(result);
        super.onPostExecute(result);
    }

    interface OnGetContactListener {
        void openAsyncTask();

        void finishAsyncTask(List<Contact> contacts);
    }
}
