package vn.asiantech.internship.ui.jsonparsing;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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
import vn.asiantech.internship.helpers.HttpHandler;
import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.ui.adapters.JsonParsingAdapter;

/**
 * Json Parsing use annotation Activity.
 * Created by AnhHuy on 04-Jul-17.
 */
@EActivity(R.layout.activity_json_parsing)
public class JsonAnnotationActivity extends AppCompatActivity {
    private static final String TAG = "JsonAnnotationActivity";
    private ArrayList<Contact> mContacts;
    private ProgressDialog mProgressDialog;

    @ViewById(R.id.recyclerViewParsing)
    RecyclerView mRecyclerViewJson;

    @AfterViews
    void afterView() {
        mContacts = new ArrayList<>();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.progress_dialog));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        inBackground();
    }

    @Background
    void inBackground() {
        doBackground();
        initView();
    }

    @UiThread
    void initView() {
        initRecyclerView();
        closeProgressDialog();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initRecyclerView() {
        JsonParsingAdapter adapter = new JsonParsingAdapter(mContacts);
        mRecyclerViewJson.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewJson.setAdapter(adapter);
        mRecyclerViewJson.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                outRect.set(20, position == 0 ? 15 : 20, 20, 20);
            }
        });
    }

    private void doBackground() {
        HttpHandler httpHandler = new HttpHandler();
        String url = "http://api.androidhive.info/contacts/";
        String jsonString = httpHandler.makeServiceCall(url);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject.has("contacts") && jsonObject.optJSONArray("contacts") != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String id;
                        String name = null;
                        String email = null;
                        String mobile = null;

                        if (jsonArray.getJSONObject(i) != null) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("id") && object.optString("id") != null) {
                                id = object.getString("id");
                                if (object.has("name") && object.optString("name") != null) {
                                    name = object.getString("name");
                                    if (object.has("email") && object.optString("email") != null) {
                                        email = object.getString("email");
                                        if (object.has("phone") && object.optJSONObject("phone") != null) {
                                            JSONObject phone = object.getJSONObject("phone");
                                            if (phone.has("mobile") && phone.optString("mobile") != null) {
                                                mobile = phone.getString("mobile");
                                            } else {
                                                Log.e(TAG, "mobile null");
                                                mobile = null;
                                            }
                                        } else {
                                            Log.e(TAG, "phone null");
                                        }
                                    } else {
                                        Log.e(TAG, "email null");
                                        email = null;
                                    }
                                } else {
                                    Log.e(TAG, "name null");
                                    name = null;
                                }
                            } else {
                                Log.e(TAG, "id null");
                                id = null;
                            }
                            mContacts.add(new Contact(id, name, email, mobile));
                        } else {
                            Log.e(TAG, "JsonArray null");
                        }
                    }
                } else {
                    Log.e(TAG, "Can not find json");
                }
            } catch (JSONException e) {
                Log.e("doInBackground: ", e.getMessage());
            }
        }
    }
}
