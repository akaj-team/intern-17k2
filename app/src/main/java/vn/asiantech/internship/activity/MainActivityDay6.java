package vn.asiantech.internship.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.NavigationAdapter;
import vn.asiantech.internship.adapters.TouchItem;

/**
 * Created by Administrator on 6/12/2017.
 */
public class MainActivityDay6 extends AppCompatActivity{
    private DrawerLayout mDlMain;
    private String mName;
    private String mEmail;
    private String[] mTitle;
    private TextView mTvShow;
    private RecyclerView mRecyclerView;
    private NavigationAdapter mNavigationAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_day6);
        reference();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels * 2 / 3;
        mNavigationAdapter = new NavigationAdapter(this, mTitle, mName, mEmail);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNavigationAdapter);
        mRecyclerView.addOnItemTouchListener(new TouchItem(getApplication(), mRecyclerView, new TouchItem.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        mDrawerToggle = new ActionBarDrawerToggle(this, mDlMain, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDlMain.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDlMain.isDrawerOpen(GravityCompat.START)) {
                    mDlMain.closeDrawer(GravityCompat.START);
                } else {
                    mDlMain.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void reference() {
        mName = "LeDuc";
        mEmail = "leanhduc2015@gmail.com";
        mTitle = new String[]{"Feed", "Activity", "Profile", "Friends", "Map", "Chat", "Settings"};
        mDlMain = (DrawerLayout) findViewById(R.id.dlMain);
        mTvShow = (TextView) findViewById(R.id.tvShow);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
