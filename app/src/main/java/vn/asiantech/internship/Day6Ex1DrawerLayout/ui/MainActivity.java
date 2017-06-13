package vn.asiantech.internship.Day6Ex1DrawerLayout.ui;

import android.graphics.Bitmap;
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
import java.util.List;

import vn.asiantech.internship.Day6Ex1DrawerLayout.adapter.DrawerAdapter;
import vn.asiantech.internship.Day6Ex1DrawerLayout.event.OnRecyclerViewClickListener;
import vn.asiantech.internship.R;

/**
 * create MainActivity
 *
 * @author at-hoavo
 * create on 13/06/2017
 */
public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener {
    private List<String> mFunctions = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearlayout;
    private ContentFragment mContentFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter adapter;
    public static int sSelectedPosition;
    public static Bitmap sNewProfilePic;
    public static boolean sCheckPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLinearlayout = (LinearLayout) findViewById(R.id.linearLayoutDrawer);
        ImageView mImgHome = (ImageView) findViewById(R.id.imgHome);
        mImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        mContentFragment = new ContentFragment();
        initFragment();
        String[] functions = getResources().getStringArray(R.array.list_function);
        for (String function : functions) {
            mFunctions.add(function);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        adapter = new DrawerAdapter(mFunctions, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLinearlayout.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenFrame, mContentFragment);
        transaction.commit();
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
            MainActivity.sSelectedPosition = position;
        }
        mDrawerLayout.closeDrawers();
        adapter.notifyDataSetChanged();
    }
}
