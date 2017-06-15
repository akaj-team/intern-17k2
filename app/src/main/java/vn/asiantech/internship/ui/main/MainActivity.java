package vn.asiantech.internship.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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
    private Toolbar mToolbar;

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
                this, mDlContainer, mToolbar, R.string.text_messages_navigation_open, R.string.text_messages_navigation_close) {
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

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvContent = (TextView) findViewById(R.id.tvContent);
        mRlParent = (RelativeLayout) findViewById(R.id.rlParent);
        mFragmentDrawer = findViewById(R.id.fragmentDrawer);
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
    public void setMainText(String s) {
        mTvContent.setText(s);
        mDlContainer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        showDialogOut();
    }

    private void showDialogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setTitle(getString(R.string.tip_show_text_do_you_want_to_exit));
        builder.create().show();
    }
}
