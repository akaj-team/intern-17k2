package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class RecyclerActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentFriend fragmentFriend = new FragmentFriend();
        transaction.replace(R.id.fragmentInformation, fragmentFriend).commit();
    }
}
