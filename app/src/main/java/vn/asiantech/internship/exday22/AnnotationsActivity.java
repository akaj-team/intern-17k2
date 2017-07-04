package vn.asiantech.internship.exday22;

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
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 03-07-2017.
 */
@EActivity(R.layout.activity_annotations)
public class AnnotationsActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private ArrayList<JsonItem> mJsonItems;

    @ViewById(R.id.rvJson)
    RecyclerView mRecyclerView;

    @AfterViews
    void initView() {
        mJsonItems = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        backGround();
    }

    @UiThread
    void initSetData() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(manager);
        JsonAdapter adapter = new JsonAdapter(mJsonItems);
        mRecyclerView.setAdapter(adapter);
    }

    @Background
    void backGround() {
        initData();
        initSetData();
    }

    public void initData() {
        HttpHandler httpHandler = new HttpHandler();
        // Making a request to url and getting response
        String url = "http://api.androidhive.info/contacts/";
        String jsonStr = httpHandler.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                JSONArray contacts = jsonObj.getJSONArray("contacts");
                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    String name = c.getString("name");
                    String email = c.getString("email");
                    // Phone node is JSON Object
                    JSONObject phone = c.getJSONObject("phone");
                    String mobile = phone.getString("mobile");
                    mJsonItems.add(new JsonItem(name, email, mobile));
                }
            } catch (final JSONException e) {
                Log.e("tag", "Json parsing error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
