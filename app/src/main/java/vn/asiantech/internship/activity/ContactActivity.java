package vn.asiantech.internship.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.asynctasks.GetContacts;
import vn.asiantech.internship.models.Contact;

/**
 * Created by ducle on 03/07/2017.
 * ContactActivity contain a RecyclerView to show list contact
 */
public class ContactActivity extends AppCompatActivity implements GetContacts.OnUpdateListener{
    private ProgressDialog mProgressDialog;
    private static String url = "http://api.androidhive.info/contacts/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        new GetContacts(this).execute(url);
    }

    @Override
    public void onShowProgressDialog() {
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait ... ");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    public void onUpdateData(ArrayList<Contact> contacts) {

    }
}
