package vn.asiantech.internship.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.activity.HttpHandler;
import vn.asiantech.internship.models.Contact;

/**
 * Created by ducle on 03/07/2017.
 * GetContacts is AsyncTask to get list contact
 */
public class GetContacts extends AsyncTask<String, Void, ArrayList<Contact>> {
    private static final String TAG = GetContacts.class.getSimpleName();
    private ArrayList<Contact> mContacts;
    private Context mContext;

    public GetContacts(Context context) {
        mContacts = new ArrayList<>();
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mContext instanceof OnUpdateListener) {
            ((OnUpdateListener) mContext).onShowProgressDialog();
        }
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(params[0]);
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("contacts") && jsonObject.optJSONArray("contacts") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonContact = jsonArray.getJSONObject(i);
                        String name;
                        String email;
                        String number;
                        if (jsonContact.has("name") && jsonContact.optString("name") != null) {
                            name = jsonContact.getString("name");
                        } else {
                            name = null;
                        }
                        if (jsonContact.has("email") && jsonContact.optString("email") != null) {
                            email = jsonContact.getString("email");
                        } else {
                            email = null;
                        }
                        if (jsonContact.has("phone") && jsonContact.optJSONObject("phone") != null) {
                            JSONObject jsonNumber = jsonContact.getJSONObject("phone");
                            if (jsonNumber.has("mobile") && jsonNumber.optString("mobile") != null) {
                                number = jsonNumber.getString("mobile");
                            } else {
                                number = null;
                            }
                        } else {
                            number = null;
                        }

                        Contact contact = new Contact(name, email, number);
                        mContacts.add(contact);
                    }
                }
            } catch (JSONException e) {
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        }
        Log.d("tag111", "doInBackground: ");
        return mContacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        ((OnUpdateListener) mContext).onCloseProgressDialog();
        Log.d("tag111", "onProgressUpdate: ");
        ((OnUpdateListener) mContext).onUpdateData(mContacts);
    }

    /**
     * interface to update UI
     */
    public interface OnUpdateListener {
        void onShowProgressDialog();

        void onCloseProgressDialog();

        void onUpdateData(ArrayList<Contact> contacts);
    }
}
