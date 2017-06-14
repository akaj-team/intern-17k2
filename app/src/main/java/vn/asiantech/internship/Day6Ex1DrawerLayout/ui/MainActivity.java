package vn.asiantech.internship.Day6Ex1DrawerLayout.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.Day6Ex1DrawerLayout.adapter.DrawerAdapter;
import vn.asiantech.internship.Day6Ex1DrawerLayout.event.OnRecyclerViewClickListener;
import vn.asiantech.internship.R;

/**
 * create MainActivity
 *
 * @author at-hoavo
 *         create on 13/06/2017
 */
public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener {
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearLayout;
    private ContentFragment mContentFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter mAdapterDrawer;
    private List<String> mFunctions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        initDrawerLayout();
        initFragment();
    }

    private void initFragment() {
        mContentFragment = new ContentFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frContent, mContentFragment);
        transaction.commit();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        String[] functions = getResources().getStringArray(R.array.list_function);
        Collections.addAll(mFunctions, functions);
        mAdapterDrawer = new DrawerAdapter(mFunctions, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapterDrawer);
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLinearLayout = (LinearLayout) findViewById(R.id.llDrawer);
        ImageView imgHome = (ImageView) findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLinearLayout.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onClick(int position, boolean check) {
        if (check) {
            mContentFragment.showContent(mFunctions.get(position));
            mAdapterDrawer.setPosition(position);
        }
        mDrawerLayout.closeDrawers();
        mAdapterDrawer.notifyDataSetChanged();
    }
}
