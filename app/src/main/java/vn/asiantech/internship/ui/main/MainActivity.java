package vn.asiantech.internship.ui.main;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

/**
 * Mainactivity
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/12/2017
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_CROP = 22;
    public static final int REQUEST_CODE_GALLERY = 222;
    public static final int REQUEST_CODE_CAMERA = 2222;

    private TextView mTvItemChooser;
    private RelativeLayout mRlContent;
    private RecyclerView mRecyclerViewDrawer;
    private DrawerLayout mDrawerLayout;

    private DrawerAdapter mAdapter;

    private List<DrawerItem> mDrawerItems;
    private int mPositionSelected = -1;
    //Uri of photo taked by camera
    private Uri mPhotoUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAdapter();
        initDrawerLayout();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    cropImage(data.getData());
                    break;
                case REQUEST_CODE_CAMERA:
                    cropImage(mPhotoUri);
                    break;
                case REQUEST_CODE_CROP:
                    Bitmap bm = data.getExtras().getParcelable("data");
                    mAdapter.setAvatar(bm);
                    mAdapter.notifyItemChanged(0);
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
            Toast toast = Toast.makeText(this, R.string.crop_error, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void initView() {
        mTvItemChooser = (TextView) findViewById(R.id.tvChooser);
        mRlContent = (RelativeLayout) findViewById(R.id.rlContent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        ImageView imgMenu = (ImageView) findViewById(R.id.imgMenu);
        mRecyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);

        setSupportActionBar(toolbar);
        tvTitle.setText(R.string.app_name);
        imgMenu.setOnClickListener(this);
    }

    public void initAdapter() {
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.feed)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.activity)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.profile)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.friend)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.friends)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.map)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.chat)));
        mDrawerItems.add(new DrawerItem(getResources().getString(R.string.settings)));
        mRecyclerViewDrawer.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DrawerAdapter(this, mDrawerItems, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mPositionSelected > -1) {
                    mDrawerItems.get(mPositionSelected).setSelected();
                    mAdapter.notifyItemChanged(mPositionSelected + 1);
                }
                mDrawerItems.get(position).setSelected();
                mPositionSelected = position;
                mTvItemChooser.setText(mDrawerItems.get(position).getName());
                mAdapter.notifyItemChanged(position + 1);
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void onAvatarClick(int chooser) {
                if (chooser == REQUEST_CODE_GALLERY) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                    return;
                }
                if (chooser == REQUEST_CODE_CAMERA) {
                    File root = new File(Environment.getExternalStorageDirectory().getPath() + File.separatorChar + "Camera" + File.separatorChar);
                    try {
                        File imageFile = File.createTempFile("img", System.currentTimeMillis() + ".jpg", root);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mPhotoUri = Uri.fromFile(imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    } catch (IOException e) {
                        Log.i("Tag11", e.toString());
                    }
                }
            }
        });
        mRecyclerViewDrawer.setAdapter(mAdapter);
    }

    public void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {
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
                mRlContent.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    /**
     * This interface used to handle DrawerLayoutItem onClick
     */
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAvatarClick(int chooser);
    }
}
