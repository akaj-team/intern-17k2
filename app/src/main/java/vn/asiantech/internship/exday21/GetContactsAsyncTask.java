package vn.asiantech.internship.exday21;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 03-07-2017.
 */
class GetContactsAsyncTask extends AsyncTask<JsonItem, Void, ArrayList<JsonItem>> {
    private ArrayList<JsonItem> mJsonItems;
    private OnCallBack mOnCallBack;

    GetContactsAsyncTask(OnCallBack mOnCallBack) {
        this.mJsonItems = new ArrayList<>();
        this.mOnCallBack = mOnCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnCallBack.onCall();
    }

    @Override
    protected ArrayList<JsonItem> doInBackground(JsonItem... arg0) {
        HttpHandler httpHandler = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://api.androidhive.info/contacts/";
        String jsonStr = httpHandler.makeServiceCall(url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray contacts = jsonObj.getJSONArray("contacts");
                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String name = c.getString("name");
                    String email = c.getString("email");
                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    mJsonItems.add(new JsonItem(name, email, mobile));
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<JsonItem> jsonItems) {
        super.onPostExecute(jsonItems);
        mOnCallBack.onCallBack(mJsonItems);
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by datbu on 03-07-2017.
     */
    interface OnCallBack {
        void onCall();

        void onCallBack(ArrayList<JsonItem> jsonItems);
    }
}