package vn.asiantech.internship.ui.contact.annotation;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

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
import vn.asiantech.internship.adapters.ContactAnnotationAdapter;
import vn.asiantech.internship.handler.HttpHandler;
import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.models.Phone;

/**
 *
 * Created by quanghai on 04/07/2017.
 */
@EActivity(R.layout.activity_contact_annotation)
public class ContactActivity extends AppCompatActivity {
    private static final String URL = "http://api.androidhive.info/contacts/";
    private ProgressDialog mDialog;

    @ViewById(R.id.recyclerViewContact)
    RecyclerView mRecyclerViewContact;

    @AfterViews
    void initAdapter() {
        mDialog = new ProgressDialog(this);
        mDialog.setMessage(getResources().getString(R.string.dialog_message));
        mDialog.setCancelable(false);
        mDialog.show();
        getContacts();
    }

    @Background
    void getContacts() {
        List<Contact> contacts = new ArrayList<>();
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(URL);
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    String email = object.getString("email");
                    JSONObject phone = object.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    Phone newPhone = new Phone(mobile);
                    Contact contact = new Contact(name, email, newPhone);
                    contacts.add(contact);
                }
            } catch (JSONException e) {
                e.getMessage();
            }
        }
        showContact(contacts);
    }

    @UiThread
    void showContact(List<Contact> contacts) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mRecyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        ContactAnnotationAdapter adapter = new ContactAnnotationAdapter(contacts);
        mRecyclerViewContact.setAdapter(adapter);
    }
}
