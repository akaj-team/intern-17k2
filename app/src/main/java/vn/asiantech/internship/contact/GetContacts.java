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
        JSONArray contactArray;
        String name = "";
        String mail = "";
        String mobile = "";
        JSONObject phone;
        String jsonString = httpHandler.makeServiceCall(strings[0]);
        if (!TextUtils.equals(jsonString, "")) {
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                if (jsonObj.has("contacts") && jsonObj.optJSONArray("contacts") != null) {
                    contactArray = jsonObj.getJSONArray("contacts");
                    for (int i = 0; i < contactArray.length(); i++) {
                        Contact contact = new Contact();
                        JSONObject jsonObject = contactArray.getJSONObject(i);
                        if (jsonObj.has("name") && jsonObj.optString("name") != null) {
                            name = jsonObject.getString("name");
                        }
                        if (jsonObj.has("email") && jsonObj.optString("email") != null) {
                            mail = jsonObject.getString("email");
                        }
                        if (jsonObj.has("phone") && jsonObj.optJSONObject("phone") != null) {
                            phone = jsonObj.getJSONObject("phone");
                            if (phone.has("mobile") && phone.optString("mobile") != null)
                                mobile = phone.getString("mobile");
                        }
                        if (!TextUtils.equals(name, "")) {
                            contact.setName(name);
                            if (TextUtils.equals(mail, "")) {
                                contact.setName(mail);
                                if (TextUtils.equals(mobile, "")) {
                                    contact.setName(mobile);
                                }
                            }
                        }
                        contacts.add(contact);
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
