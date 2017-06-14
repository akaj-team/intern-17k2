package vn.asiantech.internship.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CROP = 11;
    public static final int REQUEST_CODE_GALERY = 22;
    public static final int REQUEST_CODE_CAMERA = 33;

    private List<DrawerItem> mDrawerItemList;
    private int mMenuItemChooser;
    private DrawerAdapter mDrawerRecyclerAdapter;
    private LinearLayout mLinearLayout;
    private TextView mTvShow;
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawer;
    private Uri mUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initDrawer();

    }

    public void initView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.lnMain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvShow = (TextView) findViewById(R.id.tvShow);
        mRecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);

        mMenuItemChooser = 0;
        // Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDefaultDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }

    }

    public void initData() {
        //set Data
        mDrawerItemList = new ArrayList<>();
        String[] menuItemList = getResources().getStringArray(R.array.listitem);
        for (String menuItem : menuItemList) {
            mDrawerItemList.add(new DrawerItem(menuItem));
        }
        mDrawerRecyclerAdapter = new DrawerAdapter(this, mDrawerItemList, new DrawerAdapter.OnItemClick() {

            @Override
            public void onItemClick(int position) {
                if (mMenuItemChooser > 0) {
                    mDrawerItemList.get(mMenuItemChooser).setChoose();
                }
                mDrawerItemList.get(position).setChoose();
                mMenuItemChooser = position;
                mDrawerRecyclerAdapter.notifyDataSetChanged();
                mTvShow.setText(mDrawerItemList.get(position).getTitle());
                mDrawer.closeDrawers();
                Log.d("tag12", "onClick：" + position);
            }

            @Override
            public void onAvatarClick(int select) {
                Log.d("Tag11", "onAvatarClick: "+ select);
                if (select == REQUEST_CODE_GALERY) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_GALERY);
                    return;
                }
                if (select == REQUEST_CODE_CAMERA) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        File root = new File(String.valueOf(Environment.getExternalStorageDirectory()),
                                File.separator + "Camera" + File.separator);
                        File file = File.createTempFile("img", ".jpg", root);
                        mUri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
            }
        });
    }

    public void initDrawer() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDrawerRecyclerAdapter);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                super.onDrawerSlide(view, slideOffset);
                mLinearLayout.setTranslationX(slideOffset * view.getWidth());
            }
        };
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_GALERY:
                    performCrop(data.getData());
                    break;
                case REQUEST_CODE_CAMERA:
                    performCrop(mUri);
                    break;
                case REQUEST_CODE_CROP:
                    Bitmap bm = data.getExtras().getParcelable("data");
                    mDrawerRecyclerAdapter.setAvatar(bm);
                    mDrawerRecyclerAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }

    private void performCrop(Uri uri) {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, REQUEST_CODE_CROP);
        } catch (ActivityNotFoundException e) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
