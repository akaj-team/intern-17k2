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
        if (!TextUtils.isEmpty(jsonString)) {
            getData(jsonString);
        }
        return mContacts;
    }

    private boolean checkValueJSon(JSONObject jsonObject, String key) {
        return (jsonObject.has(key) && !jsonObject.isNull(key));
    }

    private void getData(String jsonString) {
        Contact contact;
        Phone phone;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (checkValueJSon(jsonObject, "contacts")) {
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.get(i) != null) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (checkValueJSon(object, "name")
                                    && checkValueJSon(object, "email")
                                    && checkValueJSon(object, "gender")
                                    && checkValueJSon(object, "address")
                                    && checkValueJSon(object, "id")
                                    && checkValueJSon(object, "phone")) {
                                JSONObject jsonPhone = object.getJSONObject("phone");
                                if (jsonPhone != null) {
                                    if (checkValueJSon(jsonPhone, "mobile")
                                            && checkValueJSon(jsonPhone, "home")
                                            && checkValueJSon(jsonPhone, "office")) {
                                        phone = new Phone(jsonPhone.getString("mobile"),
                                                jsonPhone.getString("home"), jsonPhone.getString("office"));
                                        contact = new Contact(object.getString("id"), object.getString("name"),
                                                object.getString("email"), object.getString("gender"),
                                                object.getString("address"), phone);
                                        mContacts.add(contact);
                                    }
                                }
                            }
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

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        mOnUpdateUiListener.onUpdateRecyclerView(mContacts);
    }
}
