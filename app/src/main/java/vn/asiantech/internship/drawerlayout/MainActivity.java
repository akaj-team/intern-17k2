package vn.asiantech.internship.drawerlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMenuItemChosser = -1;

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.app_name);
        mDrawerItems = new ArrayList<>();
        //mDrawerItems.add(new User("Cao Văn Cường", "vancuong.itf@gmail.com"));
        mDrawerItems.add(new DrawerItem("Feed", false));
        mDrawerItems.add(new DrawerItem("Activity", false));
        mDrawerItems.add(new DrawerItem("Profile", false));
        mDrawerItems.add(new DrawerItem("Friends", false));
        mDrawerItems.add(new DrawerItem("Map", false));
        mDrawerItems.add(new DrawerItem("Chat", false));
        mDrawerItems.add(new DrawerItem("Settings", false));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMenuList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new DrawerApdater(mDrawerItems, this, new DrawerApdater.MainActivityRecyclerViewOnClick() {
            @Override
            public void OnClick(int position) {
                if (mMenuItemChosser>-1){
                    mDrawerItems.get(mMenuItemChosser).setSelected();
                }
                mDrawerItems.get(position).setSelected();
                mMenuItemChosser = position;
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}
