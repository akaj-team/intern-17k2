package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 10/06/2017.
 */
public class RecycleViewrActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FriendFragment fragmentFriend = new FriendFragment();
        transaction.replace(R.id.fragmentFriend, fragmentFriend).commit();
    }
}
