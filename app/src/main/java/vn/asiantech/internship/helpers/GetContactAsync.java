package vn.asiantech.internship.helpers;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.models.Contact;

/**
 * AsyncTask.
 * Created by huypham on 03-Jul-17.
 */
public class GetContactAsync extends AsyncTask<String, Void, ArrayList<Contact>> {
    private ProgressDialog mProgressDialog;
    private ArrayList<Contact> mContacts;
    private CallBackListener mCallBackListener;

    public GetContactAsync(ProgressDialog progressDialog, CallBackListener callBackListener) {
        mProgressDialog = progressDialog;
        mContacts = new ArrayList<>();
        mCallBackListener = callBackListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        HttpHandler httpHandler = new HttpHandler();
        String url = "http://api.androidhive.info/contacts/";
        String jsonString = httpHandler.makeServiceCall(url);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    String id = object.getString("id");
                    String name = object.getString("name");
                    String email = object.getString("email");

                    JSONObject phone = object.getJSONObject("phone");
                    String mobile = phone.getString("mobile");

                    mContacts.add(new Contact(id, name, email, mobile));
                }
            } catch (JSONException e) {
                Log.e("doInBackground: ", e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mCallBackListener.callBack(mContacts);
    }

    /**
     * Interface Callback.
     * Created by huypham on 03-Jul-17.
     */
    public interface CallBackListener {
        void callBack(ArrayList<Contact> contacts);
    }
}
