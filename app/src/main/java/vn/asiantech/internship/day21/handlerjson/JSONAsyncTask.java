package vn.asiantech.internship.day21.handlerjson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.day21.models.Contact;
import vn.asiantech.internship.day21.models.Phone;
import vn.asiantech.internship.day21.ui.JsonAdapter;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class JSONAsyncTask extends AsyncTask<Void, Void, ArrayList<Contact>> {

    private static final String URL = "http://api.androidhive.info/contacts/";

    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;

    private Context mContext;
    private ArrayList<Contact> mContacts;

    public JSONAsyncTask(Context context, RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<Contact> doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        mContacts = new ArrayList<>();
        String jsonString = httpHandler.makeServiceCall(URL);
        Contact contact;
        Phone phone;
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    JSONObject jsonPhone = c.getJSONObject("phone");
                    phone = new Phone(jsonPhone.getString("mobile"),
                            jsonPhone.getString("home"), jsonPhone.getString("office"));
                    contact = new Contact(c.getString("id"), c.getString("name"),
                            c.getString("email"), c.getString("gender"),
                            c.getString("address"), phone);
                    mContacts.add(contact);
                }
            } catch (JSONException e) {
                Log.e("JSONException", "JSONException: " + e.getMessage());
            }
        } else {
            Log.e("at-dinhvo", "doInBackground: ");
        }
        return mContacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
        JsonAdapter jsonAdapter = new JsonAdapter(mContacts);
        mRecyclerView.setAdapter(jsonAdapter);
    }
}
