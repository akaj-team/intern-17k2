package vn.asiantech.internship.Day6Ex1DrawerLayout.ui;

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

import vn.asiantech.internship.Day6Ex1DrawerLayout.adapter.DrawerAdapter;
import vn.asiantech.internship.Day6Ex1DrawerLayout.event.OnRecyclerViewClickListener;
import vn.asiantech.internship.R;

/**
 * create MainActivity
 *
 * @author at-hoavo
 * create on 13/06/2017
 */
public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener {
    private List<String> mFunctions = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLinearlayout;
    private ContentFragment mContentFragment;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerAdapter adapter;
    private Dialog mDialog;
    public static int sSelectedPosition;
    public static Bitmap sNewProfilePic;
    public static boolean sCheckPicture;
    public static final int GALLERY_TYPE = 0;
    public static final int CAMERA_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mLinearlayout = (LinearLayout) findViewById(R.id.linearLayoutDrawer);
        ImageView mImgHome = (ImageView) findViewById(R.id.imgHome);
        mImgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        mContentFragment = new ContentFragment();
        initFragment();
        String[] functions = getResources().getStringArray(R.array.list_function);
        for (String function : functions) {
            mFunctions.add(function);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        adapter = new DrawerAdapter(mFunctions, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mLinearlayout.setTranslationX(slideOffset * drawerView.getWidth());
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenFrame, mContentFragment);
        transaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onClick(int position, boolean check) {
        if (check) {
            mContentFragment.showContent(mFunctions.get(position));
            MainActivity.sSelectedPosition = position;
        } else {
            mDialog = createDialog();
            mDialog.show();
        }
        mDrawerLayout.closeDrawers();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                sNewProfilePic = extras.getParcelable("data");
                sCheckPicture = true;
                adapter.notifyDataSetChanged();
                mDrawerLayout.openDrawer(GravityCompat.START);
                mDialog.cancel();
            }
        }

    }

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

    public void setCropImage(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
    }
}
