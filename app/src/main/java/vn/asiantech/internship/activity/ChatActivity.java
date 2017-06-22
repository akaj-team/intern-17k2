package vn.asiantech.internship.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragment.ChatFragment;

/**
 * Created by ducle on 22/06/2017.
 */

public class ChatActivity extends AppCompatActivity {
    private ChatFragment mChatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mChatFragment = new ChatFragment();
        switchFragment(mChatFragment, true, R.id.flContain);
    }

    /**
     * switch fragment to replace in framlayout
     *
     * @param fragment       is fragment need to show
     * @param addToBackStack add to back stack or no
     * @param id             id of framelayout contain fragment
     */
    public void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        if (fragment.getTag() == null) {
            ft.replace(id, fragment, fragment.toString());
        } else {
            ft.replace(id, fragment);
        }
        ft.commit();
    }
}
