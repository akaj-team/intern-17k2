package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.model.DrawerItem;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_DATA = "data";
    private static final int REQUEST_GALLERY = 1234;
    private static final int REQUEST_CAMERA = 1235;
    private static final int REQUEST_CROP = 1236;
    private static final int TYPE_GALLERY = 0;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerViewDrawer;
    private LinearLayout mLinearLayoutContent;
    private Toolbar mToolbar;

    private DrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerItems;
    private int mItemSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
        initAdapter();
        initLeftMenu();
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mLinearLayoutContent = (LinearLayout) findViewById(R.id.llContent);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void initLeftMenu() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
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
                mLinearLayoutContent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initAdapter() {
        mDrawerItems = initData();
        mAdapter = new DrawerAdapter(MainActivity.this, mDrawerItems, new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TextView tvTitleFragment = (TextView) findViewById(R.id.tvTitleFragment);
                DrawerItem item = mDrawerItems.get(position);
                tvTitleFragment.setText(item.getTitle());
                if (mItemSelected >= 0) {
                    mDrawerItems.get(mItemSelected).setSelect(false);
                    mAdapter.notifyItemChanged(mItemSelected + 1);
                }
                item.setSelect(true);
                mItemSelected = position;
                mDrawerLayout.closeDrawers();
                mAdapter.notifyItemChanged(position + 1);
            }
        });
        mRecyclerViewDrawer.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerViewDrawer.setHasFixedSize(true);
        mRecyclerViewDrawer.setAdapter(mAdapter);
    }

    private List<DrawerItem> initData() {
        List<DrawerItem> list = new ArrayList<>();
        list.add(new DrawerItem(getString(R.string.menuleft_title_feed)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_activity)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_profile)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_friend)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_map)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_chat)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_setting)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_home)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_store)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_history)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_back)));
        list.add(new DrawerItem(getString(R.string.menuleft_title_exit)));
        return list;
    }
}
