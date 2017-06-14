package vn.asiantech.internship.ui.main;

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

public class MainActivity extends AppCompatActivity {

    public static final int KEY_CAMERA = 1773;
    public static final int KEY_LIBRARY = 2;
    public static final int KEY_CROP = 3;

    private RelativeLayout mRlContent;
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
                this, mDlContainer, mToolbar, R.string.Text_Messages_Navigation_Open, R.string.Text_Messages_Navigation_Close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                //slide content when drawer openning
                mRlContent.setTranslationX(slideOffset * drawerView.getWidth());
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
        mRlContent = (RelativeLayout) findViewById(R.id.rlContent);
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

}
