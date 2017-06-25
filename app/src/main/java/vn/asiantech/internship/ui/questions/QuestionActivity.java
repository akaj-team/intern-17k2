package vn.asiantech.internship.ui.questions;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import vn.asiantech.internship.R;

/**
 * QuestionActivity Created by Thanh Thien
 */
public class QuestionActivity extends AppCompatActivity {
    private QuestionMainFragment mQuestionMainFragment;
    private LinearLayout mLlSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mLlSecond = (LinearLayout) findViewById(R.id.llSecond);
        replaceFragmentAddContent();
    }

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void replaceFragmentAddContent() {
        mQuestionMainFragment = new QuestionMainFragment();
        replaceFragment(mQuestionMainFragment, true);

    }

    public void replaceFragment(Fragment fragment, boolean isFirst) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isFirst) {
            fragmentTransaction.replace(R.id.llFirst, fragment);
            mLlSecond.setVisibility(View.GONE);
        } else {
            fragmentTransaction.replace(R.id.llSecond, fragment);
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
            mLlSecond.setVisibility(View.VISIBLE);
        }
        fragmentTransaction.commit();
    }

    public void sendToMain(String s, int mCurrentPosition) {
        mQuestionMainFragment.onClickAnswer(mCurrentPosition, s);
    }

    public void showDialogFragment() {
        mQuestionMainFragment.showResultFragment();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionActivity.this);
        builder.setMessage(getString(R.string.tip_show_text_do_you_want_to_exit));
        builder.setTitle(getString(R.string.notication));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
