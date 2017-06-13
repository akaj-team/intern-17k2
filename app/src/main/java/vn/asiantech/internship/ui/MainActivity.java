package vn.asiantech.internship.ui;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * class handle events onClick
 * Created by Hai on 6/12/2017.
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY = 0;
    private static final int REQUEST_CODE_CROP = 1;
    private static final int REQUEST_CODE_CAMERA = 2;
    private TextView mTvTitle;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLlContent;

    private DrawerAdapter mAdapter;
    private List<DrawerItem> mItems;
    private int mPositionSelect = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLlContent = (LinearLayout) findViewById(R.id.llContent);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        ImageView imgToggle = (ImageView) findViewById(R.id.imgToggle);
        imgToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        mAdapter = new DrawerAdapter(this, createData(), new DrawerAdapter.OnItemListener() {
            @Override
            public void OnItemClick(int position) {
                if (mPositionSelect > -1) {
                    mItems.get(mPositionSelect).setSelected(false);
                }
                mItems.get(position).setSelected(true);
                mTvTitle.setText(mItems.get(position).getTitle());
                mPositionSelect = position;
                mAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void OnAvatarClick() {
                AlertDialog.Builder alertDialogBuider = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuider.setTitle(R.string.alertdialog_message);
                alertDialogBuider.setItems(R.array.dialog_items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, REQUEST_CODE_GALLERY);
                        } else if (which == 1) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CODE_CAMERA);
                        }
                    }
                });
                alertDialogBuider.create();
                alertDialogBuider.show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    cropImage(uri);
                    break;
                case REQUEST_CODE_CROP:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        mAdapter.setAvatar(bitmap);
                        mAdapter.notifyItemChanged(0);
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    cropImage(uri);
                    break;
            }
        }
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
            Toast toast = Toast.makeText(this, anfe.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private List<DrawerItem> createData() {
        mItems = new ArrayList<>();
        String[] arr = getResources().getStringArray(R.array.drawer_item);
        for (String anArr : arr) {
            mItems.add(new DrawerItem(anArr));
        }
        return mItems;
    }
}
