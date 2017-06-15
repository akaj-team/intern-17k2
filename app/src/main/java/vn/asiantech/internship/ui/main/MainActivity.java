package vn.asiantech.internship.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Main of all fragment
 * Created by Thanh Thien
 */
public class MainActivity extends AppCompatActivity {

    public static final int KEY_CAMERA = 1773;
    public static final int KEY_LIBRARY = 2;
    public static final int KEY_CROP = 3;

    private RelativeLayout mRlParent;
    private TextView mTvContent;
    private DrawerLayout mDlContainer;
    private View mFragmentDrawer;
    private RelativeLayout mRlContent;
    private Toolbar mToolbar;

    private boolean mIsBackToOut;
    private Fragment currentFragmentOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolbar();
        initDrawer();
    }

    private void initDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, mDlContainer, mToolbar, R.string.Text_Messages_Navigation_Open, R.string.Text_Messages_Navigation_Close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                //slide content when drawer openning
                mRlParent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDlContainer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @SuppressLint("CommitTransaction")
    private void initView() {
        mIsBackToOut = true;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvContent = (TextView) findViewById(R.id.tvContent);
        mRlParent = (RelativeLayout) findViewById(R.id.rlParent);
        mFragmentDrawer = findViewById(R.id.fragmentDrawer);
        mRlContent = (RelativeLayout) findViewById(R.id.rlContent);
        mDlContainer = (DrawerLayout) findViewById(R.id.dlContainer);
        setWidthDrawer();
    }

    private void setWidthDrawer() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int newWidth = displayMetrics.widthPixels;
        ViewGroup.LayoutParams params = mFragmentDrawer.getLayoutParams();
        params.width = (2 * newWidth) / 3;
        mFragmentDrawer.setLayoutParams(params);
    }

    /**
     * @param s is a title of item
     */
    public void setMainText(String s, boolean isShowText) {
        if (isShowText) {
            mTvContent.setVisibility(View.VISIBLE);
            if (mIsBackToOut) {
                mRlContent.setVisibility(View.GONE);
            }
            mTvContent.setText(s);
        } else {
            mTvContent.setVisibility(View.GONE);
            if (mIsBackToOut) {
                mRlContent.setVisibility(View.VISIBLE);
            }
        }
        mDlContainer.closeDrawers();
    }

    /**
     * Open drawer layout
     */
    public void openDrawer() {
        mDlContainer.openDrawer(Gravity.START);
    }

    /**
     * @param fragmentContent is a fragment replace to this id
     */
    public void setFragmentSlideInBottom(Fragment fragmentContent) {
        currentFragmentOpen = fragmentContent;
        mIsBackToOut = false;
        mRlContent.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom);
        fragmentTransaction.replace(R.id.rlContent, fragmentContent).commit();
    }

    private void outFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom).remove(fragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (!mIsBackToOut) {
                mIsBackToOut = true;
                outFragment(currentFragmentOpen);
                return false;
            } else {
                showDialogOut();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setTitle(getString(R.string.Tip_Show_Text_Do_You_Want_To_Exit));
        builder.create().show();
    }
}
