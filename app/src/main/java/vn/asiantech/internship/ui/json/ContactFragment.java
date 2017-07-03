package vn.asiantech.internship.ui.json;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Contact;

/**
 * A simple ContactFragment subclass.
 * Created by Thanh Thien
 */
public class ContactFragment extends Fragment implements MyAsyncTask.OnGetContactListener {
    private static final String url = "http://api.androidhive.info/contacts/";

    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private MyAsyncTask mMyAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_json, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mMyAsyncTask = new MyAsyncTask();
        mMyAsyncTask.setOnGetContactListener(this);
        mMyAsyncTask.execute(url);
        return v;
    }

    @Override
    public void openAsyncTask() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.progress_please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void finishAsyncTask(List<Contact> contacts) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        ContactAdapter adapter = new ContactAdapter(contacts);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        if (mMyAsyncTask != null) {
            mMyAsyncTask.cancel(true);
        }
        super.onDestroy();
    }
}
