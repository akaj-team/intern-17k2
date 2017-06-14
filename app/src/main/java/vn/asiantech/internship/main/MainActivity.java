package vn.asiantech.internship.main;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

/**
 * class handle events onClick
 * Created by Hai on 6/12/2017.
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY = 1000;
    private static final int REQUEST_CODE_CROP = 1001;
    private static final int REQUEST_CODE_CAMERA = 1002;
    private static final int STRING_ARRAY_POSITION_FIRST = 0;
    private static final int STRING_ARRAY_POSITION_SECOND = 1;
    private static final String KEY_DATA = "data";

    private TextView mTvTitle;
    private RecyclerView mRecyclerViewDrawer;
    private ImageView mImgToggle;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLlContent;

    private DrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerItems;
    private int mPositionSelected = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        onClickToggleButton();
        handleDrawer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                case REQUEST_CODE_CAMERA:
                    cropImage(uri);
                    break;
                case REQUEST_CODE_CROP:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable(KEY_DATA);
                        mAdapter.setAvatar(bitmap);
                        mAdapter.notifyItemChanged(0);
                    }
                    break;
            }
        }
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mRecyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mLlContent = (LinearLayout) findViewById(R.id.llContent);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mImgToggle = (ImageView) findViewById(R.id.imgToggle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void onClickToggleButton() {
        mImgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    private void handleDrawer() {
        mAdapter = new DrawerAdapter(this, createData(), new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mPositionSelected > -1) {
                    mDrawerItems.get(mPositionSelected).setSelected(false);
                    mAdapter.notifyItemChanged(mPositionSelected + 1);
                }
                mDrawerItems.get(position).setSelected(true);
                mTvTitle.setText(mDrawerItems.get(position).getTitle());
                mPositionSelected = position;
                mAdapter.notifyItemChanged(position + 1);
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void onAvatarClick() {
                showDialogChangeAvatar();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewDrawer.setLayoutManager(linearLayoutManager);
        mRecyclerViewDrawer.setAdapter(mAdapter);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, 0, 0) {
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
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void showDialogChangeAvatar() {
        AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuider.setTitle(R.string.dialog_select_picture_message);
        alertDialogBuider.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == STRING_ARRAY_POSITION_FIRST) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                } else if (which == STRING_ARRAY_POSITION_SECOND) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
            }
        });
        alertDialogBuider.create();
        alertDialogBuider.show();
    }

    private void cropImage(Uri uri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, REQUEST_CODE_CROP);
        } catch (ActivityNotFoundException anfe) {
            //TODO
        }
    }

    private List<DrawerItem> createData() {
        mDrawerItems = new ArrayList<>();
        String[] arr = getResources().getStringArray(R.array.drawer_items);
        for (String anArr : arr) {
            mDrawerItems.add(new DrawerItem(anArr));
        }
        return mDrawerItems;
    }
}
