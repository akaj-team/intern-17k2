package vn.asiantech.internship.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.models.Phone;
import vn.asiantech.internship.ui.contact.HttpHandler;

/**
 *
 * Created by quanghai on 03/07/2017.
 */
public class GetContacts extends AsyncTask<String, Void, ArrayList<Contact>> {
    private ProgressDialog mProgressDialog;
    private Context mContext;
    private OnListener mOnListener;

    public GetContacts(Context context, OnListener onListener) {
        mContext = context;
        mOnListener = onListener;
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
    protected ArrayList<Contact> doInBackground(String... url) {
        ArrayList<Contact> contacts = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(url[0]);
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    String email = object.getString("email");
                    JSONObject phone = object.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    Phone newPhone = new Phone(mobile);
                    Contact contact = new Contact(name, email, newPhone);
                    contacts.add(contact);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        Log.d("xxx", "onPostExecute: " + contacts.size());
        if (mOnListener != null) {
            mOnListener.onUpdateRecyclerView(contacts);
        }
    }

    /**
     * Created by quanghai on 03/07/2017.
     */
    public interface OnListener {
        void onUpdateRecyclerView(ArrayList<Contact> contacts);
    }
}
