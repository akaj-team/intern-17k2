package vn.asiantech.internship.day15.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.ui.fragment.MyDismissFragment;
import vn.asiantech.internship.day15.ui.fragment.QuizFragment;
import vn.asiantech.internship.day15.ui.fragment.ResultFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        replaceFragment(new QuizFragment(), false);
    }

    public void replaceFragment(Fragment fragment, boolean isBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.flQuiz, fragment);
        if (isBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.flQuiz);
        if (fragment != null && fragment instanceof ResultFragment) {
            new MyDismissFragment().show(getSupportFragmentManager(), "dismiss dialog");
        } else {
            super.onBackPressed();
        }
    }
}
