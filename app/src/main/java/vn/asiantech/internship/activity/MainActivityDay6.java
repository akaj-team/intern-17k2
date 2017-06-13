package vn.asiantech.internship.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.NavigationAdapter;

/**
 * main activity DrawerLayout
 */
public class MainActivityDay6 extends AppCompatActivity {
    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_GALLERY = 2;
    private DrawerLayout mDlMain;
    private String mName;
    private String mEmail;
    private String[] mTitle;
    private TextView mTvShow;
    private RecyclerView mRecyclerView;
    private LinearLayout mLlContent;
    private  NavigationAdapter mNavigationAdapter;
    private Toolbar mToolbar;
    private ImageView mImgMenu;
    private TextView mTvTitle;
    private Bitmap mBitmap,mBitmap2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_day6);
        reference();
        setSupportActionBar(mToolbar);
        mBitmap2= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
        mNavigationAdapter = new NavigationAdapter(this, mTitle, mName, mEmail, new NavigationAdapter.OnClickItem() {
            @Override
            public void click(int position) {
                mTvShow.setText(mTitle[position]);
                mDlMain.closeDrawer(Gravity.START);
            }

            @Override
            public void onClickAvatar(int key) {
                if(key==NavigationAdapter.KEY_GALLERY){
                    getPhotoGallery();
                }
                if(key==NavigationAdapter.KEY_CAMERA){
                    getPhotoCamera();
                }
            }
        },mBitmap);
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
        mName = "LeDuc";
        mEmail = "leanhduc2015@gmail.com";
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
                    mBitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    Toast.makeText(getApplication(),"1the nao "+(mBitmap==mBitmap2),Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == REQUEST_CODE_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mBitmap=thumbnail;
                Toast.makeText(getApplication(),"2the nao "+(mBitmap==mBitmap2),Toast.LENGTH_LONG).show();
            }
            mNavigationAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mNavigationAdapter);
        }
    }
}
