package vn.asiantech.internship.contact;

import android.os.AsyncTask;
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
        String name = "";
        String email = "";
        String mobile = "";
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject.has("contacts") && jsonObject.optJSONArray("contacts") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.getJSONObject(i) != null) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("name") && object.optString("name") != null) {
                                name = object.getString("name");
                            } else {
                                Log.d(TAG, "name null or object does not have name column");
                            }
                            if (object.has("email") && object.optString("email") != null) {
                                email = object.getString("email");
                            } else {
                                Log.d(TAG, "email null or object does not have email column");
                            }
                            if (object.has("phone") && object.optJSONObject("phone") != null) {
                                JSONObject phoneObject = object.getJSONObject("phone");
                                if (phoneObject.has("mobile") && phoneObject.optString("mobile") != null) {
                                    mobile = phoneObject.getString("mobile");
                                } else {
                                    Log.d(TAG, "mobile null or object does not have mobile column");
                                }
                            } else {
                                Log.d(TAG, "phone null or object does not have phone column");
                            }
                        } else {
                            Log.d(TAG, "contacts null or jsonObject does not have contacts column");
                        }
                        contacts.add(new Contact(name, email, mobile));
                    }
                } else {
                    Log.d(TAG, "object null");
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException" + e.toString());
            }
        } else {
            Log.d(TAG, "jsonString null");
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        mListener.onProcessFinish(contacts);
    }

    /**
     * Used to get contacts from AsyncTask to ContactActivity
     */
    interface OnAsyncResponseListener {
        void onProcessFinish(ArrayList<Contact> contacts);
    }
}
