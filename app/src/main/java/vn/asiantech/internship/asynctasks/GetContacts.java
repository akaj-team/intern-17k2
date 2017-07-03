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
        mContext=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((OnUpdateListener) mContext).onShowProgressDialog();
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        ArrayList<Contact> contacts = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(params[0]);
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonContact = jsonArray.getJSONObject(i);
                    String name = jsonContact.getString("name");
                    String email = jsonContact.getString("email");
                    JSONObject jsonNumber = jsonContact.getJSONObject("phone");
                    String number = jsonNumber.getString("mobile");
                    Contact contact = new Contact(name, email, number);
                    contacts.add(contact);
                }
            } catch (JSONException e) {
                Log.d(TAG, "JSONException: " + e.getMessage());
            }
        }
        Log.d("tag111", "doInBackground: ");
        mContacts = contacts;
        return contacts;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        ((OnUpdateListener) mContext).onCloseProgressDialog();
        Log.d("tag111", "onProgressUpdate: ");
        ((OnUpdateListener) mContext).onUpdateData(mContacts);
    }

    public interface OnUpdateListener {
        void onShowProgressDialog();

        void onCloseProgressDialog();

        void onUpdateData(ArrayList<Contact> contacts);
    }
}
