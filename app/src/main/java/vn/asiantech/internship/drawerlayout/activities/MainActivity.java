package vn.asiantech.internship.drawerlayout.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import vn.asiantech.internship.R;
import vn.asiantech.internship.drawerlayout.ui.leftmenu.DrawerAdapter;
import vn.asiantech.internship.drawerlayout.models.DrawerItem;

/**
 * Used to display drawerlayout
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private DrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerItems;
    private TextView mTvResult;
    private int mPositionSelected = -1;
    private LinearLayout mLnLayout;
    public static final int RESULT_LOAD_IMAGE_GALERRY = 1;
    public static final int RESULT_LOAD_IMAGE_CAMERA = 2;
    public static final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        mLnLayout = (LinearLayout) findViewById(R.id.lnLayout);
        mTvResult = (TextView) findViewById(R.id.tvResult);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_function);

        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(getString(R.string.feed)));
        mDrawerItems.add(new DrawerItem(getString(R.string.activity)));
        mDrawerItems.add(new DrawerItem(getString(R.string.profile)));
        mDrawerItems.add(new DrawerItem(getString(R.string.friends)));
        mDrawerItems.add(new DrawerItem(getString(R.string.map)));
        mDrawerItems.add(new DrawerItem(getString(R.string.chat)));
        mDrawerItems.add(new DrawerItem(getString(R.string.setting)));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new DrawerAdapter(mDrawerItems);
        recyclerView.setAdapter(mAdapter);
        navigation();
        mAdapter.setOnItemClickListener(new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTvResult.setText(mDrawerItems.get(position).getName());
                mDrawerLayout.closeDrawers();
                if (mPositionSelected >= 0) {
                    mDrawerItems.get(mPositionSelected).setState(false);
                }
                mPositionSelected = position;
                mDrawerItems.get(position).setState(true);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAvatarClick() {
                showDialog();
            }
        });
    }

    private void navigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLnLayout.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cropImage(intent);
                startActivityForResult(intent, RESULT_LOAD_IMAGE_CAMERA);
            }
        });
        builder.setNegativeButton(R.string.gallery, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                cropImage(intent);
                startActivityForResult(intent, RESULT_LOAD_IMAGE_GALERRY);
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == RESULT_LOAD_IMAGE_GALERRY) {
                getImage(data);
            } else if (requestCode == RESULT_LOAD_IMAGE_CAMERA) {
                getImage(data);
            }
        }
    }

    private void getImage(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get(DATA);
        ImageView imgAvata = (ImageView) findViewById(R.id.crImgAvata);
        imgAvata.setImageBitmap(imageBitmap);
    }

    private void cropImage(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
    }
}
