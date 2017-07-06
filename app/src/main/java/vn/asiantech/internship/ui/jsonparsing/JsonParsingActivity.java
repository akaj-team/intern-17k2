package vn.asiantech.internship.ui.jsonparsing;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.helpers.GetContactAsync;
import vn.asiantech.internship.models.Contact;
import vn.asiantech.internship.ui.adapters.JsonParsingAdapter;

/**
 * Activity.
 * Created by AnhHuy on 03-Jul-17.
 */
public class JsonParsingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewJson;
    private ArrayList<Contact> mContacts;
    private GetContactAsync mGetContactAsync;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parsing);
        mRecyclerViewJson = (RecyclerView) findViewById(R.id.recyclerViewParsing);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerViewJson.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewJson.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildLayoutPosition(view);
                outRect.set(20, position == 0 ? 15 : 20, 20, 20);
            }
        });

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.progress_dialog));
        progressDialog.setCancelable(false);
        mGetContactAsync = new GetContactAsync(progressDialog, new GetContactAsync.CallBackListContactListener() {
            @Override
            public void callBack(ArrayList<Contact> contacts) {
                mContacts = contacts;
                JsonParsingAdapter adapter = new JsonParsingAdapter(mContacts);
                mRecyclerViewJson.setAdapter(adapter);
            }
        });
        mGetContactAsync.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGetContactAsync != null && !mGetContactAsync.isCancelled()) {
            mGetContactAsync.cancel(true);
        }
    }
}
