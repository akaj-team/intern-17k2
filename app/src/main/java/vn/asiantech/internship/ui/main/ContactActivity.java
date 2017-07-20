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
        mTvTitle.setText(getString(R.string.contact));
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
                    contacts.add(contact);
                }
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
