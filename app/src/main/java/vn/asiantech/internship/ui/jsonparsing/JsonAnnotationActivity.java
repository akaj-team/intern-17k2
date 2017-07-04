package vn.asiantech.internship.ui.jsonparsing;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
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
        closeProgressDialog();
        initRecyclerView();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initRecyclerView() {
        JsonParsingAdapter adapter = new JsonParsingAdapter(mContacts);
        mRecyclerViewJson.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewJson.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                outRect.set(20, position == 0 ? 15 : 20, 20, 20);
            }
        });
        mRecyclerViewJson.setAdapter(adapter);
    }

    private void doBackground() {
        HttpHandler httpHandler = new HttpHandler();
        String url = "http://api.androidhive.info/contacts/";
        String jsonString = httpHandler.makeServiceCall(url);
        if (jsonString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    String id = object.getString("id");
                    String name = object.getString("name");
                    String email = object.getString("email");

                    JSONObject phone = object.getJSONObject("phone");
                    String mobile = phone.getString("mobile");

                    mContacts.add(new Contact(id, name, email, mobile));
                }
            } catch (JSONException e) {
                Log.e("doInBackground: ", e.getMessage());
            }
        }
    }
}
