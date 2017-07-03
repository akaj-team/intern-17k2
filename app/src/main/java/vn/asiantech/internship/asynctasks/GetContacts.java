package vn.asiantech.internship.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.activity.ContactActivity;
import vn.asiantech.internship.activity.HttpHandler;
import vn.asiantech.internship.adapter.ContactAdapter;
import vn.asiantech.internship.models.Contact;

/**
 * Created by ducle on 03/07/2017.
 * GetContacts is AsyncTask to get list contact
 */
public class GetContacts extends AsyncTask<String, Void, ArrayList<Contact>> {
    private static final String TAG=GetContacts.class.getSimpleName();
    private List<Contact> mContacts;
    private RecyclerView mRecyclerViewContact;
    private ContactAdapter mContactAdapter;
    private ProgressDialog mProgressDialog;
    private Context mContext;
    public GetContacts(Context context) {
        mContext=context;
        mContacts=new ArrayList<>();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((OnUpdateListener) mContacts).onShowProgressDialog();
    }

    @Override
    protected ArrayList<Contact> doInBackground(String... params) {
        ArrayList<Contact> contacts=new ArrayList<>();
        HttpHandler httpHandler=new HttpHandler();
        String json=httpHandler.makeServiceCall(params[0]);
        if (json!=null){
            try {
                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray=jsonObject.getJSONArray("contacs");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonContact=jsonArray.getJSONObject(i);
                    String name=jsonContact.getString("name");
                    String email=jsonContact.getString("email");
                    JSONObject jsonNumber=jsonContact.getJSONObject("phone");
                    String number=jsonNumber.getString("mobile");
                    Contact contact=new Contact(name,email,number);
                    contacts.add(contact);
                }
            } catch (JSONException e) {
                Log.d(TAG, "JSONException: "+e.getMessage());
            }
        }
        return contacts;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }

    }
    public interface OnUpdateListener{
        void onShowProgressDialog();
        void onUpdateData(ArrayList<Contact> contacts);
    }
}
