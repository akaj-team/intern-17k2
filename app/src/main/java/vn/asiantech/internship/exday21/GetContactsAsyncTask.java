package vn.asiantech.internship.exday21;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 03-07-2017.
 */
class GetContactsAsyncTask extends AsyncTask<ItemInformation, Void, ArrayList<ItemInformation>> {
    private ArrayList<ItemInformation> mItemInformations;
    private OnCallBack mOnCallBack;

    GetContactsAsyncTask(OnCallBack mOnCallBack) {
        this.mItemInformations = new ArrayList<>();
        this.mOnCallBack = mOnCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnCallBack.onShowDialog();
    }

    @Override
    protected ArrayList<ItemInformation> doInBackground(ItemInformation... arg0) {
        HttpHandler httpHandler = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://api.androidhive.info/contacts/";
        String jsonStr = httpHandler.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                if (jsonObj.has("contacts") && jsonObj.optJSONArray("contacts") != null) {
                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        if (contacts.getJSONObject(i) != null) {
                            JSONObject c = contacts.getJSONObject(i);
                            if (c.has("name") && c.optString("name") != null) {
                                String name = c.getString("name");
                                if (c.has("email") && c.optString("email") != null) {
                                    String email = c.getString("email");
                                    // Phone node is JSON Object
                                    if (c.has("phone") && c.optJSONObject("phone") != null) {
                                        JSONObject phone = c.getJSONObject("phone");
                                        if (phone.has("mobile") && phone.optString("mobile") != null) {
                                            String mobile = phone.getString("mobile");
                                            mItemInformations.add(new ItemInformation(name, email, mobile));
                                        } else {
                                            Log.e("tag1", "mobile null");
                                        }
                                    } else {
                                        Log.e("tag2", "phone null");
                                    }
                                } else {
                                    Log.e("tag3", "email null");
                                }
                            } else {
                                Log.e("tag4", "name null");
                            }
                        } else {
                            Log.e("tag5", "contact null");
                        }
                    }
                } else {
                    Log.e("tag6", "contacts null");
                }
            } catch (JSONException e) {
                Log.e("tag7", "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e("tag8", "json str null");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemInformation> itemInformations) {
        super.onPostExecute(itemInformations);
        mOnCallBack.onSetAdapter(mItemInformations);
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by datbu on 03-07-2017.
     */
    interface OnCallBack {
        void onShowDialog();

        void onSetAdapter(ArrayList<ItemInformation> itemInformations);
    }
}