package vn.asiantech.internship.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Drawer;
import vn.asiantech.internship.ui.leftmenu.DrawerAdapter;

/**
 * Main Activity with DrawerAdapter
 * Created by anhhuy on 14/06/2017.
 */

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CAMERA = 1337;
    public static final int REQUEST_GALLERY = 1;
    public static final int REQUEST_CROP = 2;

    private TextView mTvChoose;
    private TextView mTvToolbar;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerViewNavigation;
    private Toolbar mToolbar;
    private LinearLayout mLlContent;
    private DrawerAdapter mDrawerAdapter;

    private List<Drawer> mDrawerLists;
    private int mPositionChoose = -1;
    private Uri mUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDataDrawer();
        initToolbar();
        initDrawer();
        setDrawerAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CAMERA:
                    cropImage(mUri);
                    break;
                case REQUEST_GALLERY:
                    cropImage(data.getData());
                    break;
                case REQUEST_CROP:
                    Bitmap bitmap = data.getExtras().getParcelable("data");
                    mDrawerAdapter.setBitmap(bitmap);
                    mDrawerAdapter.notifyItemChanged(0);
                    break;
            }
        }
    }

    private void initViews(){
        mTvChoose = (TextView) findViewById(R.id.tvChoose);
        mRecyclerViewNavigation = (RecyclerView) findViewById(R.id.recyclerViewNavigation);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dlNavigation);
        mLlContent = (LinearLayout) findViewById(R.id.llContent);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvToolbar = (TextView) findViewById(R.id.tvToolbar);
    }

    private void initDataDrawer(){
        mDrawerLists = new ArrayList<>();
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_feed)));
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_activity)));
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_profile)));
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_friends)));
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_map)));
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_chat)));
        mDrawerLists.add(new Drawer(getResources().getString(R.string.textview_main_settings)));
    }

    private void setDrawerAdapter(){
        mDrawerAdapter = new DrawerAdapter(this, mDrawerLists, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mPositionChoose > -1){
                    mDrawerLists.get(mPositionChoose).setChoose();
                    mDrawerAdapter.notifyItemChanged(mPositionChoose + 1);
                }
                mDrawerLists.get(position).setChoose();
                mPositionChoose = position;
                mTvChoose.setText(mDrawerLists.get(position).getDrawerName());
                mDrawerAdapter.notifyItemChanged(position + 1);
                mDrawerLayout.closeDrawers();
            }

            @Override
            public void dialogChoose(int position) {
                if (position == REQUEST_CAMERA){
                    File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    try{
                        File imageFile = File.createTempFile("img", System.currentTimeMillis() + ".jpg", file);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mUri = Uri.fromFile(imageFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
                        startActivityForResult(intent, REQUEST_CAMERA);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                if (position == REQUEST_GALLERY){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_GALLERY);
                }
            }
        });

        mRecyclerViewNavigation.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewNavigation.setAdapter(mDrawerAdapter);
        mRecyclerViewNavigation.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.set(position == 0 ? 0 : 40, position == 0 ? 0 : 40, 0, 0);

            }
        });
    }

    private void initDrawer(){
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close) {
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

        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void initToolbar(){
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_red_500_24dp);
        }
        mTvToolbar.setText(R.string.textview_toolbar_name);
    }

    private void cropImage(Uri uri){
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, REQUEST_CROP);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Interface for handle click on Drawer
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
        void dialogChoose(int position);
    }
}
