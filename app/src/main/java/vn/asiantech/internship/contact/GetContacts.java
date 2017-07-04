package vn.asiantech.internship.contact;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 03/07/2017.
 */
class GetContacts extends AsyncTask<String, Void, ArrayList<Contact>> {
    private static final String TAG = GetContacts.class.getSimpleName();
    private OnAsyncResponseListener mListener = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    GetContacts(OnAsyncResponseListener listener) {
        this.mListener = listener;
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... strings) {
        ArrayList<Contact> contacts = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(strings[0]);
        JSONArray contactArray;
        if (!TextUtils.equals(jsonString, "")) {
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                if (jsonObj.optJSONArray("contacts") != null) {
                    contactArray = jsonObj.getJSONArray("contacts");
                    if (contactArray != null) {
                        for (int i = 0; i < contactArray.length(); i++) {
                            JSONObject jsonObject = contactArray.getJSONObject(i);
                            Contact contact = new Contact();
                            String name = jsonObject.getString("name");
                            String mail = jsonObject.getString("email");
                            String phone = jsonObject.getJSONObject("phone").getString("mobile");
                            if (!TextUtils.equals(name, "")) {
                                contact.setName(name);
                                if (TextUtils.equals(mail, "")) {
                                    contact.setName(mail);
                                    if (TextUtils.equals(phone, "")) {
                                        contact.setName(phone);
                                    }
                                }
                            }
                            contacts.add(contact);
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "IOException: " + e.toString());
            }
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        mListener.onProcessFinish(contacts);
    }

    /*
    * Used to get contacts from AsyncTask to MainActivity
     */
    interface OnAsyncResponseListener {
        void onProcessFinish(ArrayList<Contact> contacts);
    }
}
