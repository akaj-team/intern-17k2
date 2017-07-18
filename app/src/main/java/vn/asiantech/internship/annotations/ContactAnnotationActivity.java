package vn.asiantech.internship.annotations;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class ContactAnnotationActivity extends AppCompatActivity {
    private static final String TAG = ContactAnnotationActivity.class.getSimpleName();
    private static final String URL = "http://api.androidhive.info/contacts/";
    private final ArrayList<Contact> mContacts = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    @ViewById(R.id.recyclerViewContactAnnotation)
    RecyclerView mRecyclerView;

    @AfterViews
    void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressDialog = new ProgressDialog(ContactAnnotationActivity.this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        asysnTask(URL);
    }

    @Background
    void asysnTask(String url) {
        HttpHandler httpHandler = new HttpHandler();
        String jsonString = httpHandler.makeServiceCall(url);
        String name = "";
        String email = "";
        String mobile = "";
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject.has("contacts") && jsonObject.optJSONArray("contacts") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.getJSONObject(i) != null) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("name") && object.optString("name") != null) {
                                name = object.getString("name");
                            } else {
                                Log.d(TAG, "name null or object does not have name column");
                            }
                            if (object.has("email") && object.optString("email") != null) {
                                email = object.getString("email");
                            } else {
                                Log.d(TAG, "email null or object does not have email column");
                            }
                            if (object.has("phone") && object.optJSONObject("phone") != null) {
                                JSONObject phoneObject = object.getJSONObject("phone");
                                if (phoneObject.has("mobile") && phoneObject.optString("mobile") != null) {
                                    mobile = phoneObject.getString("mobile");
                                } else {
                                    Log.d(TAG, "mobile null or object does not have mobile column");
                                }
                            } else {
                                Log.d(TAG, "phone null or object does not have phone column");
                            }
                        } else {
                            Log.d(TAG, "contacts null or jsonObject does not have contacts column");
                        }
                        mContacts.add(new Contact(name, email, mobile));
                    }
                } else {
                    Log.d(TAG, "object null");
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSONException" + e.toString());
            }
        } else {
            Log.d(TAG, "jsonString null");
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
