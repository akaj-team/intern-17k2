package vn.asiantech.internship.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragment.MainAnimalFragment;

/**
 * Created by ducle on 26/06/2017.
 * AnimalActivity is main activity og app
 */
public class AnimalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        MainAnimalFragment mainAnimalFragment = new MainAnimalFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.flContain, mainAnimalFragment);
        fragmentTransaction.commit();
    }
}
