package vn.asiantech.internship.ui.anotation;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Contact;

/**
 * A simple ContactFragment subclass.
 * Created by Thanh Thien
 */
@EFragment(R.layout.fragment_contact_anontation)
public class ContactFragment extends Fragment implements MyAsyncTask.OnGetContactListener {
    private static final String URL_API = "http://api.androidhive.info/contacts/";

    private ProgressDialog mProgressDialog;
    private MyAsyncTask mMyAsyncTask;

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @AfterViews
    void afterViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMyAsyncTask = new MyAsyncTask();
        mMyAsyncTask.setOnGetContactListener(this);
        mMyAsyncTask.execute(URL_API);
    }

    @Override
    public void openAsyncTask() {
        showProgress();
    }

    @Override
    public void finishAsyncTask(List<Contact> contacts) {
        showFinished(contacts);
    }

    @UiThread
    void showProgress() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.progress_please_wait));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @UiThread
    void showFinished(List<Contact> contacts) {
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
