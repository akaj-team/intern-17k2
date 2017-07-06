package vn.asiantech.internship.asynctask;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.models.Phone;
import vn.asiantech.internship.handler.HttpHandler;
import vn.asiantech.internship.ui.contact.ContactActivity;

/**
 *
 * Created by quanghai on 03/07/2017.
 */
public class GetContacts extends AsyncTask<String, Void, ArrayList<Contact>> {
    private static final String TAG = ContactActivity.class.getSimpleName();
    private OnUpdateViewListener mOnUpdateViewListener;

    public GetContacts(OnUpdateViewListener onUpdateViewListener) {
        mOnUpdateViewListener = onUpdateViewListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... url) {
        ArrayList<Contact> contacts = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(url[0]);
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject.has("contacts") && jsonObject.getJSONArray("contacts") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject phone;
                        String name;
                        String mobile;
                        String email = null;
                        Phone newPhone = null;
                        if (jsonArray.getJSONObject(i) != null) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("name") && !TextUtils.isEmpty(object.getString("name"))) {
                                name = object.getString("name");
                                if (object.has("email") && !TextUtils.isEmpty(object.getString("email"))) {
                                    email = object.getString("email");
                                    if (object.has("phone") && object.getJSONObject("phone") != null) {
                                        phone = object.getJSONObject("phone");
                                        if (phone.has("mobile") && !TextUtils.isEmpty(object.getString("mobile"))) {
                                            mobile = phone.getString("mobile");
                                        } else {
                                            Log.e(TAG, "mobile null");
                                            mobile = null;
                                        }
                                        newPhone = new Phone(mobile);
                                    } else {
                                        newPhone = null;
                                        Log.e(TAG, "phone null");
                                    }
                                } else {
                                    email = null;
                                    Log.e(TAG, "email null");
                                }
                            } else {
                                name = null;
                                Log.e(TAG, "name null");
                            }
                            Contact contact = new Contact(name, email, newPhone);
                            contacts.add(contact);
                        } else {
                            Log.e(TAG, "jsonObject null ");
                        }
                    }
                } else {
                    Log.e(TAG, "Json is not exist");
                }
            } catch (JSONException e) {
                e.getMessage();
                return null;
            }
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        if (mOnUpdateViewListener != null && contacts.size() > 0) {
            mOnUpdateViewListener.onUpdateRecyclerView(contacts);
        }
    }

    /**
     * Created by quanghai on 03/07/2017.
     */
    public interface OnUpdateViewListener {
        void onUpdateRecyclerView(ArrayList<Contact> contacts);
    }
}
