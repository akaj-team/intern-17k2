package vn.asiantech.internship.ui.contact.annotation;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

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
    private static final String TAG = ContactActivity.class.getSimpleName();
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
                Log.e("JSONException", "JSONException: " + e.getMessage());
            }
            showContact(contacts);
        }
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
