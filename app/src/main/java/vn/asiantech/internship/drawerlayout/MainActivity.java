package vn.asiantech.internship.drawerlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * Created by PC on 6/12/2017.
 */

public class MainActivity extends AppCompatActivity {
    private List<DrawerItem> mDrawerItems;
    private RecyclerView mRecyclerView;
    private DrawerApdater mAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private int mMenuItemChosser;
    private TextView mTvItemChooser;
    private RelativeLayout mRlMainContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvItemChooser = (TextView) findViewById(R.id.tvChooser);
        mRlMainContent = (RelativeLayout) findViewById(R.id.rlMainContent);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mMenuItemChosser = -1;
        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);

        mDrawerItems = new ArrayList<>();
        String[] menuList = getResources().getStringArray(R.array.menu_list);
        for (String s : menuList) {
            mDrawerItems.add(new DrawerItem(s));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.drawerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DrawerApdater(this, mDrawerItems, new DrawerApdater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mMenuItemChosser > -1) {
                    mDrawerItems.get(mMenuItemChosser).setSelected();
                }
                mDrawerItems.get(position).setSelected();
                mMenuItemChosser = position;
                mTvItemChooser.setText(mDrawerItems.get(position).getName());
                mAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawers();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                translateMainContentX(drawerView.getWidth(), 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                translateMainContentX(0, drawerView.getWidth());
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    /**
     * Add animation stranslate for MainContent
     *
     * @param fromX : position when start
     * @param toX   : position when finish
     */
    private void translateMainContentX(float fromX, float toX) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, fromX, 0, toX, 0, 0, 0, 0);
        translateAnimation.setDuration(0);
        mRlMainContent.setAnimation(translateAnimation);
        translateAnimation.setFillAfter(true);
        translateAnimation.start();
    }

}
