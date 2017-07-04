package vn.asiantech.internship.annotations;

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

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by sony on 04/07/2017.
 */
@EActivity(R.layout.activity_contact_annotation)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    static final String URL = "http://api.androidhive.info/contacts/";
    private ArrayList<Contact> mContacts = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @ViewById(R.id.recyclerViewContactAnnotation)
    RecyclerView mRecyclerView;

    @AfterViews
    void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        asysnTask(URL);
    }

    @Background
    void asysnTask(String url) {
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(url);
        JSONArray contactArray;
        if (!TextUtils.equals(jsonString, "")) {
            try {
                JSONObject jsonObj = new JSONObject(jsonString);
                if (jsonObj.optJSONArray("contacts") != null) {
                    contactArray = jsonObj.getJSONArray("contacts");
                    if (contactArray != null) {
                        for (int i = 0; i < contactArray.length(); i++) {
                            JSONObject jsonObject = contactArray.getJSONObject(i);
                            Contact contact = new Contact();
                            String name = jsonObject.getString("name");
                            String mail = jsonObject.getString("email");
                            String phone = jsonObject.getJSONObject("phone").getString("mobile");
                            if (!TextUtils.equals(name, "")) {
                                contact.setName(name);
                                if (TextUtils.equals(mail, "")) {
                                    contact.setName(mail);
                                    if (TextUtils.equals(phone, "")) {
                                        contact.setName(phone);
                                    }
                                }
                            }
                            mContacts.add(contact);
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "IOException: " + e.toString());
            }
        }
        updateUI();
    }

    @UiThread
    void updateUI() {
        ContactAdapter adapter = new ContactAdapter(mContacts);
        mRecyclerView.setAdapter(adapter);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
