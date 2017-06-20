package vn.asiantech.internship.note.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;


public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frContainer, new NoteFragment(new NoteFragment.OnChangeFragment() {
            @Override
            public void onChange(int key, int position) {
                switch (key) {
                    case 1:
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frContainer, new NoteAddFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        FragmentTransaction frtransaction = getSupportFragmentManager().beginTransaction();
                        DetailFragment detailFragment = new DetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("keykey", position);
                        detailFragment.setArguments(bundle);
                        frtransaction.replace(R.id.frContainer, detailFragment);
                        frtransaction.addToBackStack(null);
                        frtransaction.commit();
                }
            }
        }));
        transaction.addToBackStack(null);
        transaction.commit();
        /**/
    }
}
