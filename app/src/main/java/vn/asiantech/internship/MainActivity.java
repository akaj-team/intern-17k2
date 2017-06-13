package vn.asiantech.internship;

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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<DrawerItem> mDrawerItemList;
    private int mMenuItemChosser;
    private DrawerAdapter mDrawerRecyclerAdapter;
    private LinearLayout mLinearLayout;


    public static final int REQUEST_CODE_CROP = 11;
    public static final int REQUEST_CODE_GARELLY = 22;
    public static final int REQUEST_CODE_CAMERA = 33;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.lnMain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mMenuItemChosser = 0;
        // Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDefaultDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }

        //set Data
        mDrawerItemList = new ArrayList<>();
        mDrawerItemList.add(new DrawerItem("Feed", false));
        mDrawerItemList.add(new DrawerItem("Activity", false));
        mDrawerItemList.add(new DrawerItem("Profile", false));
        mDrawerItemList.add(new DrawerItem("Friends", false));
        mDrawerItemList.add(new DrawerItem("Map", false));
        mDrawerItemList.add(new DrawerItem("Chat", false));
        mDrawerItemList.add(new DrawerItem("Settings", false));

        mDrawerRecyclerAdapter = new DrawerAdapter(this, mDrawerItemList, new DrawerAdapter.OnItemClick() {

            @Override
            public void onItemClick(int position) {
                if (mMenuItemChosser > 0) {
                    mDrawerItemList.get(mMenuItemChosser).setChoose();
                }
                mDrawerItemList.get(position).setChoose();
                mMenuItemChosser = position;
                mDrawerRecyclerAdapter.notifyDataSetChanged();
                Log.d("tag12", "onClick：" + position);
            }

            @Override
            public void onAvatarClick(int select) {
                if (select == REQUEST_CODE_GARELLY) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_GARELLY);
                    return;
                }
                if (select == REQUEST_CODE_CAMERA) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mDrawerRecyclerAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_GARELLY:
                    performCrop(data.getData());
//                    Picasso.with(MainActivity.this).load(data.getData())
//                            .noPlaceholder().centerCrop().fit()
//                            .into((ImageView) findViewById(R.id.imgProf));
                    break;
                case REQUEST_CODE_CAMERA:
                    File file = saveBitmap((Bitmap) data.getExtras().getParcelable("data"));
                    if (file != null) {
                        Uri uri = Uri.fromFile(file);
                        performCrop(uri);
                    }
                    break;
                case REQUEST_CODE_CROP:
                    Bitmap bitmap = data.getExtras().getParcelable("data");
                    mDrawerRecyclerAdapter.setAvatar(bitmap);
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
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public File saveBitmap(Bitmap bitmap) {
        OutputStream outStream = null;
        File sdImageMainDirectory = null;
        try {
            File root = new File(String.valueOf(Environment.getExternalStorageDirectory()),
                    File.separator + "Camera" + File.separator);
            sdImageMainDirectory = new File(root, String.valueOf(Calendar.getInstance().getTimeInMillis()) + ".jpg");
            outStream = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            Toast.makeText(this, "Exception.",
                    Toast.LENGTH_SHORT).show();
        }
        return sdImageMainDirectory;
    }

}


