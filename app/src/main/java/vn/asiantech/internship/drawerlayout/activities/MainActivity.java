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
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawerlayout.models.DrawerItem;
import vn.asiantech.internship.drawerlayout.ui.leftmenu.DrawerAdapter;

/**
 * Used to display drawerlayout
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class MainActivity extends AppCompatActivity {
    public static final int RESULT_LOAD_IMAGE_GALERRY = 1;
    public static final int RESULT_LOAD_IMAGE_CAMERA = 2;
    public static final String DATA = "data";
    private TextView mTvResult;
    private ImageView imgMenu;
    private RecyclerView mRecyclerView;
    private LinearLayout mLnLayout;
    private DrawerLayout mDrawerLayout;
    private DrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerItems;
    private int mPositionSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        initView();
        initLeftMenu();
        initDrawer();
        handleLeftMenu();
    }

    private void initView() {
        mLnLayout = (LinearLayout) findViewById(R.id.lnLayout);
        mTvResult = (TextView) findViewById(R.id.tvResult);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_function);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        TextView tvToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        tvToolbarTitle.setText(R.string.toolbar_title);
    }

    private void initLeftMenu() {
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(getString(R.string.feed)));
        mDrawerItems.add(new DrawerItem(getString(R.string.activity)));
        mDrawerItems.add(new DrawerItem(getString(R.string.profile)));
        mDrawerItems.add(new DrawerItem(getString(R.string.friends)));
        mDrawerItems.add(new DrawerItem(getString(R.string.map)));
        mDrawerItems.add(new DrawerItem(getString(R.string.chat)));
        mDrawerItems.add(new DrawerItem(getString(R.string.setting)));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DrawerAdapter(mDrawerItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleLeftMenu() {
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

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

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
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }

        });
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void showDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Choose one");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.select_dialog_item);
        arrayAdapter.add(getString(R.string.camera));
        arrayAdapter.add(getString(R.string.gallery));

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if (strName != null && strName.equals(getString(R.string.camera))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cropImage(intent);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE_CAMERA);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    cropImage(intent);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE_GALERRY);
                }
            }
        });
        builderSingle.show();
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
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 200);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
    }
}
