package vn.asiantech.internship.ui.jsonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.helpers.HttpHandler;
import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.ui.adapters.JsonParsingAdapter;

/**
 * Activity.
 * Created by AnhHuy on 03-Jul-17.
 */
public class JsonParsingActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerViewJson;
    private ArrayList<Contact> mContacts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parsing);
        mRecyclerViewJson = (RecyclerView) findViewById(R.id.recyclerViewParsing);

        new GetContact().execute();
    }

    private class GetContact extends AsyncTask<String, Void, ArrayList<Contact>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(JsonParsingActivity.this);
            mProgressDialog.setMessage("Wait....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<Contact> doInBackground(String... params) {
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
                } catch (final JSONException e) {
                    Log.e("Json parsing error: ", e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("Main", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contacts) {
            super.onPostExecute(contacts);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            mRecyclerViewJson.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            JsonParsingAdapter mAdapter = new JsonParsingAdapter(mContacts);
            mRecyclerViewJson.setAdapter(mAdapter);
        }
    }
}
