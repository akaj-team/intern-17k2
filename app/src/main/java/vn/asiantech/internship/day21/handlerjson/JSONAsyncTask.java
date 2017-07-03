package vn.asiantech.internship.day21.handlerjson;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.day21.models.Contact;
import vn.asiantech.internship.day21.models.Phone;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class JSONAsyncTask extends AsyncTask<Void, Void, ArrayList<Contact>> {

    private static final String URL = "http://api.androidhive.info/contacts/";

    private ArrayList<Contact> mContacts;
    private OnUpdateUiListener mOnUpdateUiListener;

    /**
     * Interface update UI
     */
    public interface OnUpdateUiListener {
        void onShowDialog();

        void onUpdateRecyclerView(ArrayList<Contact> contacts);
    }

    public JSONAsyncTask(OnUpdateUiListener onUpdateUiListener) {
        mOnUpdateUiListener = onUpdateUiListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mOnUpdateUiListener.onShowDialog();
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
                if (jsonObject.has("contacts")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.has("name") && object.has("email") && object.has("gender")
                                && object.has("address") && object.has("id") && object.has("phone")) {
                            JSONObject jsonPhone = object.getJSONObject("phone");
                            if (jsonPhone.has("mobile") && jsonPhone.has("home") && jsonPhone.has("office")) {
                                phone = new Phone(jsonPhone.getString("mobile"),
                                        jsonPhone.getString("home"), jsonPhone.getString("office"));
                                contact = new Contact(object.getString("id"), object.getString("name"),
                                        object.getString("email"), object.getString("gender"),
                                        object.getString("address"), phone);
                                mContacts.add(contact);
                            }else {
                                return mContacts;
                            }
                        }
                    }
                } else {
                    Log.e("JSON isn't exist", "" + jsonObject.has("contacts"));
                }
            } catch (JSONException e) {
                Log.e("JSONException", "JSONException: " + e.getMessage());
            }
        }
        return mContacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        mOnUpdateUiListener.onUpdateRecyclerView(mContacts);
    }
}
