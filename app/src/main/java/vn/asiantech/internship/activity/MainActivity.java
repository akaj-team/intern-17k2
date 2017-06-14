package vn.asiantech.internship.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.leftmenu.NavigationAdapter;
import vn.asiantech.internship.models.User;

/**
 * main activity DrawerLayout
 */
public class MainActivityDay6 extends AppCompatActivity {
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;
    public static final int REQUEST_CODE_CROP = 3;

    private DrawerLayout mDlMain;
    private String[] mTitle;
    private TextView mTvShow;
    private RecyclerView mRecyclerView;
    private LinearLayout mLlContent;
    private NavigationAdapter mNavigationAdapter;
    private Toolbar mToolbar;
    private ImageView mImgMenu;
    private TextView mTvTitle;
    private List<User> mUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_day6);
        mUsers = new ArrayList<>();
        reference();
        setSupportActionBar(mToolbar);
        mNavigationAdapter = new NavigationAdapter(this, mTitle, mUsers, new NavigationAdapter.OnClickItem() {
            @Override
            public void click(int position) {
                mTvShow.setText(mTitle[position]);
                mDlMain.closeDrawer(Gravity.START);
            }

            @Override
            public void onClickAvatar(int key) {
                if (key == NavigationAdapter.KEY_GALLERY) {
                    getPhotoGallery();
                }
                if (key == NavigationAdapter.KEY_CAMERA) {
                    getPhotoCamera();
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mNavigationAdapter);
        mTvTitle.setText(R.string.app_name);
        mTvTitle.setTextColor(Color.WHITE);
        mImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDlMain.openDrawer(Gravity.START);
            }
        });
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDlMain, R.string.app_name, R.string.app_name) {
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
        mUsers.add(new User(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round), "LeDuc", "leanhduc2015"));
        mTitle = new String[]{"Feed", "Activity", "Profile", "Friends", "Map", "Chat", "Settings"};
        mDlMain = (DrawerLayout) findViewById(R.id.dlMain);
        mTvShow = (TextView) findViewById(R.id.tvShow);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLlContent = (LinearLayout) findViewById(R.id.llContent);
        mImgMenu = (ImageView) findViewById(R.id.imgMenu);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
    }


    private void getPhotoCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void getPhotoGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (requestCode == REQUEST_CODE_GALLERY) {
                try {
                    mUsers.get(0).setImgUser(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == REQUEST_CODE_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                mUsers.get(0).setImgUser(thumbnail);
            }
            mNavigationAdapter.notifyItemChanged(0);
        }
    }
    private void performCrop(Uri picUri){
        Intent cropIntent=new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri,"image/*");
        cropIntent.putExtra("crop", true);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 128);
        cropIntent.putExtra("outputY", 128);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_CODE_CROP);
    }
}
