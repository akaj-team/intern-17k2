package vn.asiantech.internship.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.NavigationAdapter;

/**
 * Created by Administrator on 6/12/2017.
 */
public class MainActivityDay6 extends AppCompatActivity implements NavigationAdapter.OnClickItem{
    private DrawerLayout mDlMain;
    private String mName;
    private String mEmail;
    private String[] mTitle;
    private TextView mTvShow;
    private RecyclerView mRecyclerView;
    private LinearLayout mLlContent;
    private NavigationAdapter mNavigationAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ImageView mImgMenu;
    private TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_day6);
        reference();
        setSupportActionBar(mToolbar);
        mNavigationAdapter = new NavigationAdapter(this, mTitle, mName, mEmail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNavigationAdapter);
        mTvTitle.setText(R.string.app_name);
        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDlMain.openDrawer(Gravity.START);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDlMain, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLlContent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDlMain.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void reference() {
        mName = "LeDuc";
        mEmail = "leanhduc2015@gmail.com";
        mTitle = new String[]{"Feed", "Activity", "Profile", "Friends", "Map", "Chat", "Settings"};
        mDlMain = (DrawerLayout) findViewById(R.id.dlMain);
        mTvShow = (TextView) findViewById(R.id.tvShow);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLlContent=(LinearLayout) findViewById(R.id.llContent);
        mImgMenu=(ImageView) findViewById(R.id.imgMenu);
        mTvTitle=(TextView) findViewById(R.id.tvTitle);
    }

    @Override
    public void click(int position) {
        mTvShow.setText(mTitle[position]);
        mDlMain.closeDrawer(Gravity.START);
    }
}
