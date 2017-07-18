package vn.asiantech.internship.background;

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

    private ArrayList<Contact> mContacts;
    private TaskStatusListener mListener;

    public GetContactsAsyncTask(TaskStatusListener listener) {
        this.mContacts = new ArrayList<>();
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onPrepareExecute();
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(params[0]);
        Log.i("tag11", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("contacts") && jsonObject.optJSONArray("contacts") != null) {
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Contact contact = new Contact();
                    JSONObject jsonObjectContact = jsonArray.getJSONObject(i);
                    if (jsonObjectContact.has("name") && jsonObjectContact.optString("name") != null) {
                        contact.setName(jsonObjectContact.getString("name"));
                    }
                    if (jsonObjectContact.has("email") && jsonObjectContact.optString("email") != null) {
                        contact.setEmail(jsonObjectContact.getString("email"));
                    }
                    if (jsonObjectContact.has("phone") && jsonObjectContact.optJSONObject("phone") != null) {
                        JSONObject phone = jsonObjectContact.getJSONObject("phone");
                        if (phone.has("mobile") && phone.optString("mobile") != null) {
                            contact.setPhone(phone.getString("mobile"));
                        }
                    }
                    Log.i("tag11", contact.getName());
                    mContacts.add(contact);
                }
            }
        } catch (JSONException e) {
            Log.i("tag11", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);
        mListener.onComplete(mContacts);
    }

    /**
     * This interface used to handle when this task complete
     */
    public interface TaskStatusListener {
        void onPrepareExecute();

        void onComplete(ArrayList<Contact> contacts);
    }
}
