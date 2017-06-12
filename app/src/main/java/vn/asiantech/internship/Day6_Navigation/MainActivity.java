package vn.asiantech.internship.Day6_Navigation;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;
    private TextView mTvContent;
    private Toolbar mToolbar;
    private DrawableAdapter mRecyclerAdapter;
    private List<DrawableItem> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        addEvents();
    }

    private void addEvents() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                mDrawerLayout.closeDrawers();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        String[] titles = {"Feed", "Activity", "Profile", "Friends", "Map",
                "Chat", "Settings", "Home", "Store", "History", "Back", "Exit"};
        mTitles = initData(titles);
        mRecyclerAdapter = new DrawableAdapter(mTitles, new DrawableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTvContent = (TextView) findViewById(R.id.tvContent);
                mTvContent.setText(mTitles.get(position).getTitle());
                mTitles.get(position).setSelected(true);
                int size = mTitles.size();
                for (int i = 0; i < size; i++) {
                    if (i != position) {
                        mTitles.get(i).setSelected(false);
                    }
                }
                mDrawerLayout.closeDrawers();
                mRecyclerAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    /**
     * Initialize List and add DrawableItem
     *
     * @param items list title of navigation
     * @return list of DrawableItem
     */
    private List<DrawableItem> initData(String[] items) {
        List<DrawableItem> list = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            list.add(new DrawableItem(items[i]));
        }
        return list;
    }
}
