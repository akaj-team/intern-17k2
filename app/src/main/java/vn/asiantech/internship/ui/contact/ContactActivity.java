package vn.asiantech.internship.ui.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import vn.asiantech.internship.R;

/**
 * Created by quanghai on 03/07/2017.
 */

public class ContactActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewContact;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }
}
