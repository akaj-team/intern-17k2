package vn.asiantech.internship.drawer.ui.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import vn.asiantech.internship.drawer.models.DrawerItem;
import vn.asiantech.internship.drawer.ui.feed.FeedActivity;
import vn.asiantech.internship.drawer.ui.leftmenu.DrawerAdapter;

/**
 * MainActivity
 */
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
        setContentView(R.layout.activity_main_drawer);
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
                    mDrawerItems.get(mItemSelected).setChoose(false);
                    mAdapter.notifyItemChanged(mItemSelected + 1);
                }
                item.setChoose(true);
                mItemSelected = position;
                mDrawerLayout.closeDrawers();
                mAdapter.notifyItemChanged(position + 1);
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, FeedActivity.class));
                }

            }

            @Override
            public void onImageClick() {
                Dialog dialog = showDialog();
                dialog.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (data != null && resultCode == RESULT_OK) {
                    functionCropImage(data.getData());
                }
                break;
            case REQUEST_CAMERA:
                if (data != null & resultCode == RESULT_OK) {
                    functionCropImage(data.getData());
                }
                break;
            default:
                if (data != null && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap selectedBitmap = extras.getParcelable(KEY_DATA);
                    mAdapter.setImageBitmap(selectedBitmap);
                    mAdapter.notifyItemChanged(0);
                }
        }
    }

    private Dialog showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title)
                .setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case TYPE_GALLERY:
                                Intent galleryIntent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, REQUEST_GALLERY);
                                break;
                            default:
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        }
                    }
                });
        return builder.create();
    }

    public void functionCropImage(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", R.dimen.header_image_size);
        cropIntent.putExtra("outputY", R.dimen.header_image_size);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_CROP);
    }
}
