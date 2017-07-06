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
    private static final String TAG = "GetContactAsync";
    private ProgressDialog mProgressDialog;
    private ArrayList<Contact> mContacts;
    private CallBackListContactListener mCallBackListener;

    public GetContactAsync(ProgressDialog progressDialog, CallBackListContactListener callBackListener) {
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
                if (jsonObject.has("contacts") && jsonObject.optJSONArray("contacts") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String id;
                        String name = null;
                        String email = null;
                        String mobile = null;

                        if (jsonArray.getJSONObject(i) != null) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("id") && object.optString("id") != null) {
                                id = object.getString("id");
                                if (object.has("name") && object.optString("name") != null) {
                                    name = object.getString("name");
                                    if (object.has("email") && object.optString("email") != null) {
                                        email = object.getString("email");
                                        if (object.has("phone") && object.optJSONObject("phone") != null) {
                                            JSONObject phone = object.getJSONObject("phone");
                                            if (phone.has("mobile") && phone.optString("mobile") != null) {
                                                mobile = phone.getString("mobile");
                                            } else {
                                                Log.e(TAG, "mobile null");
                                                mobile = null;
                                            }
                                        } else {
                                            Log.e(TAG, "phone null");
                                        }
                                    } else {
                                        Log.e(TAG, "email null");
                                        email = null;
                                    }
                                } else {
                                    Log.e(TAG, "name null");
                                    name = null;
                                }
                            } else {
                                Log.e(TAG, "id null");
                                id = null;
                            }
                            mContacts.add(new Contact(id, name, email, mobile));
                        } else {
                            Log.e(TAG, "JsonArray null");
                        }
                    }
                } else {
                    Log.e(TAG, "Can not find json");
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
    public interface CallBackListContactListener {
        void callBack(ArrayList<Contact> contacts);
    }
}
