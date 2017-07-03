package vn.asiantech.internship.background;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.models.Contact;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/3/2017.
 */
public class GetContactsAsyncTask extends AsyncTask<String, Void, ArrayList<Contact>> {

    private ProgressDialog mProgressDialog;
    private ArrayList<Contact> mContacts;
    private CallBackListener mListener;

    public GetContactsAsyncTask(ProgressDialog progressDialog, CallBackListener listener) {
        this.mContacts = new ArrayList<>();
        this.mProgressDialog = progressDialog;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(params[0]);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");
            for (int i = 0; i < jsonArray.length(); i++) {
                mContacts.add(new Contact(jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("email"), jsonArray.getJSONObject(i).getJSONObject("phone").getString("mobile")));
            }
        } catch (JSONException e) {
            Log.i("tag11", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        mProgressDialog.dismiss();
        mListener.onComplete(mContacts);
    }

    /**
     * This interface used to handle when this task complete
     */
    public interface CallBackListener {
        void onComplete(ArrayList<Contact> contacts);
    }
}
