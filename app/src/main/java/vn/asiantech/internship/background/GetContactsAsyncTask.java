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
    private CallBackListener mListener;

    public GetContactsAsyncTask(CallBackListener listener) {
        this.mContacts = new ArrayList<>();
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onTaskPreExecute();
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
        mListener.onComplete(mContacts);
    }

    /**
     * This interface used to handle when this task complete
     */
    public interface CallBackListener {
        void onTaskPreExecute();

        void onComplete(ArrayList<Contact> contacts);
    }
}
