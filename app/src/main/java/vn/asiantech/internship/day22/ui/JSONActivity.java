package vn.asiantech.internship.day22.ui;

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
import vn.asiantech.internship.day22.handlerjson.HttpHandler;
import vn.asiantech.internship.day22.models.Contact;
import vn.asiantech.internship.day22.models.Phone;

/**
 * JSONActivity show list contact
 */
@EActivity(R.layout.activity_json)
public class JSONActivity extends AppCompatActivity {

    private static final String URL = "http://api.androidhive.info/contacts/";

    private ProgressDialog mProgressDialog;

    private ArrayList<Contact> mContacts;

    @ViewById(R.id.recyclerViewJSON)
    RecyclerView mRecyclerView;

    @AfterViews
    void afterView() {
        handlerHttpUrl();
    }

    @Background
    void handlerHttpUrl() {
        HttpHandler httpHandler = new HttpHandler();
        mContacts = new ArrayList<>();
        String jsonString = httpHandler.makeServiceCall(URL);
        Contact contact;
        Phone phone;
        if (!TextUtils.isEmpty(jsonString)) {
            showDialog();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject.has("contacts")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        if (object.has("name") && object.has("email") && object.has("gender")
                                && object.has("address") && object.has("id") && object.has("phone")) {
                            JSONObject jsonPhone = object.getJSONObject("phone");
                            if (jsonPhone.has("mobile") && jsonPhone.has("home") && jsonPhone.has("office")) {
                                phone = new Phone(jsonPhone.getString("mobile"),
                                        jsonPhone.getString("home"), jsonPhone.getString("office"));
                                contact = new Contact(object.getString("id"), object.getString("name"),
                                        object.getString("email"), object.getString("gender"),
                                        object.getString("address"), phone);
                                mContacts.add(contact);
                            }
                        }
                    }
                } else {
                    Log.e("Exist", "JSON isn't exist: " + !jsonObject.has("contacts"));
                }
            } catch (JSONException e) {
                Log.e("JSONException", "JSONException: " + e.getMessage());
            }
            updateUI();
        }
    }

    @UiThread
    void updateUI() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        JsonAdapter adapter = new JsonAdapter(mContacts);
        mRecyclerView.setAdapter(adapter);
    }

    @UiThread
    void showDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.dialog_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
}
