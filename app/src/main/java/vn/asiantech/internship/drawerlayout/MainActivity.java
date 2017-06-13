package vn.asiantech.internship.drawerlayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.models.User;

/**
 * Created by PC on 6/12/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_CROP = 22;
    public static final int REQUEST_CODE_GARELLY = 222;
    public static final int REQUEST_CODE_CAMERA = 2222;

    private TextView mTvItemChooser;
    private RelativeLayout mRlMainContent;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ImageView mImgMenu;
    private TextView mTvTitle;
    private DrawerLayout mDrawerLayout;

    private DrawerApdater mAdapter;

    private List<Object> mDrawerItems;

    private ActionBarDrawerToggle mDrawerToggle;
    private int mMenuItemChosser = -1;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvItemChooser = (TextView) findViewById(R.id.tvChooser);
        mRlMainContent = (RelativeLayout) findViewById(R.id.rlMainContent);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mImgMenu = (ImageView) findViewById(R.id.imgMenu);
        mRecyclerView = (RecyclerView) findViewById(R.id.drawerList);

        setSupportActionBar(mToolbar);
        setTitle("");
        mTvTitle.setText(R.string.app_name);
        mImgMenu.setOnClickListener(this);
        mDrawerItems = new ArrayList<>();
        mUser = new User(getResources().getString(R.string.user_name), getResources().getString(R.string.email));
        mDrawerItems.add(mUser);
        String[] menuList = getResources().getStringArray(R.array.menu_list);
        for (String s : menuList) {
            mDrawerItems.add(new DrawerItem(s));
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DrawerApdater(this, mDrawerItems, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mMenuItemChosser > -1) {
                    ((DrawerItem) mDrawerItems.get(mMenuItemChosser)).setSelected();
                }
                ((DrawerItem) mDrawerItems.get(position)).setSelected();
                mMenuItemChosser = position;
                mTvItemChooser.setText(((DrawerItem) mDrawerItems.get(position)).getName());
                mAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void onAvatarClick(int chooser) {
                if (chooser == REQUEST_CODE_GARELLY) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_GARELLY);
                    return;
                }
                if (chooser == REQUEST_CODE_CAMERA) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mRlMainContent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_GARELLY:
                    cropImage(data.getData());
                    break;
                case REQUEST_CODE_CAMERA:
                    File file = savaBitmap((Bitmap) data.getExtras().get("data"));
                    if (file != null) {
                        Uri uri = Uri.fromFile(file);
                        cropImage(uri);
                    }
                    break;
                case REQUEST_CODE_CROP:
                    Bitmap bm = data.getExtras().getParcelable("data");
                    mUser.setAvatar(bm);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMenu:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    /**
     * This interface used to handle DrawerLayoutItem onClick
     */
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAvatarClick(int chooser);
    }

    private void cropImage(Uri uri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, REQUEST_CODE_CROP);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your phone doesn't support Crop Image!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public File savaBitmap(Bitmap bm) {

        OutputStream fOut = null;
        File sdImageMainDirectory = null;

        try {
            File root = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "Camera" + File.separator);
            sdImageMainDirectory = new File(root, String.valueOf(Calendar.getInstance().getTimeInMillis()) + ".jpg");
            fOut = new FileOutputStream(sdImageMainDirectory);

        } catch (Exception e) {
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Toast.makeText(this, "Exception.",
                    Toast.LENGTH_SHORT).show();
        }
        return sdImageMainDirectory;
    }

}

