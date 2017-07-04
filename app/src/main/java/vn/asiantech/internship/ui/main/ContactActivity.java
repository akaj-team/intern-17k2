package vn.asiantech.internship.ui.main;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.ContactAdapter;
import vn.asiantech.internship.background.HttpHandler;
import vn.asiantech.internship.models.Contact;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/3/2017.
 */
@EActivity(R.layout.activity_contact)
public class ContactActivity extends AppCompatActivity {

    private static final String URL = "http://api.androidhive.info/contacts/";
    private ProgressDialog mProgressDialog;

    @ViewById(R.id.recyclerViewContact)
    RecyclerView mRecyclerViewContacts;
    @ViewById(R.id.tvTitle)
    TextView mTvTitle;

    @AfterViews
    void afterView() {
        mRecyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        loadData(URL);
    }

    @Background
    void loadData(String url) {
        preLoadData();
        HttpHandler httpHandler = new HttpHandler();
        String json = httpHandler.makeServiceCall(url);
        List<Contact> contacts = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("contacts");
            for (int i = 0; i < jsonArray.length(); i++) {
                contacts.add(new Contact(jsonArray.getJSONObject(i).getString("name"), jsonArray.getJSONObject(i).getString("email"), jsonArray.getJSONObject(i).getJSONObject("phone").getString("mobile")));
            }
        } catch (JSONException e) {
            Log.i("tag11", e.getMessage());
        }
        dataLoaded(contacts);
    }

    @UiThread
    void preLoadData() {
        mProgressDialog.show();
    }

    @UiThread
    void dataLoaded(List<Contact> contacts) {
        ContactAdapter adapter = new ContactAdapter(contacts);
        mRecyclerViewContacts.setAdapter(adapter);
        mProgressDialog.dismiss();
    }
}
