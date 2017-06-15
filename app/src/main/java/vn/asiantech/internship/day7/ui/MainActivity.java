package vn.asiantech.internship.day7.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.day7.adapter.DrawerAdapter;
import vn.asiantech.internship.day7.event.OnRecyclerViewClickListener;
import vn.asiantech.internship.R;

/**
 * create MainActivity
 *
 * @author at-hoavo
 *         create on 13/06/2017
 */
public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener {
    public static final int GALLERY_TYPE = 0;
    public static final int CAMERA_TYPE = 1;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearlayout;
    private RecyclerView mRecyclerView;
    private ContentFragment mContentFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private Dialog mDialog;

    private DrawerAdapter adapterDrawer;
    private List<String> mFunctions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initRecyclerView();
        initDrawerLayout();
        initFragment();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLinearlayout = (LinearLayout) findViewById(R.id.llDrawer);
        ImageView imgHome = (ImageView) findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void initData() {
        mFunctions.add("Feed");
        mFunctions.add("Activity");
        mFunctions.add("Profile");
        mFunctions.add("Friends");
        mFunctions.add("Map");
        mFunctions.add("Chat");
        mFunctions.add("Settings");
    }

    private void initRecyclerView() {
        adapterDrawer = new DrawerAdapter(mFunctions, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapterDrawer);
    }

    private void initDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLinearlayout.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    private void initFragment() {
        mContentFragment = new ContentFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frContent, mContentFragment);
        transaction.commit();
    }

    @Override
    public void onClick(int position, boolean check) {
        if (check) {
            mContentFragment.showContent(mFunctions.get(position));
            adapterDrawer.setPosition(position);
        } else {
            mDialog = createDialog();
            mDialog.show();
        }
        mDrawerLayout.closeDrawers();
        adapterDrawer.notifyDataSetChanged();
    }

    //create dialog with list data got from resource
    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title_please_choose)
                .setItems(R.array.items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case GALLERY_TYPE:
                                Intent intent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                setCropImage(intent);
                                startActivityForResult(intent, GALLERY_TYPE);
                                break;
                            default:
                                try {
                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    setCropImage(cameraIntent);
                                    startActivityForResult(cameraIntent, CAMERA_TYPE);
                                } catch (ActivityNotFoundException anfe) {
                                    Toast toast = Toast
                                            .makeText(getApplicationContext(), "This device doesn't support the camera action!", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                        }
                    }
                });
        return builder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                adapterDrawer.setBitMapAvatar((Bitmap) extras.getParcelable("data"));
                adapterDrawer.notifyDataSetChanged();
                mDrawerLayout.openDrawer(GravityCompat.START);
                mDialog.cancel();
            }
        }

    }

    public void setCropImage(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", R.dimen.image_outputx);
        intent.putExtra("outputY", R.dimen.image_outputy);
        intent.putExtra("aspectX", R.dimen.image_aspectx);
        intent.putExtra("aspectY", R.dimen.image_aspecty);
        intent.putExtra("return-data", true);
    }
}
